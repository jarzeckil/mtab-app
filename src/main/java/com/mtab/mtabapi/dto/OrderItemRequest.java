package com.mtab.mtabapi.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long itemId;
    private int quantity;
}
