package com.rusnak.dim.web.crawler.component;

import com.rusnak.dim.web.crawler.dto.SearchResultOutputDto;

import java.util.Queue;
import java.util.Set;

public interface LinkCrawlerComponent {

    void pickingUpLinks(Queue<String> queueUrls, Set<SearchResultOutputDto> marked, int maxCountOfLinks);
}
