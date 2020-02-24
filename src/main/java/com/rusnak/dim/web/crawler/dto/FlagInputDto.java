package com.rusnak.dim.web.crawler.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FlagInputDto {

    @NotNull
    private Boolean processStatus;
}
