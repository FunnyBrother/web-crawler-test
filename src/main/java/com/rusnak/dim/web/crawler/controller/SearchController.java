package com.rusnak.dim.web.crawler.controller;

import com.rusnak.dim.web.crawler.dto.FlagInputDto;
import com.rusnak.dim.web.crawler.dto.SearchResultInputDto;
import com.rusnak.dim.web.crawler.component.impl.ProcessComponent;
import com.rusnak.dim.web.crawler.service.ExecuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final ExecuteService executeService;
    private final ProcessComponent processComponent;

    @MessageMapping("/input")
    public void results(SearchResultInputDto input) throws ExecutionException, InterruptedException {
        executeService.parallelExecuteService(input);
    }

    @MessageMapping("/flag")
    public void flag(FlagInputDto input) {
        processComponent.setFlag(input.getProcessStatus());
    }
}
