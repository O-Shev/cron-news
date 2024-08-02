package com.leshkins.cronnews.server.service;

import com.leshkins.cronnews.server.dto.ParsedNewsDTO;
import com.leshkins.cronnews.server.util.parser.NewsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsParserService {

    private final NewsParser newsParser;


    @Autowired
    public NewsParserService(NewsParser newsParser) {

        this.newsParser = newsParser;
    }


    public List<ParsedNewsDTO> getParsedNews(){
        return newsParser.parse();
    }
}
