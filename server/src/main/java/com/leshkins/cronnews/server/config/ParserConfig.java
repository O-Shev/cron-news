package com.leshkins.cronnews.server.config;

import com.leshkins.cronnews.server.util.parser.BbcParser;
import com.leshkins.cronnews.server.util.parser.NewsParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



// Every website has own structure then logic for parsing can be different.
// currently implemented only parser for bbc.com, but it easy scalable
@Configuration
public class ParserConfig {

    private final String parserTarget;

    public ParserConfig(@Value("${spring.news-config.news-parser-target}") String parserTarget) {
        this.parserTarget = parserTarget;
    }


    @Bean
    public NewsParser createNewsParser(){
        return switch (parserTarget) {
            case "bbc" -> new BbcParser();
            default -> new BbcParser();
        };
    }
}
