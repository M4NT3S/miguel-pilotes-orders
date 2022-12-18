package com.jagaad.miguelpilotesorders.controller;

import com.jagaad.miguelpilotesorders.payload.OrderResponse;
import com.jagaad.miguelpilotesorders.payload.request.OrderRequest;
import com.jagaad.miguelpilotesorders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/miguel-pilotes-orders/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/take")
    public ResponseEntity<OrderResponse> takeOrder(@RequestBody OrderRequest orderRequest) {
        return null;
    }

    @PostMapping("/update")
    public ResponseEntity<OrderResponse> updateOrder(@RequestBody OrderRequest orderRequest) {
        return null;
    }

    @PostMapping("/search")
    public ResponseEntity<OrderResponse> searchOrders(@RequestBody OrderRequest orderRequest) {
        return null;
    }
}
