package com.rusnak.dim.web.crawler.component.impl;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class ProcessComponent {

    private final AtomicBoolean processStatus = new AtomicBoolean();

    public void setFlag(Boolean status) {
        processStatus.set(status);
    }

    public boolean getFlag() {
        return processStatus.get();
    }
}
