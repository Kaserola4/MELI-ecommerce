package com.pikolinc.meliecommerce.service;

import com.pikolinc.meliecommerce.domain.dto.client.ClientCreateDTO;
import com.pikolinc.meliecommerce.domain.dto.client.ClientResponseDTO;
import com.pikolinc.meliecommerce.domain.dto.client.ClientUpdateDTO;
import com.pikolinc.meliecommerce.domain.dto.item.ItemCreateDTO;
import com.pikolinc.meliecommerce.domain.dto.item.ItemResponseDTO;
import com.pikolinc.meliecommerce.domain.entity.Client;
import com.pikolinc.meliecommerce.domain.entity.Item;
import com.pikolinc.meliecommerce.exception.NotFoundException;
import com.pikolinc.meliecommerce.repository.ClientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<ClientResponseDTO> getAllClients(Pageable pageable) {
        List<Client> clients = this.clientRepository.findAll(pageable).getContent();
        return clients.stream().map(ClientService::toResponseDTO).toList();
    }

    public ClientResponseDTO getClientById(Long id) {
        Client client = clientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found with id " + id));

        return toResponseDTO(client);
    }

    public ClientResponseDTO addClient(@Valid ClientCreateDTO clientCreateDTO) {
        Client client = toEntity(clientCreateDTO);

        Client savedClient = this.clientRepository.save(client);

        return toResponseDTO(savedClient);
    }

    public ClientResponseDTO updateClient(Long id, @Valid ClientUpdateDTO clientUpdateDTO) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found with id " + id));

        client.setName(clientUpdateDTO.name());
        client.setAge(clientUpdateDTO.age());
        client.setAddress(clientUpdateDTO.address());

        Client updated = clientRepository.save(client);

        return toResponseDTO(updated);
    }

    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found with id " + id));

        clientRepository.delete(client);
    }

    public static ClientResponseDTO toResponseDTO(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getName(),
                client.getAddress(),
                client.getAge()
        );
    }

    private Client toEntity(ClientCreateDTO clientCreateDTO) {
        return Client.builder()
                .name(clientCreateDTO.name())
                .address(clientCreateDTO.address())
                .age(clientCreateDTO.age())
                .build();
    }
}
