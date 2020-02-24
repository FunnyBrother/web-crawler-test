package com.rusnak.dim.web.crawler.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SearchStatus {

    UPLOAD("upload"),
    FOUND("found"),
    NOT_FOUND("not found"),
    ERROR("error"),
    ;

    private final String status;
}
