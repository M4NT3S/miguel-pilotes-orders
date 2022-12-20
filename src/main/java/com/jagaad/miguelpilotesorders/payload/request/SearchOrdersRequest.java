package com.jagaad.miguelpilotesorders.payload.request;

import lombok.Data;

@Data
public class SearchOrdersRequest {
    String fieldSearchingFor;
    String valueToSearch;
}
