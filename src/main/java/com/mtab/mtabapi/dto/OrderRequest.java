package com.mtab.mtabapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    @NotEmpty(message = "There must be at least one item")
    private List<@Valid OrderItemRequest> items;
}
