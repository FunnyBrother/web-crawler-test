package com.rusnak.dim.web.crawler.component;

import com.rusnak.dim.web.crawler.dto.SearchResultOutputDto;

public interface SearchTextComponent {

    void searchText(SearchResultOutputDto output, String searchText);
}
