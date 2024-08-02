package com.leshkins.cronnews.server.util.exception.news;

public class NewsAlreadyExistException extends RuntimeException{

    public NewsAlreadyExistException() {
    }

    public NewsAlreadyExistException(String message) {
        super(message);
    }
}
