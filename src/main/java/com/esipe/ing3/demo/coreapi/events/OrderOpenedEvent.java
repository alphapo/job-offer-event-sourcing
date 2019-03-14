package com.esipe.ing3.demo.coreapi.events;

import lombok.Data;

@Data
public class OrderOpenedEvent {
  
    private final String jobId;
  
}