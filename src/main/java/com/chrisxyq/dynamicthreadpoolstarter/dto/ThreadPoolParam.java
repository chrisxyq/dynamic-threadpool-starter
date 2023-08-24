package com.chrisxyq.dynamicthreadpoolstarter.dto;

import lombok.Data;

@Data
public class ThreadPoolParam {
    private String poolName;
    private int poolSize;
    private int maxPoolSize;
    private int queueSize;
    private String rejectHandler;

}
