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

/**
 * REST controller that manages client-related operations.
 * <p>
 * Provides endpoints to retrieve, create, update, and delete clients.
 * </p>
 */
@RestController
@RequestMapping("api/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Operations related to Clients")
public class ClientController {

    private final ClientService clientService;

    /**
     * Retrieves a paginated list of clients.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of clients per page (default is 10)
     * @return a {@link ResponseEntity} containing a list of {@link ClientResponseDTO}
     */
    @GetMapping({"", "/"})
    @Operation(summary = "Get a page of clients", description = "Returns a page of registered clients")
    public ResponseEntity<List<ClientResponseDTO>> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.clientService.getAllClients(pageable));
    }

    /**
     * Retrieves a single client by its ID.
     *
     * @param id the unique identifier of the client
     * @return a {@link ResponseEntity} containing the {@link ClientResponseDTO}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a single client", description = "Returns the client with the given id")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(this.clientService.getClientById(id));
    }

    /**
     * Creates a new client.
     *
     * @param clientCreateDTO the data transfer object containing client creation details
     * @return a {@link ResponseEntity} containing the created {@link ClientResponseDTO} with HTTP status 201
     */
    @PostMapping({"", "/"})
    @Operation(summary = "Create a new client", description = "Creates a new client with the given ClientCreateDTO as the request body")
    public ResponseEntity<ClientResponseDTO> addClient(@Valid @RequestBody ClientCreateDTO clientCreateDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.clientService.addClient(clientCreateDTO));
    }

    /**
     * Updates an existing client's information.
     *
     * @param id              the unique identifier of the client
     * @param clientUpdateDTO the data transfer object containing updated client details
     * @return a {@link ResponseEntity} containing the updated {@link ClientResponseDTO}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update client information", description = "Updates an existing client with the given ClientUpdateDTO as the request body")
    public ResponseEntity<ClientResponseDTO> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientUpdateDTO clientUpdateDTO
    ) {
        return ResponseEntity.ok(this.clientService.updateClient(id, clientUpdateDTO));
    }

    /**
     * Deletes a client by its ID.
     *
     * @param id the unique identifier of the client
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a client", description = "Deletes the client with the given id")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        this.clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
