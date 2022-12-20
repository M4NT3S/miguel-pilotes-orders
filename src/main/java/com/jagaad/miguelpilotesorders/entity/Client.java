package com.jagaad.miguelpilotesorders.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
@Entity
@Table(name = "CLIENTS")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="phone_number")
    private String telephoneNumber;

    @Column(name="email")
    private String email;

    @OneToMany(mappedBy = "client"  )
    private List<Order> orders;





}
