package com.rusnak.dim.web.crawler.dto;

import com.rusnak.dim.web.crawler.enums.SearchStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class SearchResultOutputDto implements Comparable<SearchResultOutputDto> {

    private String url;
    private SearchStatus searchStatus;
    private String descriptionError;

    @Override
    public int compareTo(SearchResultOutputDto o) {
        return this.getUrl().compareTo(o.getUrl());
    }
}
