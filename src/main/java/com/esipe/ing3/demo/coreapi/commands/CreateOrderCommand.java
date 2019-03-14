package com.esipe.ing3.demo.coreapi.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class CreateOrderCommand {
  
    @TargetAggregateIdentifier
    private final String jobId;
    private final String jobName;
  
}