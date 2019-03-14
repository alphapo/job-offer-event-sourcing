package com.esipe.ing3.demo.coreapi.events;

import lombok.Data;

@Data
public class OrderConfirmedEvent {
  
    private final String jobId;
  
}