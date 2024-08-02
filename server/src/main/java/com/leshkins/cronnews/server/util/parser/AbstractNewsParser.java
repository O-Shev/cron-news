package com.leshkins.cronnews.server.util.parser;

import com.leshkins.cronnews.server.util.exception.ParseException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractNewsParser implements NewsParser{
    private final RestClient client;
    private final String URL;

    protected AbstractNewsParser(String URL) {
        this.URL = URL;
        this.client = RestClient.create(URL);
    }

    protected String retrieveHtmlBody(){
        return client.get()
                .retrieve()
                .onStatus(new ResponseErrorHandler() {
                    @Override
                    public boolean hasError(ClientHttpResponse response) throws IOException {
                        if(response.getBody().equals(InputStream.nullInputStream())) return true;
                        return response.getStatusCode().isError();
                    }

                    @Override
                    public void handleError(ClientHttpResponse response) throws IOException {
                        System.out.println(response);
                        throw new ParseException("Exception was occur during retrieve data from news site. URL target is:" + URL);
                    }
                })
                .body(String.class);
    }
}
