package com.mtab.mtabapi.controller;

import com.mtab.mtabapi.dto.OrderRequest;
import com.mtab.mtabapi.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> createOrder(@Valid @RequestBody OrderRequest orderRequest) {

        log.info("Received POST order request: {}", orderRequest);

        Long orderId = orderService.placeOrder(orderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }

}
