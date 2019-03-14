package com.esipe.ing3.demo.commandmodel;

import com.esipe.ing3.demo.coreapi.commands.ConfirmOrderCommand;
import com.esipe.ing3.demo.coreapi.commands.CreateOrderCommand;
import com.esipe.ing3.demo.coreapi.commands.CloseOrderCommand;
import com.esipe.ing3.demo.coreapi.commands.OpenOrderCommand;
import com.esipe.ing3.demo.coreapi.events.OrderConfirmedEvent;
import com.esipe.ing3.demo.coreapi.events.OrderCreatedEvent;
import com.esipe.ing3.demo.coreapi.events.OrderClosedEvent;
import com.esipe.ing3.demo.coreapi.events.OrderOpenedEvent;
import com.esipe.ing3.demo.coreapi.queries.JobStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class OrderAggregate {
 
    @AggregateIdentifier
    private String jobId;
    private JobStatus orderJobStatus;
 
    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        apply(new OrderCreatedEvent(command.getJobId(), command.getJobName()));
    }

    @CommandHandler
    public void handle(OpenOrderCommand command) {
        apply(new OrderOpenedEvent(jobId));
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) {

        if (!orderJobStatus.equals(JobStatus.OPENED)) {
            throw new IllegalStateException("Cannot confirm an order which has not been opened yet.");
        }

        apply(new OrderConfirmedEvent(jobId));
    }

    @CommandHandler
    public void handle(CloseOrderCommand command) {

        apply(new OrderClosedEvent(jobId));
    }



    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.jobId = event.getJobId();
        orderJobStatus = JobStatus.CREATED;
    }

    @EventSourcingHandler
    public void on(OrderOpenedEvent event) {
        this.jobId = event.getJobId();
        this.orderJobStatus = JobStatus.OPENED;
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        this.jobId = event.getJobId();
        this.orderJobStatus = JobStatus.CONFIRMED;
    }

    @EventSourcingHandler
    public void on(OrderClosedEvent event) {
        this.jobId = event.getJobId();
        orderJobStatus = JobStatus.CLOSED;
    }

    protected OrderAggregate() { }
}