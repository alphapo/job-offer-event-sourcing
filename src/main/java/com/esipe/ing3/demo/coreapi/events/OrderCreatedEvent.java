package com.esipe.ing3.demo.coreapi.events;

import lombok.Data;

@Data
public class OrderCreatedEvent {
  
    private final String jobId;
    private final String jobName;
  
}