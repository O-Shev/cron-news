package com.leshkins.cronnews.server.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@Builder
@ToString
public class NewsDTO {
    @NotBlank(message = "headline can't be empty")
    private String headline;
    private String description;
    private String mediaUrl;
    private Timestamp publishedAt;
    private Timestamp lastUpdateAt;
}
