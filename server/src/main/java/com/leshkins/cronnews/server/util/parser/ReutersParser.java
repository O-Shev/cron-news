package com.leshkins.cronnews.server.util.parser;

import com.leshkins.cronnews.server.dto.ParsedNewsDTO;

import java.util.List;

public class ReutersParser extends AbstractNewsParser implements NewsParser{
    static public String URL="https://www.reuters.com/";

    public ReutersParser() {
        super(URL);
    }

    @Override
    public List<ParsedNewsDTO> parse() {
        //TODO
        return null;
    }
}
