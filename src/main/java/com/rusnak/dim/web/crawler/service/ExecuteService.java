package com.rusnak.dim.web.crawler.service;

import com.rusnak.dim.web.crawler.dto.SearchResultInputDto;

import java.util.concurrent.ExecutionException;

public interface ExecuteService {

    void parallelExecuteService(SearchResultInputDto input) throws ExecutionException, InterruptedException;
}
