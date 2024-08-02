package com.leshkins.cronnews.server.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Map;

@Data
@Builder
@ToString
public class ParsedNewsDTO {
    private String headline;
    private String description;
    private Timestamp publishedAt;
    private String mediaUrl;

    private Map<String, String> additional;
}
