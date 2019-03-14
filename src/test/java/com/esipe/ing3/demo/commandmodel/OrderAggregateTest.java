package com.esipe.ing3.demo.commandmodel;

import com.esipe.ing3.demo.coreapi.commands.CreateOrderCommand;
import com.esipe.ing3.demo.coreapi.commands.CloseOrderCommand;
import com.esipe.ing3.demo.coreapi.events.OrderConfirmedEvent;
import com.esipe.ing3.demo.coreapi.events.OrderCreatedEvent;
import com.esipe.ing3.demo.coreapi.events.OrderClosedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class OrderAggregateTest {

    private FixtureConfiguration<OrderAggregate> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
    }

    @Test
    public void shouldProduceEventWhenGettingCommand() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe Chair";
        fixture.givenNoPriorActivity()
                .when(new CreateOrderCommand(orderId, product))
                .expectEvents(new OrderCreatedEvent(orderId, product));
    }

    @Test
    public void shouldProduceExceptionIfOrderNotConfirmed() {
        String orderId = UUID.randomUUID().toString();
        String product = "Deluxe Chair";
        fixture.given(new OrderCreatedEvent(orderId, product))
                .when(new CloseOrderCommand(orderId))
                .expectException(IllegalStateException.class);
    }

    @Test
    public void shouldCloseOrderIfConfirmed() {
        String orderId = UUID.randomUUID().toString();
        String product = "Manager";
        fixture.given(new OrderCreatedEvent(orderId, product), new OrderConfirmedEvent(orderId))
                .when(new CloseOrderCommand(orderId))
                .expectEvents(new OrderClosedEvent(orderId));
    }

}