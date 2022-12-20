package com.jagaad.miguelpilotesorders;

import com.jagaad.miguelpilotesorders.dto.ClientDTO;
import com.jagaad.miguelpilotesorders.dto.OrderDTO;
import com.jagaad.miguelpilotesorders.entity.Client;
import com.jagaad.miguelpilotesorders.entity.Order;
import com.jagaad.miguelpilotesorders.mapper.ClientMapper;
import com.jagaad.miguelpilotesorders.mapper.ClientMapperImpl;
import com.jagaad.miguelpilotesorders.mapper.OrderMapper;
import com.jagaad.miguelpilotesorders.mapper.OrderMapperImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ClientMapperImpl.class, OrderMapperImpl.class})
public class MapperTests {

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void clientEntityToDto(){
        Client clientTest = Client.builder()
                .name("Miguel")
                .surname("Montoro")
                .email("elgranmiguelmontoro@pilotes.com")
                .telephoneNumber("phoneNumber")
                .build();

        ClientDTO clientDTOTest = clientMapper.entityToDto(clientTest);
        Assertions.assertThat(clientDTOTest.getName()).isEqualTo(clientTest.getName());
        Assertions.assertThat(clientDTOTest.getSurname()).isEqualTo(clientTest.getSurname());
        Assertions.assertThat(clientDTOTest.getTelephoneNumber()).isEqualTo(clientTest.getTelephoneNumber());
        Assertions.assertThat(clientDTOTest.getEmail()).isEqualTo(clientTest.getEmail());
    }

    @Test
    public void clientDtoToEntity(){
        ClientDTO clientDTOTest = ClientDTO.builder()
                .name("Miguel")
                .surname("Montoro")
                .email("elgranmiguelmontoro@pilotes.com")
                .telephoneNumber("phoneNumber")
                .build();

        Client clientTest = clientMapper.DtoToEntity(clientDTOTest);

        Assertions.assertThat(clientTest.getName()).isEqualTo(clientDTOTest.getName());
        Assertions.assertThat(clientTest.getSurname()).isEqualTo(clientDTOTest.getSurname());
        Assertions.assertThat(clientTest.getTelephoneNumber()).isEqualTo(clientDTOTest.getTelephoneNumber());
        Assertions.assertThat(clientTest.getEmail()).isEqualTo(clientDTOTest.getEmail());
    }

    @Test
    public void orderEntityToDto(){
        Order orderTest = Order.builder()
                .deliveryAddress("via Costantino Baroni 05")
                .pilotesQuantity(5)
                .orderTotal(50.00)
                .build();

        OrderDTO orderDTOTest = orderMapper.entityToDto(orderTest);

        Assertions.assertThat(orderDTOTest.getId()).isEqualTo(orderTest.getId());
        Assertions.assertThat(orderDTOTest.getDeliveryAddress()).isEqualTo(orderTest.getDeliveryAddress());
        Assertions.assertThat(orderDTOTest.getPilotesQuantity()).isEqualTo(orderTest.getPilotesQuantity());
        Assertions.assertThat(orderDTOTest.getOrderTotal()).isEqualTo(orderTest.getOrderTotal());

    }

    @Test
    public void orderDtoToEntity(){
        OrderDTO orderDtoTest = OrderDTO.builder()
                .deliveryAddress("via Costantino Baroni 05")
                .pilotesQuantity(5)
                .orderTotal(50.00)
                .build();

        Order orderTest = orderMapper.DtoToEntity(orderDtoTest);

        Assertions.assertThat(orderTest.getId()).isEqualTo(orderDtoTest.getId());
        Assertions.assertThat(orderTest.getDeliveryAddress()).isEqualTo(orderDtoTest.getDeliveryAddress());
        Assertions.assertThat(orderTest.getPilotesQuantity()).isEqualTo(orderDtoTest.getPilotesQuantity());
        Assertions.assertThat(orderTest.getOrderTotal()).isEqualTo(orderDtoTest.getOrderTotal());
    }


}
