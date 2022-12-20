package com.jagaad.miguelpilotesorders.payload.request;

import com.jagaad.miguelpilotesorders.entity.Client;
import com.jagaad.miguelpilotesorders.entity.Order;

import java.util.List;

public class TakeOrderRequest {
    Client client;
    List<Order> orders;
}
