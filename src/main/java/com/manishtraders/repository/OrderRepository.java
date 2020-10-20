package com.manishtraders.repository;

import com.manishtraders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    Order findByPartyName(String partyName);
    List<Order> findAllByUserId(String id);
}
