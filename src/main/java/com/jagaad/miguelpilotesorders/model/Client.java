package com.jagaad.miguelpilotesorders.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "CLIENTS")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="telephone_number")
    private String telephoneNumber;

    @Column(name="email")
    private String email;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;





}
