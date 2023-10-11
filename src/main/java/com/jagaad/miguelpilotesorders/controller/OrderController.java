package com.jagaad.miguelpilotesorders.controller;

import com.jagaad.miguelpilotesorders.payload.OrderUpdateResponse;
import com.jagaad.miguelpilotesorders.payload.SearchOrdersResponse;
import com.jagaad.miguelpilotesorders.payload.TakeOrderResponse;
import com.jagaad.miguelpilotesorders.payload.request.OrderUpdateRequest;
import com.jagaad.miguelpilotesorders.payload.request.SearchOrdersRequest;
import com.jagaad.miguelpilotesorders.payload.request.TakeOrderRequest;
import com.jagaad.miguelpilotesorders.service.OrderService;
import com.jagaad.miguelpilotesorders.validator.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/miguel-pilotes-orders/orders")
@CrossOrigin("http://localhost:4200")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    InputValidator inputValidator;

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);


    @PostMapping("/take")
    public ResponseEntity<TakeOrderResponse> takeOrder(@RequestBody TakeOrderRequest takeOrderRequest) {
        TakeOrderResponse takeOrderResponse = new TakeOrderResponse();
        if(inputValidator.validate(takeOrderRequest)) {
            log.info("received request to take order by client with email " + takeOrderRequest.getClient().getEmail());
            takeOrderResponse = orderService.takeOrder(takeOrderRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(takeOrderResponse);
        }
        takeOrderResponse.setStatusTakeOrderRequest(InputValidator.errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(takeOrderResponse);
    }

    @PostMapping("/update")
    public ResponseEntity<OrderUpdateResponse> updateOrder(@RequestBody OrderUpdateRequest orderUpdateRequest) {
        OrderUpdateResponse orderUpdateResponse = new OrderUpdateResponse();
        if(inputValidator.validate(orderUpdateRequest)) {
            log.info("received request to update order with id: " + orderUpdateRequest.getIdOrderToUpdate());
            orderUpdateResponse = orderService.updateOrder(orderUpdateRequest);
            return ResponseEntity.status(HttpStatus.OK).body(orderUpdateResponse);
        }
        orderUpdateResponse.setStatusOrderUpdateRequest(InputValidator.errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderUpdateResponse);
    }

    @PostMapping("/search")
    public ResponseEntity<SearchOrdersResponse> searchOrders(
            @RequestHeader("username") String username,
            @RequestHeader("password") String password,
            @RequestBody SearchOrdersRequest searchOrdersRequest) {
        SearchOrdersResponse searchOrdersResponse = new SearchOrdersResponse();
        if(inputValidator.validateCredentials(username, password)) {
            if(inputValidator.validate(searchOrdersRequest)){
                log.info("received request to search orders related to field: " + searchOrdersRequest.getFieldSearchingFor() + " and the value it's: " + searchOrdersRequest.getValueToSearch());
                searchOrdersResponse = orderService.searchOrders(searchOrdersRequest);
                return ResponseEntity.status(HttpStatus.OK).body(searchOrdersResponse);
            }
            searchOrdersResponse.setStatusSearchOrderRequest(InputValidator.errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(searchOrdersResponse);
        }
        searchOrdersResponse.setStatusSearchOrderRequest(InputValidator.errorMessage);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(searchOrdersResponse);
    }

}
