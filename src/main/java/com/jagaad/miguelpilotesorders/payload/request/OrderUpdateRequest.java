package com.jagaad.miguelpilotesorders.payload.request;

import lombok.Data;

@Data
public class OrderUpdateRequest {
    String idOrderToUpdate;
    String orderDelivery;
    int pilotesQuantity;

}
