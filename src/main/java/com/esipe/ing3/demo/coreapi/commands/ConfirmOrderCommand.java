package com.esipe.ing3.demo.coreapi.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class ConfirmOrderCommand {
  
    @TargetAggregateIdentifier
    private final String jobId;
     
}