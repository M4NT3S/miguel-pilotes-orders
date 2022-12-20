package com.jagaad.miguelpilotesorders.payload;

import com.jagaad.miguelpilotesorders.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchOrdersResponse {

    String statusSearchOrderRequest;

    List<OrderDTO> listOrdersSearched;
}
