package com.rusnak.dim.web.crawler.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SearchResultInputDto {

    @NotBlank
    private String startUrl;
    @NotNull
    private Integer maxThreads;
    @NotNull
    private String searchText;
    @NotBlank
    private Integer maxCountScanUrl;

}
