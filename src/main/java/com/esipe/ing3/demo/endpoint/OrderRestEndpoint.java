package com.esipe.ing3.demo.endpoint;

import com.esipe.ing3.demo.coreapi.queries.FindAllOrderedProductsQuery;
import com.esipe.ing3.demo.coreapi.queries.OrderedJob;
import com.esipe.ing3.demo.coreapi.commands.ConfirmOrderCommand;
import com.esipe.ing3.demo.coreapi.commands.CreateOrderCommand;
import com.esipe.ing3.demo.coreapi.commands.CloseOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class OrderRestEndpoint {
 
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public OrderRestEndpoint(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    // Autowiring constructor and POST/GET endpoints



    @PostMapping("/create/{jobId}/name/{jobName}")
    public void createJobById(@PathVariable String jobId, @PathVariable String jobName) {
        commandGateway.send(commandGateway.send(new CreateOrderCommand(jobId, jobName)));
    }

//    @PostMapping("/ship-order")
//    public void shipOrder() {
//        String orderId = UUID.randomUUID().toString();
//        commandGateway.send(new CreateOrderCommand(orderId, "Deluxe Chair"));
//        commandGateway.send(new ConfirmOrderCommand(orderId));
//        commandGateway.send(new CloseOrderCommand(orderId));
//    }
//
    @GetMapping("/all-orders")
    public List<OrderedJob> findAllOrderedProducts() {
        return queryGateway.query(new FindAllOrderedProductsQuery(),
                ResponseTypes.multipleInstancesOf(OrderedJob.class)).join();
    }

}