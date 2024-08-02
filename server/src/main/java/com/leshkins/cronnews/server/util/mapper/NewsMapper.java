package com.leshkins.cronnews.server.util.mapper;

import com.leshkins.cronnews.server.dto.NewsDTO;
import com.leshkins.cronnews.server.model.News;
import org.springframework.stereotype.Component;

// here usually I use MapStruct but to provide my code style =>
@Component
public class NewsMapper {

    public NewsDTO toDto(News news){
        if(news == null) return null;
        return NewsDTO.builder()
                .headline(news.getHeadline())
                .description(news.getDescription())
                .mediaUrl(news.getMediaURL())
                .publishedAt(news.getPublishedAt())
                .lastUpdateAt(news.getLastUpdateAt())
                .build();
    }

    public News toModel(NewsDTO newsDTO){
        if(newsDTO == null) return null;
        return News.builder()
                .headline(newsDTO.getHeadline())
                .description(newsDTO.getDescription())
                .mediaURL(newsDTO.getMediaUrl())
                .build();
    }
}
