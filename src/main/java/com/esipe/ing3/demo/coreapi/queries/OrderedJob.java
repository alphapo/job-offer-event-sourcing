package com.esipe.ing3.demo.coreapi.queries;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderedJob {
 
    private final String jobId;
    private final String jobName;
    private JobStatus jobStatus;
 
    public OrderedJob(String jobId, String product) {
        this.jobId = jobId;
        this.jobName = product;
        jobStatus = JobStatus.CREATED;
    }

    public void setOrderOpened(){
        this.jobStatus = JobStatus.OPENED;
    }

    public void setOrderConfirmed() {
        this.jobStatus = JobStatus.CONFIRMED;
    }
 
    public void setOrderClosed() {
        this.jobStatus = JobStatus.CLOSED;
    }
 
    // getters, equals/hashCode and toString functions
}