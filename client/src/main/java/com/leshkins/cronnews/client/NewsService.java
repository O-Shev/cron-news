package com.leshkins.cronnews.client;



import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

public class NewsService {
    private static final String BASE_URL = "http://localhost:8080/news";
    private final RestClient restClient;

    public NewsService() {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public List<NewsDTO> getTodayNews() {
        NewsDTO[] newsDTOs = null;
        try{
            newsDTOs = restClient.get()
                    .uri("/today")
                    .retrieve()
                    .body(NewsDTO[].class);
        } catch (Exception e){
            e.fillInStackTrace();
        }


        if(newsDTOs == null || newsDTOs.length == 0) return Collections.emptyList();
        return List.of(newsDTOs);
    }

    public List<NewsDTO> getTodayMorningNews() {
        NewsDTO[] newsDTOs = null;
        try{
            newsDTOs = restClient.get()
                    .uri("/today?day_part={day_part}", "morning")
                    .retrieve()
                    .body(NewsDTO[].class);
        } catch (Exception e) {
            e.fillInStackTrace();
        }


        if(newsDTOs == null || newsDTOs.length == 0) return Collections.emptyList();
        return List.of(newsDTOs);
    }

    public List<NewsDTO> getTodayMiddayNews() {
        NewsDTO[] newsDTOs = null;
        try{
            newsDTOs = restClient.get()
                    .uri("/today?day_part={day_part}", "midday")
                    .retrieve()
                    .body(NewsDTO[].class);
        } catch (Exception e){
            e.fillInStackTrace();
        }

        if(newsDTOs == null || newsDTOs.length == 0) return Collections.emptyList();
        return List.of(newsDTOs);
    }

    public List<NewsDTO> getTodayEveningNews() {
        NewsDTO[] newsDTOs = null;
        try{
            newsDTOs = restClient.get()
                    .uri("/today?day_part={day_part}", "evening")
                    .retrieve()
                    .body(NewsDTO[].class);
        } catch (Exception e){
            e.fillInStackTrace();
        }


        if(newsDTOs == null || newsDTOs.length == 0) return Collections.emptyList();
        return List.of(newsDTOs);
    }
}
