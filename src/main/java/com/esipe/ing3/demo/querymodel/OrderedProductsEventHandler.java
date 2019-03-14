package com.esipe.ing3.demo.querymodel;

import com.esipe.ing3.demo.coreapi.events.OrderClosedEvent;
import com.esipe.ing3.demo.coreapi.queries.FindAllOrderedProductsQuery;
import com.esipe.ing3.demo.coreapi.queries.OrderedJob;
import com.esipe.ing3.demo.coreapi.events.OrderConfirmedEvent;
import com.esipe.ing3.demo.coreapi.events.OrderCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderedProductsEventHandler {
 
    private final Map<String, OrderedJob> orderedProducts = new HashMap<>();
 
    @EventHandler
    public void on(OrderCreatedEvent event) {
        String orderId = event.getJobId();
        orderedProducts.put(orderId, new OrderedJob(orderId, event.getJobName()));
    }

    // Event Handlers for OrderConfirmedEvent and OrderClosedEvent...
    @EventHandler
    public void on(OrderConfirmedEvent confirmedEvent){
        String orderId = confirmedEvent.getJobId();
        orderedProducts.forEach((s, orderedJob) -> {
            if(orderId.equals(s))
                orderedJob.setOrderConfirmed();
        });
    }

    @EventHandler
    public void on(OrderClosedEvent shippedEvent){
        String orderId = shippedEvent.getJobId();
        orderedProducts.forEach((s, orderedJob) -> {
            if(orderId.equals(s))
                orderedJob.setOrderClosed();
        });
    }

    @QueryHandler
    public List<OrderedJob> handle(FindAllOrderedProductsQuery query) {
        return new ArrayList<>(orderedProducts.values());
    }

    public static void main(String[] agrs){
        Map<String, OrderedJob> orderedProducts = new HashMap<>();
        orderedProducts.put("hello", new OrderedJob("12", "vélo"));
        orderedProducts.put("bonjour", new OrderedJob("44", "moto"));
        orderedProducts.put("esipe", new OrderedJob("19", "car"));

        orderedProducts.forEach((s, orderedJob) -> {
            if("bonjour".equals(s))
                orderedJob.setOrderClosed();
        });

        orderedProducts.forEach((k, v)-> System.out.println(k+" test: "+v.getJobStatus()));
    }

}