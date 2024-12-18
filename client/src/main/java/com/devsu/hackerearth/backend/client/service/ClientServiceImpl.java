package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.converter.ClientConverter;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final ClientConverter clientConverter;

	public ClientServiceImpl(ClientRepository clientRepository, ClientConverter clientConverter) {
		this.clientRepository = clientRepository;
		this.clientConverter = clientConverter;
	}

	@Override
	public List<ClientDto> getAll() {
		// Get all clients
		return clientRepository.findAll().stream().map(clientConverter::convert).collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) {
		return clientConverter.convert(getClient(id));
	}

	private Client getClient(Long id) {
		return clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found."));
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		Client client = new Client();
		client.setActive(clientDto.isActive());
		client.setPassword(clientDto.getPassword());
		client.setAddress(clientDto.getAddress());
		client.setDni(clientDto.getDni());
		client.setGender(clientDto.getGender());
		client.setName(clientDto.getName());
		client.setAge(clientDto.getAge());
		client.setPhone(clientDto.getPhone());
		return clientConverter.convert(clientRepository.save(client));
	}

	@Override
	public ClientDto update(Long id, ClientDto clientDto) {
		Client client = getClient(id);
		client.setActive(clientDto.isActive());
		client.setPassword(clientDto.getPassword());
		client.setAddress(clientDto.getAddress());
		client.setDni(clientDto.getDni());
		client.setGender(clientDto.getGender());
		client.setName(clientDto.getName());
		client.setPhone(clientDto.getPhone());
		return clientConverter.convert(clientRepository.save(client));
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		Client client = getClient(id);
		client.setActive(partialClientDto.isActive());
		return clientConverter.convert(clientRepository.save(client));
	}

	@Override
	public void deleteById(Long id) {
		clientRepository.deleteById(id);
	}
}
