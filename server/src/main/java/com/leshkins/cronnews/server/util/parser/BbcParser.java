package com.leshkins.cronnews.server.util.parser;

import com.leshkins.cronnews.server.dto.ParsedNewsDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Pattern;

public class BbcParser extends AbstractNewsParser implements NewsParser{
    static public String URL="https://www.bbc.com/news";
    static final Pattern timePattern = Pattern.compile("^\\d+\\s(mins|hrs|days?)\\sago$");

    public BbcParser() {
        super(URL);
    }


    @Override
    public List<ParsedNewsDTO> parse() {
        String htmlBody = retrieveHtmlBody();

        return Jsoup.parse(htmlBody)
                .select("div[data-testid=edinburgh-article]")
                .stream()
                .map((e)-> {
                    Element imgDiv = e.selectFirst("div[data-testid=card-media-wrapper]");
                    Element img = imgDiv != null ? imgDiv.selectFirst("img[srcset]") : null;
                    String imgUrl = img != null ? img.attr("src") : null;

                    Element headline = e.selectFirst("h2[data-testid=card-headline]");
                    String headlineText = headline != null ? headline.text() : null;

                    Element description = e.selectFirst("p[data-testid=card-description]");
                    String descriptionText = description != null ? description.text() : null;

                    Element lastUpdated = e.selectFirst("span[data-testid=card-metadata-lastupdated]");
                    String lastUpdatedText = lastUpdated != null ? lastUpdated.text() : null;
                    Timestamp publishedAt = lastUpdatedText != null ? parseTime(lastUpdatedText) : null;

                    return ParsedNewsDTO.builder()
                            .headline(headlineText)
                            .description(descriptionText)
                            .publishedAt(publishedAt)
                            .mediaUrl(imgUrl)
                            .build();
                }).toList();
    }


    private Timestamp parseTime(String relativeTime) {
        if(relativeTime.isEmpty()) return null;
        if(!timePattern.matcher(relativeTime).matches()) return null;

        Instant now = Instant.now();
        long amount = Long.parseLong(relativeTime.split(" ")[0]);
        String unit = relativeTime.split(" ")[1];

        if (unit.startsWith("min")) {
            return Timestamp.from(now.minus(amount, ChronoUnit.MINUTES));
        } else if (unit.startsWith("hr")) {
            return Timestamp.from(now.minus(amount, ChronoUnit.HOURS));
        } else if (unit.startsWith("day")) {
            return Timestamp.from(now.minus(amount, ChronoUnit.DAYS));
        } else {
            return null;
        }
    }
}
