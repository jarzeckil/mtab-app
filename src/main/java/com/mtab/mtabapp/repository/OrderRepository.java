package com.mtab.mtabapp.repository;

import com.mtab.mtabapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
