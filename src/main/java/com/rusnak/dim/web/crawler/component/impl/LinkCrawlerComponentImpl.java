package com.rusnak.dim.web.crawler.component.impl;

import com.rusnak.dim.web.crawler.component.LinkCrawlerComponent;
import com.rusnak.dim.web.crawler.dto.SearchResultOutputDto;
import com.rusnak.dim.web.crawler.enums.SearchStatus;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LinkCrawlerComponentImpl implements LinkCrawlerComponent {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String CSS_QUERY_A_HREF = "a[href]";
    private static final String ATTRIBUTE_KEY_ABS_HREF = "abs:href";
    private static final String START_WITH_HTTP = "http";

    @Override
    public void pickingUpLinks(Queue<String> queueUrls, Set<SearchResultOutputDto> markedUrls, int maxCountOfLinks) {
        while (!queueUrls.isEmpty()) {
            String currentUrl = queueUrls.remove();

            if (markedUrls.size() < maxCountOfLinks) {
                try {
                    var page = Jsoup.connect(currentUrl).get();
                    var links = page.select(CSS_QUERY_A_HREF);

                    var urls = links.stream()
                            .map(element -> element.attr(ATTRIBUTE_KEY_ABS_HREF))
                            .filter(link -> link.startsWith(START_WITH_HTTP))
                            .collect(Collectors.toList());

                    urls.forEach(url -> {
                        if (markedUrls.size() == maxCountOfLinks) {
                            pickingUpLinks(queueUrls, markedUrls, maxCountOfLinks);
                        } else {
                            queueUrls.add(url);
                            markedUrls.add(new SearchResultOutputDto(url, SearchStatus.UPLOAD, null));
                        }
                    });
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}
