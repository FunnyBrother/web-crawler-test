package com.rusnak.dim.web.crawler.service.impl;

import com.rusnak.dim.web.crawler.component.LinkCrawlerComponent;
import com.rusnak.dim.web.crawler.component.SearchTextComponent;
import com.rusnak.dim.web.crawler.dto.SearchResultInputDto;
import com.rusnak.dim.web.crawler.dto.SearchResultOutputDto;
import com.rusnak.dim.web.crawler.enums.SearchStatus;
import com.rusnak.dim.web.crawler.service.ExecuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

@Service
@RequiredArgsConstructor
public class ExecuteServiceImpl implements ExecuteService {

    private final LinkCrawlerComponent linkCrawlerComponent;
    private final SearchTextComponent searchTextComponent;

    @Override
    public void parallelExecuteService(SearchResultInputDto input) throws ExecutionException, InterruptedException {
        var maxThreads = input.getMaxThreads();
        var maxCountScanUrl = input.getMaxCountScanUrl();
        var startUrl = input.getStartUrl();
        var searchText = input.getSearchText();

        forkJoinPoolSubmit(startUrl, maxCountScanUrl, searchText, maxThreads);
    }

    private void forkJoinPoolSubmit(String startUrl, int maxCountScanUrl, String searchText, int maxThreads)
            throws ExecutionException, InterruptedException {
        var searchResult = new SearchResultOutputDto(startUrl, SearchStatus.UPLOAD, null);

        var marked = new ConcurrentSkipListSet<>(Collections.singleton(searchResult));
        var queue = new ConcurrentLinkedQueue<>(Collections.singleton(startUrl));

        var forkJoinPool = new ForkJoinPool(maxThreads);

        forkJoinPool.submit(() -> {
            linkCrawlerComponent.pickingUpLinks(queue, marked, maxCountScanUrl);

            marked.parallelStream().forEach(output -> searchTextComponent.searchText(output, searchText));
        }).get();
    }

}
