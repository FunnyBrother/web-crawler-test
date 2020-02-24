package com.rusnak.dim.web.crawler.component.impl;

import com.rusnak.dim.web.crawler.component.SearchTextComponent;
import com.rusnak.dim.web.crawler.dto.SearchResultOutputDto;
import com.rusnak.dim.web.crawler.enums.SearchStatus;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SearchTextComponentImpl implements SearchTextComponent {

    private static final String TOPIC_RESULTS = "/topic/results";

    private final SimpMessagingTemplate brokerMessagingTemplate;
    private final ProcessComponent processComponent;

    @Override
    public void searchText(SearchResultOutputDto output, String searchText) {
        if (processComponent.getFlag()) {
            String url = output.getUrl();

            try {
                var page = Jsoup.connect(url).get();

                output.setSearchStatus(isContainsText(searchText, page));
            } catch (IOException e) {
                output.setSearchStatus(SearchStatus.ERROR);
                output.setDescriptionError(e.getMessage());
            }
        }

        brokerMessagingTemplate.convertAndSend(TOPIC_RESULTS, output);
    }

    private SearchStatus isContainsText(String searchText, Document page) {
        return page.text().contains(searchText) ? SearchStatus.FOUND : SearchStatus.NOT_FOUND;
    }
}
