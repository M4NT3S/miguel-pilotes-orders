package com.jagaad.miguelpilotesorders.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ORDERS")
public class Order {
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    private String id;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "pilotes_quantity")
    private int pilotesQuantity;

    @Column(name = "order_total")
    private double orderTotal;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;
}
