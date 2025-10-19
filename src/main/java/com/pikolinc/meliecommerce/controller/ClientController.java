package com.pikolinc.meliecommerce.controller;


import com.pikolinc.meliecommerce.domain.dto.client.ClientCreateDTO;
import com.pikolinc.meliecommerce.domain.dto.client.ClientResponseDTO;
import com.pikolinc.meliecommerce.domain.dto.client.ClientUpdateDTO;
import com.pikolinc.meliecommerce.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Operations related to Clients")
class ClientController {
    private final ClientService clientService;

    @GetMapping({"", "/"})
    @Operation(summary = "Get a page of clients", description = "Returns a page of registered clients")
    public ResponseEntity<List<ClientResponseDTO>> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(this.clientService.getAllClients(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single client", description = "Returns the client with the given id")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(this.clientService.getClientById(id));
    }

    @PostMapping({"", "/"})
    @Operation(summary = "Creates a new client", description = "Creates a new client with the given ClientCreateDTO as the request body")
    public ResponseEntity<ClientResponseDTO> addClient(@Valid @RequestBody ClientCreateDTO clientCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.clientService.addClient(clientCreateDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a client information", description = "Updates a new client with the given ClientUpdateDTO as the request body")
    public ResponseEntity<ClientResponseDTO> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody ClientUpdateDTO clientUpdateDTO
    ) {
        return ResponseEntity.ok(this.clientService.updateClient(id, clientUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a client", description = "Deletes the user with the given id")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        this.clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
