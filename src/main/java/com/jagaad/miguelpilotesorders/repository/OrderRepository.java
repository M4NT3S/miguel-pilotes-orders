package com.jagaad.miguelpilotesorders.repository;

import com.jagaad.miguelpilotesorders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    public List<Order> findByClientIdEquals(Long searchedClientId);
}
