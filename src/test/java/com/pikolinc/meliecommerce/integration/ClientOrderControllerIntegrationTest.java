package com.pikolinc.meliecommerce.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pikolinc.meliecommerce.domain.dto.order.OrderCreateForClientDTO;
import com.pikolinc.meliecommerce.domain.entity.Client;
import com.pikolinc.meliecommerce.domain.entity.Item;
import com.pikolinc.meliecommerce.repository.ClientRepository;
import com.pikolinc.meliecommerce.repository.ItemRepository;
import com.pikolinc.meliecommerce.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClientOrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void cleanDatabase() {
        orderRepository.deleteAll();
        clientRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    void testCreateOrderForClient_success() throws Exception {

        Client client = clientRepository.save(Client.builder()
                .name("Daniel Vargas")
                .age(20)
                .address("Home")
                .build());

        Item item = itemRepository.save(Item.builder()
                .name("Test item")
                .description("Expensive item")
                .price(1000.0)
                .build());

        OrderCreateForClientDTO dto = new OrderCreateForClientDTO(
                item.getId(),
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );


        mockMvc.perform(post("/api/v1/clients/{clientId}/orders", client.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clientResponseDTO.id").value(client.getId()))
                .andExpect(jsonPath("$.clientResponseDTO.name").value(client.getName()))
                .andExpect(jsonPath("$.clientResponseDTO.age").value(client.getAge()))
                .andExpect(jsonPath("$.clientResponseDTO.address").value(client.getAddress()))
                .andExpect(jsonPath("$.itemResponseDTO.id").value(item.getId()))
                .andExpect(jsonPath("$.itemResponseDTO.name").value(item.getName()))
                .andExpect(jsonPath("$.itemResponseDTO.price").value(item.getPrice()));
    }

    @Test
    void testGetOrdersByClientId_notFound() throws Exception {
        mockMvc.perform(get("/api/v1/clients/{clientId}/orders", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetOrdersWithInvalidClientId() throws Exception {
        mockMvc.perform(get("/api/v1/clients/{clientId}/orders", -5))
                .andExpect(status().isBadRequest()); // @Min(1) violation
    }

    @Test
    void testUpdateNonexistentOrder() throws Exception {
        Client client = clientRepository.save(Client.builder()
                .name("Alice")
                .age(30)
                .address("Address")
                .build());

        Item item = itemRepository.save(Item.builder()
                .name("Monitor")
                .description("4K Monitor")
                .price(400.0)
                .build());

        OrderCreateForClientDTO dto = new OrderCreateForClientDTO(
                item.getId(),
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        mockMvc.perform(put("/api/v1/clients/{clientId}/orders/{orderId}", client.getId(), 999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteNonexistentOrder() throws Exception {
        Client client = clientRepository.save(Client.builder()
                .name("Bob")
                .age(40)
                .address("Street")
                .build());

        mockMvc.perform(delete("/api/v1/clients/{clientId}/orders/{orderId}", client.getId(), 999))
                .andExpect(status().isNotFound());
    }
}
