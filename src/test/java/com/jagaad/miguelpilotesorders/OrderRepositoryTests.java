package com.jagaad.miguelpilotesorders;


import com.jagaad.miguelpilotesorders.model.Client;
import com.jagaad.miguelpilotesorders.model.Order;
import com.jagaad.miguelpilotesorders.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;


@DataJpaTest
public class OrderRepositoryTests {

    private static final Logger log = LoggerFactory.getLogger(OrderRepositoryTests.class);

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void insertOrder(){
        Client clientTest = Client.builder().build();
        Order orderTest = Order.builder()
                .deliveryAddress("via Costantino Baroni 05")
                .pilotesQuantity(5)
                .orderTotal(50.00)
                .client(clientTest)
                .build();

        orderRepository.save(orderTest);
        log.info("The ID of the testing order just created it's: " + orderTest.getId());
        Assertions.assertThat(orderTest.getId()).isNotEmpty();
    }

   @Test
    public void retrieveOrderFromSpecificId(){
       Client clientTest = Client.builder().build();
       Order orderTest = Order.builder()
               .deliveryAddress("via Costantino Baroni 05")
               .pilotesQuantity(5)
               .orderTotal(50.00)
               .client(clientTest)
               .build();
        orderRepository.save(orderTest);
        Order retrievingOrderTest = orderRepository.findById(orderTest.getId()).get();
        log.info(retrievingOrderTest.getId());
       Assertions.assertThat(retrievingOrderTest).isNotNull();
    }

    @Test
    public void retrieveAllOrders() {
        Client clientTest = Client.builder().build();
        Order orderTest = Order.builder()
                .deliveryAddress("via Costantino Baroni 05")
                .pilotesQuantity(5)
                .orderTotal(50.00)
                .client(clientTest)
                .build();
        orderRepository.save(orderTest);
        List<Order> allOrdersTest = orderRepository.findAll();
        Assertions.assertThat(allOrdersTest).hasSizeGreaterThan(0);
    }

    @Test
    public void updateOrder(){
        Client clientTest = Client.builder().build();
        Order insertingOrderTest = Order.builder()
                .deliveryAddress("via Costantino Baroni 05")
                .pilotesQuantity(5)
                .orderTotal(50.00)
                .client(clientTest)
                .build();
        orderRepository.save(insertingOrderTest);
        Order retrievingOrderTest = orderRepository.findById(insertingOrderTest.getId()).get();
        retrievingOrderTest.setPilotesQuantity(15);
        Assertions.assertThat(orderRepository.findById(insertingOrderTest.getId()).get().getPilotesQuantity()).isEqualTo(15);

    }



}
