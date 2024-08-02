package com.leshkins.cronnews.server.util.exception.news;

public class NewsNotFoundException extends RuntimeException {
    public NewsNotFoundException() {
    }

    public NewsNotFoundException(String message) {
        super(message);
    }
}
