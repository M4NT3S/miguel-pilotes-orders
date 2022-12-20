package com.jagaad.miguelpilotesorders.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {
    private String id;

    private String deliveryAddress;

    private int pilotesQuantity;

    private double orderTotal;
}
