package com.jagaad.miguelpilotesorders.payload.request;


import lombok.Data;

@Data
public class OrderUpdateRequest {
    String idOrderToUpdate;
    String orderDeliveryAddress;
    int pilotesQuantity;

}
