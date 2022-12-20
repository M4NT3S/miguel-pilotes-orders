package com.jagaad.miguelpilotesorders;


import com.jagaad.miguelpilotesorders.entity.Client;
import com.jagaad.miguelpilotesorders.entity.Order;
import com.jagaad.miguelpilotesorders.repository.ClientRepository;
import com.jagaad.miguelpilotesorders.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;


@DataJpaTest
public class RepositoriesTests {

    private static final Logger log = LoggerFactory.getLogger(RepositoriesTests.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

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

    @Test
    public void insertClient(){
        Client clientTest = Client.builder()
                .name("Sebastian")
                .surname("Macchi")
                .telephoneNumber("phoneNumber")
                .email("sebastian@email.com")
                .build();
        clientRepository.save(clientTest);

        Order orderTest = Order.builder()
                .deliveryAddress("via Costantino Baroni 05")
                .pilotesQuantity(5)
                .orderTotal(50.00)
                .client(clientTest)
                .build();
        orderRepository.save(orderTest);

        System.out.println("THE ID GENERATED FOR THE ORDER ----->" + orderTest.getId());

    }

    @Test
    public void retrievingMultipleOrdersFromSpecificClient(){
        Client clientTest = Client.builder()
                .name("Sebastian")
                .surname("Macchi")
                .telephoneNumber("phoneNumber")
                .email("sebastian@email.com")
                .build();
        clientRepository.save(clientTest);

        Order orderTest = Order.builder()
                .deliveryAddress("via Costantino Baroni 05")
                .pilotesQuantity(5)
                .orderTotal(50.00)
                .client(clientTest)
                .build();
        orderRepository.save(orderTest);

        Order orderTest2 = Order.builder()
                .deliveryAddress("via Matteotti 05")
                .pilotesQuantity(10)
                .orderTotal(50.00)
                .client(clientTest)
                .build();
        orderRepository.save(orderTest2);

        List<Order> listOrdersClientTest = orderRepository.findByClientIdEquals(clientTest.getId());

        listOrdersClientTest.stream().forEach(order -> Assertions.assertThat(order.getClient().getId()).isEqualTo(clientTest.getId()));
    }





}
