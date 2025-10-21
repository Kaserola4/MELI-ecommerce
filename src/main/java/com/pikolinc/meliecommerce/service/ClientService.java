package com.pikolinc.meliecommerce.service;

import com.pikolinc.meliecommerce.domain.dto.client.ClientCreateDTO;
import com.pikolinc.meliecommerce.domain.dto.client.ClientResponseDTO;
import com.pikolinc.meliecommerce.domain.dto.client.ClientUpdateDTO;
import com.pikolinc.meliecommerce.domain.entity.Client;
import com.pikolinc.meliecommerce.exception.NotFoundException;
import com.pikolinc.meliecommerce.repository.ClientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for managing client-related operations.
 * <p>
 * Provides methods for creating, retrieving, updating, and deleting client records.
 * Includes helper methods for mapping between entity and DTO representations.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    /**
     * Retrieves all clients with pagination support.
     *
     * @param pageable pagination information.
     * @return a list of {@link ClientResponseDTO} objects representing the clients.
     */
    public List<ClientResponseDTO> getAllClients(Pageable pageable) {
        List<Client> clients = this.clientRepository.findAll(pageable).getContent();
        return clients.stream().map(ClientService::toResponseDTO).toList();
    }

    /**
     * Retrieves a client by its unique identifier.
     *
     * @param id the ID of the client to retrieve.
     * @return a {@link ClientResponseDTO} representing the client.
     * @throws NotFoundException if no client exists with the specified ID.
     */
    public ClientResponseDTO getClientById(Long id) {
        Client client = clientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found with id " + id));

        return toResponseDTO(client);
    }

    /**
     * Creates a new client record.
     *
     * @param clientCreateDTO the data transfer object containing the client's creation details.
     * @return a {@link ClientResponseDTO} representing the newly created client.
     */
    public ClientResponseDTO addClient(@Valid ClientCreateDTO clientCreateDTO) {
        Client client = toEntity(clientCreateDTO);
        Client savedClient = this.clientRepository.save(client);
        return toResponseDTO(savedClient);
    }

    /**
     * Updates an existing client's information.
     *
     * @param id               the ID of the client to update.
     * @param clientUpdateDTO  the data transfer object containing updated client details.
     * @return a {@link ClientResponseDTO} representing the updated client.
     * @throws NotFoundException if no client exists with the specified ID.
     */
    public ClientResponseDTO updateClient(Long id, @Valid ClientUpdateDTO clientUpdateDTO) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found with id " + id));

        client.setName(clientUpdateDTO.name());
        client.setAge(clientUpdateDTO.age());
        client.setAddress(clientUpdateDTO.address());

        Client updated = clientRepository.save(client);
        return toResponseDTO(updated);
    }

    /**
     * Deletes a client by its ID.
     *
     * @param id the ID of the client to delete.
     * @throws NotFoundException if no client exists with the specified ID.
     */
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found with id " + id));

        clientRepository.delete(client);
    }

    /**
     * Converts a {@link Client} entity to a {@link ClientResponseDTO}.
     *
     * @param client the entity to convert.
     * @return a {@link ClientResponseDTO} containing the client's public data.
     */
    public static ClientResponseDTO toResponseDTO(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getAddress(),
                client.getAge()
        );
    }

    /**
     * Converts a {@link ClientCreateDTO} to a {@link Client} entity.
     *
     * @param clientCreateDTO the DTO containing client creation data.
     * @return a new {@link Client} entity.
     */
    private Client toEntity(ClientCreateDTO clientCreateDTO) {
        return Client.builder()
                .name(clientCreateDTO.name())
                .address(clientCreateDTO.address())
                .age(clientCreateDTO.age())
                .build();
    }
}
