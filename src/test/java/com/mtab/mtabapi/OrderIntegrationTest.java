package com.mtab.mtabapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mtab.mtabapi.dto.OrderItemRequest;
import com.mtab.mtabapi.dto.OrderRequest;
import com.mtab.mtabapi.entity.Customer;
import com.mtab.mtabapi.entity.Item;
import com.mtab.mtabapi.repository.CustomerRepository;
import com.mtab.mtabapi.repository.ItemRepository;
import com.mtab.mtabapi.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class OrderIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void shouldPlaceOrderSuccessfully() throws Exception {
        // GIVEN 
        Customer savedCustomer = customerRepository.save(new Customer(null, "Jan Testowy", "jan@test.pl"));
        Item savedItem = itemRepository.save(new Item(null, "Laptop", 3000.00, null));

        // Przygotowujemy JSON z zamówieniem
        OrderRequest request = new OrderRequest();
        request.setCustomerId(savedCustomer.getId());

        OrderItemRequest itemReq = new OrderItemRequest();
        itemReq.setItemId(savedItem.getId());
        itemReq.setQuantity(2);
        request.setItems(List.of(itemReq));

        // WHEN
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))

        // THEN
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNumber());

        // THEN
        assertEquals(1, orderRepository.count(), "Powinno być 1 zamówienie w bazie");
    }

    @Test
    void shouldReturn400_WhenQuantityIsInvalid() throws Exception {
        //  GIVEN 
        OrderRequest request = new OrderRequest();
        request.setCustomerId(1L);

        OrderItemRequest itemReq = new OrderItemRequest();
        itemReq.setItemId(1L);
        itemReq.setQuantity(-5);
        request.setItems(List.of(itemReq));

        //  WHEN & THEN 
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}