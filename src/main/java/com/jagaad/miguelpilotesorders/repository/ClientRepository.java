package com.jagaad.miguelpilotesorders.repository;

import com.jagaad.miguelpilotesorders.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNameContains(String wordSearched);
    List<Client> findBySurnameContains(String wordSearched);
    List<Client> findByTelephoneNumberContains(String wordSearched);
    List<Client> findByEmailContains(String wordSearched);


}
