package com.leshkins.cronnews.server.service;

import com.leshkins.cronnews.server.util.mapper.NewsMapper;
import com.leshkins.cronnews.server.dto.NewsDTO;
import com.leshkins.cronnews.server.model.News;
import com.leshkins.cronnews.server.model.enums.DayPart;
import com.leshkins.cronnews.server.repository.NewsRepository;
import com.leshkins.cronnews.server.util.exception.news.NewsAlreadyExistException;
import com.leshkins.cronnews.server.util.exception.news.NewsNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.*;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsParserService newsParserService;
    private final NewsMapper newsMapper;
    private final Integer newsForLastDays;

    @Autowired
    public NewsService(NewsRepository newsRepository, NewsParserService newsParserService, NewsMapper newsMapper,
                       @Value("${spring.news-config.news-for-last-days}") Integer newsForLastDays) {
        this.newsRepository = newsRepository;
        this.newsParserService = newsParserService;
        this.newsMapper = newsMapper;
        this.newsForLastDays = newsForLastDays;
    }

    // Parse news, save in repo - repeat every 20 min
    // News websites don't provide info (like unique id) that we can use for determine uniquely of news post
    // so, before saving news post in repo we check on existing it by headline and save news only if not exist such headline.
    // Also, there are checks on time published - saves only for the current day
    // Also, delete old news from DB
    @PostConstruct
    @Scheduled(fixedRate = 20 * 60 * 1000)
    protected void processNews(){
        List<News> newsList = newsParserService.getParsedNews().stream()
                .map(p-> News.builder()
                        .headline(p.getHeadline())
                        .description(p.getDescription())
                        .mediaURL(p.getMediaUrl())
                        .publishedAt(p.getPublishedAt() != null ? p.getPublishedAt() : Timestamp.from(Instant.now()))
                        .lastUpdateAt(p.getPublishedAt() != null ? p.getPublishedAt() : Timestamp.from(Instant.now()))
                        .build())
                .filter(this::forLastDaysFilter)
                .toList();

        // save News from parser only if corresponded headline is not present yet
        for (News news : newsList) {
            Optional<News> existingPostOpt = newsRepository.findByHeadline(news.getHeadline());
            if (existingPostOpt.isEmpty()) newsRepository.save(news);
        }

        List<News> newsList1 = newsRepository.findAll();
        for (News news : newsList1) {
            if(!forLastDaysFilter(news)) newsRepository.delete(news);
        }
    }

    // return True if [now time] - [time of published] < [news for X hours]
    private boolean forLastDaysFilter(Instant publishedAt){
        return Duration.between(publishedAt, Instant.now()).toDays() < this.newsForLastDays;
    }
    private boolean forLastDaysFilter(News news){
        return forLastDaysFilter(news.getPublishedAt().toInstant());
    }



    public List<NewsDTO> getNewsForToday(DayPart dayPart){
        LocalDate currentDate = LocalDate.now();
        if(dayPart != null){
            return newsRepository
                    .findNewsByPublishedAtBetween(
                            Timestamp.valueOf(LocalDateTime.of(currentDate, dayPart.start())),
                            Timestamp.valueOf(LocalDateTime.of(currentDate, dayPart.end())))
                    .stream()
                    .map(newsMapper::toDto)
                    .toList();
        } else {
            return newsRepository
                    .findNewsByPublishedAtBetween(
                            Timestamp.valueOf(currentDate.atStartOfDay()),
                            Timestamp.valueOf(currentDate.atTime(LocalTime.MAX)))
                    .stream()
                    .map(newsMapper::toDto)
                    .toList();
        }
    }





    public List<NewsDTO> getAllNews(){
        return newsRepository.findAll().stream()
                .map(newsMapper::toDto)
                .toList();
    }

    public NewsDTO createNews(NewsDTO newsDTO){
        Optional<News> newsOpt = newsRepository.findByHeadline(newsDTO.getHeadline());
        if(newsOpt.isPresent()) throw new NewsAlreadyExistException("news with such headline already exist");

        News news = newsMapper.toModel(newsDTO);
        news.setPublishedAt(Timestamp.from(Instant.now()));
        news.setLastUpdateAt(Timestamp.from(Instant.now()));
        news =  newsRepository.save(news);

        return newsMapper.toDto(news);
    }

    public Optional<NewsDTO> getNews(Long id){
        Optional<News> newsOpt = newsRepository.findById(id);
        if(newsOpt.isEmpty()) throw new NewsNotFoundException("news with such id don't exist");
        return newsOpt.map(newsMapper::toDto);
    }

    public NewsDTO updateNews(Long id, NewsDTO newsDTO){
        Optional<News> existingNewsOpt = newsRepository.findById(id);
        if(existingNewsOpt.isEmpty()) throw new NewsNotFoundException("news with such id don't exist");

        Optional<News> newsOpt = newsRepository.findByHeadline(newsDTO.getHeadline());
        if(newsOpt.isPresent()) throw new NewsAlreadyExistException("news with such headline already exist");


        News existingNews = existingNewsOpt.get();

        existingNews.setHeadline(newsDTO.getHeadline());
        existingNews.setDescription(newsDTO.getDescription());
        existingNews.setMediaURL(newsDTO.getMediaUrl());
        existingNews.setLastUpdateAt(Timestamp.from(Instant.now()));

        News updatedNews = newsRepository.save(existingNews);
        return newsMapper.toDto(updatedNews);
    }

    public void deleteNews(Long id){
        Optional<News> newsOpt = newsRepository.findById(id);
        if(newsOpt.isEmpty()) throw new NewsNotFoundException("news with such id don't exist");
        newsRepository.delete(newsOpt.get());
    }
}
