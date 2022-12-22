package com.jagaad.miguelpilotesorders.service;

import com.jagaad.miguelpilotesorders.controller.OrderController;
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
import com.jagaad.miguelpilotesorders.utils.Constants;
import com.jagaad.miguelpilotesorders.validator.TimeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
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
        log.info("starting take order method");
        Client clientRequestingOrder = clientMapper.DtoToEntity(takeOrderRequest.getClient());
        clientRepository.save(clientRequestingOrder);
        List<OrderDTO> orderDTOList = takeOrderRequest.getOrders();
        Timestamp timestampNow = new Timestamp(System.currentTimeMillis());
        List<OrderDTO> ordersInserted = new ArrayList<>();
        orderDTOList.stream().forEach(orderDTO -> {
            Order order = orderMapper.DtoToEntity(orderDTO);
            order.setOrderRequestTime(timestampNow);
            order.setClient(clientRequestingOrder);
            order.setOrderTotal(calculateOrderTotal(orderDTO.getPilotesQuantity()));
            orderRepository.save(order);
            ordersInserted.add(orderMapper.entityToDto(order));
        });
        String singleOrder = "The order was successfully inserted, you have 5 minutes, starting from now, " + timestampNow + " to make any changes";
        String multipleOrders = ordersInserted.size() + " orders were successfully inserted, you have 5 minutes, starting from now, " + timestampNow + " to make any changes";
        String statusOrderRequest = ordersInserted.size() > 1 ? multipleOrders : singleOrder;

        return TakeOrderResponse.builder()
                .statusTakeOrderRequest(statusOrderRequest)
                .ordersInserted(ordersInserted)
                .build();
    }

    public OrderUpdateResponse updateOrder(OrderUpdateRequest orderUpdateRequest) {
        log.info("starting update order method");
        try {
            Order order = orderRepository.findById(orderUpdateRequest.getIdOrderToUpdate()).get();
            Timestamp timestampNow = new Timestamp(System.currentTimeMillis());
            String statusOrderRequest = "The order with id " + order.getId() + " successfully updated";
            if (timeValidator.stillUpdatable(order.getOrderRequestTime(), timestampNow) && order != null) {
                if (orderUpdateRequest.getOrderDeliveryAddress() != null) {
                    order.setDeliveryAddress(orderUpdateRequest.getOrderDeliveryAddress());
                }

                if (orderUpdateRequest.getPilotesQuantity() != 0) {
                    order.setPilotesQuantity(orderUpdateRequest.getPilotesQuantity());
                    double newOrderPrice = calculateOrderTotal(orderUpdateRequest.getPilotesQuantity());
                    order.setOrderTotal(newOrderPrice);
                    statusOrderRequest += "and the new order total price is set at " + newOrderPrice;
                }
                if(orderUpdateRequest.getOrderDeliveryAddress() == null && orderUpdateRequest.getPilotesQuantity() == 0) {
                    statusOrderRequest = "No update was performed, no new value requested to update";
                }

            } else {
                statusOrderRequest = "The order wasn't updated due to time limit exceeding";
            }
            return OrderUpdateResponse.builder()
                    .statusOrderUpdateRequest(statusOrderRequest)
                    .build();

        } catch (NoSuchElementException e) {
            log.error("The order to update wasn't found in the database", e);
        }

        return OrderUpdateResponse.builder()
                .statusOrderUpdateRequest("the id used was invalid, no order with such id present in the database")
                .build();
    }

    public SearchOrdersResponse searchOrders(SearchOrdersRequest searchOrdersRequest) {
        log.info("starting search orders method");
        List<Order> ordersRetrieved = new ArrayList<>();
        List<OrderDTO> ordersRetrievedDTO = new ArrayList<>();
        List<Client> clientListSearched;
        String statusSearchOrderRequest = "The search was successfully completed";
        switch (searchOrdersRequest.getFieldSearchingFor().toLowerCase()) {
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
            case "telephonenumber":
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
        }

        if (ordersRetrieved.size() > 0) {
            ordersRetrieved.stream().forEach(order -> {
                ordersRetrievedDTO.add(orderMapper.entityToDto(order));
            });
        } else {
            statusSearchOrderRequest = "No results were found";
        }


        return SearchOrdersResponse.builder()
                .statusSearchOrderRequest(statusSearchOrderRequest)
                .listOrdersSearched(ordersRetrievedDTO)
                .build();


    }

    public double calculateOrderTotal(int pilotesQuantity) {
        return Math.floor((Constants.SINGLE_PILOTE_PRICE * pilotesQuantity) * 100) / 100;
    }
}
