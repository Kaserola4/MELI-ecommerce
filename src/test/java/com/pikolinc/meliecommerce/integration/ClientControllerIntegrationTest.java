package com.pikolinc.meliecommerce.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pikolinc.meliecommerce.domain.dto.client.ClientCreateDTO;
import com.pikolinc.meliecommerce.domain.dto.client.ClientUpdateDTO;
import com.pikolinc.meliecommerce.domain.entity.Client;
import com.pikolinc.meliecommerce.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void cleanDatabase() {
        clientRepository.deleteAll();
    }

    // -----------------------------
    // Success cases
    // -----------------------------
    @Test
    void testAddClient_success() throws Exception {
        ClientCreateDTO dto = new ClientCreateDTO("Daniel Vargas", "Street 5", 23);

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(dto.name()))
                .andExpect(jsonPath("$.address").value(dto.address()))
                .andExpect(jsonPath("$.age").value(dto.age()));
    }

    @Test
    void testGetClientById_success() throws Exception {
        Client client = clientRepository.save(
                Client.builder()
                        .name("Alice")
                        .address("Street A")
                        .age(30)
                        .build());

        mockMvc.perform(get("/api/v1/clients/{id}", client.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(client.getId()))
                .andExpect(jsonPath("$.name").value(client.getName()));
    }

    @Test
    void testGetAllClients_success() throws Exception {
        clientRepository.save(Client.builder()
                .name("Bob")
                .address("Street B")
                .age(25)
                .build()
        );
        clientRepository.save(Client.builder()
                .name("Carol")
                .address("Street C")
                .age(28)
                .build()
        );

        mockMvc.perform(get("/api/v1/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testUpdateClient_success() throws Exception {
        Client client = clientRepository.save(Client.builder()
                .name("David")
                .address("Old Street")
                .age(35)
                .build()
        );

        ClientUpdateDTO dto = new ClientUpdateDTO("David Updated", "New Street", 36);

        mockMvc.perform(put("/api/v1/clients/{id}", client.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(dto.name()))
                .andExpect(jsonPath("$.address").value(dto.address()))
                .andExpect(jsonPath("$.age").value(dto.age()));
    }

    @Test
    void testDeleteClient_success() throws Exception {
        Client client = clientRepository.save(Client.builder()
                .name("Eve")
                .address("Street X")
                .age(22)
                .build());

        mockMvc.perform(delete("/api/v1/clients/{id}", client.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/clients/{id}", client.getId()))
                .andExpect(status().isNotFound());
    }

    // -----------------------------
    // Edge / failure cases
    // -----------------------------
    @Test
    void testGetClientById_notFound() throws Exception {
        mockMvc.perform(get("/api/v1/clients/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateNonexistentClient() throws Exception {
        ClientUpdateDTO dto = new ClientUpdateDTO("Ghost", "Nowhere", 50);

        mockMvc.perform(put("/api/v1/clients/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteNonexistentClient() throws Exception {
        mockMvc.perform(delete("/api/v1/clients/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddClient_invalidBody() throws Exception {
        // Missing name and age
        String invalidJson = "{\"address\":\"Street Y\"}";

        mockMvc.perform(post("/api/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateClient_invalidBody() throws Exception {
        Client client = clientRepository.save(Client.builder()
                .name("Frank")
                .address("Old Street")
                .age(40)
                .build());

        // Empty name
        String invalidJson = "{\"name\":\"\",\"address\":\"New Street\",\"age\":45}";

        mockMvc.perform(put("/api/v1/clients/{id}", client.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllClients_emptyPage() throws Exception {
        mockMvc.perform(get("/api/v1/clients?page=10&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
