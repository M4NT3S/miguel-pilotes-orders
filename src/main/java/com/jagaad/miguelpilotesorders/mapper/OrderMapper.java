package com.jagaad.miguelpilotesorders.mapper;

import com.jagaad.miguelpilotesorders.dto.OrderDTO;
import com.jagaad.miguelpilotesorders.entity.Order;
import org.mapstruct.Mapper;


@Mapper
public interface OrderMapper {
    public OrderDTO entityToDto(Order order);
    public Order DtoToEntity(OrderDTO orderDTO);

}
