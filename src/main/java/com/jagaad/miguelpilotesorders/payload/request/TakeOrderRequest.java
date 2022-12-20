package com.jagaad.miguelpilotesorders.payload.request;

import com.jagaad.miguelpilotesorders.dto.ClientDTO;
import com.jagaad.miguelpilotesorders.dto.OrderDTO;
import lombok.Data;

import java.util.List;

@Data
public class TakeOrderRequest {
    ClientDTO client;
    List<OrderDTO> orders;
}
