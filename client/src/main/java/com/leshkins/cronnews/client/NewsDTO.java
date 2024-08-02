package com.leshkins.cronnews.client;


import java.sql.Timestamp;

public record NewsDTO (
     String headline,
     String description,
     String mediaUrl,
     Timestamp publishedAt,
     Timestamp lastUpdateAt
){}
