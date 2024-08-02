package com.leshkins.cronnews.server.util.parser;

import com.leshkins.cronnews.server.dto.ParsedNewsDTO;

import java.util.List;

public interface NewsParser {
    List<ParsedNewsDTO> parse();
}
