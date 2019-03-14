package com.esipe.ing3.demo.coreapi.events;

import lombok.Data;

@Data
public class OrderClosedEvent {
 
    private final String jobId;
 
}