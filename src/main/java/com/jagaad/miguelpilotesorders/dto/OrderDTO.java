package com.jagaad.miguelpilotesorders.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String id;

    private String deliveryAddress;

    private int pilotesQuantity;

    private double orderTotal;

}
