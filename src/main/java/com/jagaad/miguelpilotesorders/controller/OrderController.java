package com.jagaad.miguelpilotesorders.controller;

import com.jagaad.miguelpilotesorders.payload.OrderUpdateResponse;
import com.jagaad.miguelpilotesorders.payload.SearchOrdersResponse;
import com.jagaad.miguelpilotesorders.payload.TakeOrderResponse;
import com.jagaad.miguelpilotesorders.payload.request.OrderUpdateRequest;
import com.jagaad.miguelpilotesorders.payload.request.SearchOrdersRequest;
import com.jagaad.miguelpilotesorders.payload.request.TakeOrderRequest;
import com.jagaad.miguelpilotesorders.service.OrderService;
import com.jagaad.miguelpilotesorders.validator.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ResourceBundle;

@RestController
@RequestMapping("/miguel-pilotes-orders/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    InputValidator inputValidator;

    @PostMapping("/take")
    public ResponseEntity<TakeOrderResponse> takeOrder(@RequestBody TakeOrderRequest takeOrderRequest) {
        TakeOrderResponse takeOrderResponse = new TakeOrderResponse();
        if(inputValidator.validate(takeOrderRequest)) {
            takeOrderResponse = orderService.takeOrder(takeOrderRequest);
            return ResponseEntity.ok().body(takeOrderResponse);
        }
        takeOrderResponse.setStatusTakeOrderRequest(InputValidator.errorMessage);
        return ResponseEntity.ok().body(takeOrderResponse);
    }

    @PostMapping("/update")
    public ResponseEntity<OrderUpdateResponse> updateOrder(@RequestBody OrderUpdateRequest orderUpdateRequest) {
        OrderUpdateResponse orderUpdateResponse = new OrderUpdateResponse();
        if(inputValidator.validate(orderUpdateRequest)) {
            orderUpdateResponse = orderService.updateOrder(orderUpdateRequest);
            return ResponseEntity.ok().body(orderUpdateResponse);
        }
        orderUpdateResponse.setStatusOrderUpdateRequest(InputValidator.errorMessage);
        return ResponseEntity.ok().body(orderUpdateResponse);
    }

    @PostMapping("/search")
    public ResponseEntity<SearchOrdersResponse> searchOrders(@RequestBody SearchOrdersRequest searchOrdersRequest) {
        SearchOrdersResponse searchOrdersResponse = new SearchOrdersResponse();
        if(inputValidator.validate(searchOrdersRequest)) {
            searchOrdersResponse = orderService.searchOrders(searchOrdersRequest);
            return ResponseEntity.ok().body(searchOrdersResponse);
        }
        searchOrdersResponse.setStatusSearchOrderRequest(InputValidator.errorMessage);
        return ResponseEntity.ok().body(searchOrdersResponse);
    }

}
