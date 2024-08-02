package com.leshkins.cronnews.server.controller;

import com.leshkins.cronnews.server.service.NewsService;
import com.leshkins.cronnews.server.dto.NewsDTO;
import com.leshkins.cronnews.server.model.enums.DayPart;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        List<NewsDTO> newsList = newsService.getAllNews();
        return ResponseEntity.ok(newsList);
    }

    @GetMapping("/today")
    public ResponseEntity<List<NewsDTO>> getTodayNews(@RequestParam(name="day_part", required = false) String dayPartQ) {
        DayPart dayPart = switch (dayPartQ){
            case "morning" -> DayPart.MORNING;
            case "midday" -> DayPart.MIDDAY;
            case "evening" -> DayPart.EVENING;
            case null, default -> null;
        };

        List<NewsDTO> newsList = newsService.getNewsForToday(dayPart);
        return ResponseEntity.ok(newsList);
    }

    @PostMapping
    public ResponseEntity<NewsDTO> createNews(@RequestBody @Valid NewsDTO newsDTO) {

        NewsDTO createdNews = newsService.createNews(newsDTO);
        return ResponseEntity.ok(createdNews);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Long id,
                                              @RequestBody @Valid NewsDTO newsDTO) {
        NewsDTO updatedNews = newsService.updateNews(id, newsDTO);
        return ResponseEntity.ok(updatedNews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNews(@PathVariable Long id) {
        Optional<NewsDTO> newsDTO = newsService.getNews(id);
        return newsDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok().build();
    }
}
