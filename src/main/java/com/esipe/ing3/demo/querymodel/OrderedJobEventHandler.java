package com.esipe.ing3.demo.querymodel;

import com.esipe.ing3.demo.coreapi.events.OrderClosedEvent;
import com.esipe.ing3.demo.coreapi.events.OrderConfirmedEvent;
import com.esipe.ing3.demo.coreapi.events.OrderCreatedEvent;
import com.esipe.ing3.demo.coreapi.queries.FindAllOrderedJobQuery;
import com.esipe.ing3.demo.coreapi.queries.OrderedJob;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderedJobEventHandler {
 
    private final Map<String, OrderedJob> orderedJobs = new HashMap<>();

    @EventHandler
    public void on(OrderCreatedEvent event) {
        String orderId = event.getJobId();
        orderedJobs.put(orderId, new OrderedJob(orderId, event.getJobName()));
    }

    // Event Handlers for OrderConfirmedEvent and OrderClosedEvent...
    @EventHandler
    public void on(OrderConfirmedEvent confirmedEvent){
        String orderId = confirmedEvent.getJobId();
        orderedJobs.forEach((s, orderedJob) -> {
            if(orderId.equals(s))
                orderedJob.setOrderConfirmed();
        });
    }

    @EventHandler
    public void on(OrderClosedEvent shippedEvent){
        String orderId = shippedEvent.getJobId();
        orderedJobs.forEach((s, orderedJob) -> {
            if(orderId.equals(s))
                orderedJob.setOrderClosed();
        });
    }

    @QueryHandler
    public List<OrderedJob> handle(FindAllOrderedJobQuery query) {
        return new ArrayList<>(orderedJobs.values());
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