package com.esipe.ing3.demo.endpoint;

import com.esipe.ing3.demo.coreapi.commands.CloseOrderCommand;
import com.esipe.ing3.demo.coreapi.commands.ConfirmOrderCommand;
import com.esipe.ing3.demo.coreapi.commands.CreateOrderCommand;
import com.esipe.ing3.demo.coreapi.commands.OpenOrderCommand;
import com.esipe.ing3.demo.coreapi.queries.FindAllOrderedJobQuery;
import com.esipe.ing3.demo.coreapi.queries.OrderedJob;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/open/{jobId}")
    public void openJob(@PathVariable String jobId) {
        commandGateway.send(commandGateway.send(new OpenOrderCommand(jobId)));
    }

    @PostMapping("/confirm/{jobId}")
    public void confirmJob(@PathVariable String jobId) {
        commandGateway.send(commandGateway.send(new ConfirmOrderCommand(jobId)));
    }

    @PostMapping("/close/{jobId}")
    public void closeJob(@PathVariable String jobId) {
        commandGateway.send(commandGateway.send(new CloseOrderCommand(jobId)));
    }

    @GetMapping("/all-orders")
    public List<OrderedJob> findAllOrderedProducts() {
        return queryGateway.query(new FindAllOrderedJobQuery(),
                ResponseTypes.multipleInstancesOf(OrderedJob.class)).join();
    }

}