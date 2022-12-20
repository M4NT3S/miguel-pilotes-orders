package com.jagaad.miguelpilotesorders.service;

import com.jagaad.miguelpilotesorders.dto.OrderDTO;
import com.jagaad.miguelpilotesorders.entity.Client;
import com.jagaad.miguelpilotesorders.entity.Order;
import com.jagaad.miguelpilotesorders.mapper.ClientMapper;
import com.jagaad.miguelpilotesorders.mapper.OrderMapper;
import com.jagaad.miguelpilotesorders.payload.OrderUpdateResponse;
import com.jagaad.miguelpilotesorders.payload.SearchOrdersResponse;
import com.jagaad.miguelpilotesorders.payload.TakeOrderResponse;
import com.jagaad.miguelpilotesorders.payload.request.OrderUpdateRequest;
import com.jagaad.miguelpilotesorders.payload.request.SearchOrdersRequest;
import com.jagaad.miguelpilotesorders.payload.request.TakeOrderRequest;
import com.jagaad.miguelpilotesorders.repository.ClientRepository;
import com.jagaad.miguelpilotesorders.repository.OrderRepository;
import com.jagaad.miguelpilotesorders.validator.TimeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ClientMapper clientMapper;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TimeValidator timeValidator;

    public TakeOrderResponse takeOrder(TakeOrderRequest takeOrderRequest) {

        Client clientRequestingOrder = clientMapper.DtoToEntity(takeOrderRequest.getClient());
        clientRepository.save(clientRequestingOrder);
        List<OrderDTO> orderDTOList = takeOrderRequest.getOrders();
        Timestamp timestampNow = new Timestamp(System.currentTimeMillis());
        List<OrderDTO> ordersInserted = new ArrayList<>();
        orderDTOList.stream().forEach(orderDTO -> {
                Order order = orderMapper.DtoToEntity(orderDTO);
                order.setOrderRequestTime(timestampNow);
                order.setClient(clientRequestingOrder);
                orderRepository.save(order);
                ordersInserted.add(orderMapper.entityToDto(order));
        });
        String singleOrder = "The order was successfully inserted, you have 5 minutes, starting from now, "+ timestampNow + " to make any changes";
        String multipleOrders = ordersInserted.size() + " orders were successfully inserted, you have 5 minutes, starting from now, " + timestampNow + "to make any changes";
        String statusOrderRequest = ordersInserted.size() > 1 ? multipleOrders : singleOrder;

        return TakeOrderResponse.builder()
                .statusTakeOrderRequest(statusOrderRequest)
                .ordersInserted(ordersInserted)
                .build();
    }

    public OrderUpdateResponse updateOrder(OrderUpdateRequest orderUpdateRequest) {
        Order order = orderRepository.findById(orderUpdateRequest.getIdOrderToUpdate()).get();
        Timestamp timestampNow = new Timestamp(System.currentTimeMillis());
        if(timeValidator.stillUpdatable(order.getOrderRequestTime(), timestampNow)) {
            if(orderUpdateRequest.getOrderDelivery() != null) {
                order.setDeliveryAddress(orderUpdateRequest.getOrderDelivery());
            }
            if(orderUpdateRequest.getPilotesQuantity() != 0) {
                order.setPilotesQuantity(orderUpdateRequest.getPilotesQuantity());
            }
            return OrderUpdateResponse.builder()
                    .statusOrderUpdateRequest("The order with id " + order.getId() +" successfully updated")
                    .build();
        } else {
            return OrderUpdateResponse.builder()
                    .statusOrderUpdateRequest("The order wasn't updated due to time limit exceeding")
                    .build();
        }

    }

    public SearchOrdersResponse searchOrders(SearchOrdersRequest searchOrdersRequest){
        List<Order> ordersRetrieved = new ArrayList<>();
        List<OrderDTO> ordersRetrievedDTO = new ArrayList<>();
        List<Client> clientListSearched;
        String statusSearchOrderRequest = "The search was successfully completed";
        switch(searchOrdersRequest.getFieldSearchingFor()) {
            case "name":
                clientListSearched = clientRepository.findByNameContains(searchOrdersRequest.getValueToSearch());
                clientListSearched.stream().forEach(client -> {
                    ordersRetrieved.addAll(orderRepository.findByClientIdEquals(client.getId()));
                });
                break;
            case "surname":
                clientListSearched = clientRepository.findBySurnameContains(searchOrdersRequest.getValueToSearch());
                clientListSearched.stream().forEach(client -> {
                    ordersRetrieved.addAll(orderRepository.findByClientIdEquals(client.getId()));
                });
                break;
            case  "telephoneNumber":
                clientListSearched = clientRepository.findByTelephoneNumberContains(searchOrdersRequest.getValueToSearch());
                clientListSearched.stream().forEach(client -> {
                    ordersRetrieved.addAll(orderRepository.findByClientIdEquals(client.getId()));
                });
                break;
            case "email":
                clientListSearched = clientRepository.findByEmailContains(searchOrdersRequest.getValueToSearch());
                clientListSearched.stream().forEach(client -> {
                    ordersRetrieved.addAll(orderRepository.findByClientIdEquals(client.getId()));
                });
                break;
            default:
                statusSearchOrderRequest = "The search failed, the field chosen to search for wasn't valid";
        }

        ordersRetrieved.stream().forEach(order -> {
            ordersRetrievedDTO.add(orderMapper.entityToDto(order));
        });

        return SearchOrdersResponse.builder()
                .statusSearchOrderRequest(statusSearchOrderRequest)
                .listOrdersSearched(ordersRetrievedDTO)
                .build();
    }
}
