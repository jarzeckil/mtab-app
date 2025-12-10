package com.mtab.mtabapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    @NotNull(message = "Item ID is required")
    private Long itemId;
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;
}
