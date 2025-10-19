package com.pikolinc.meliecommerce.controller;


import com.pikolinc.meliecommerce.domain.dto.client.ClientCreateDTO;
import com.pikolinc.meliecommerce.domain.dto.client.ClientResponseDTO;
import com.pikolinc.meliecommerce.domain.dto.client.ClientUpdateDTO;
import com.pikolinc.meliecommerce.service.ClientService;
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
class ClientController {
    private final ClientService clientService;

    @GetMapping({"", "/"})
    public ResponseEntity<List<ClientResponseDTO>> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(this.clientService.getAllClients(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(this.clientService.getClientById(id));
    }

    @PostMapping({"", "/"})
    public ResponseEntity<ClientResponseDTO> addClient(@Valid @RequestBody ClientCreateDTO clientCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.clientService.addClient(clientCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody ClientUpdateDTO clientUpdateDTO
    ) {
        return ResponseEntity.ok(this.clientService.updateClient(id, clientUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        this.clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
