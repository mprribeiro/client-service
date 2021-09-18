package com.mprribeiro.clientservice.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mprribeiro.clientservice.dto.ClientDTO;
import com.mprribeiro.clientservice.entities.Client;
import com.mprribeiro.clientservice.repositories.ClientRepository;
import com.mprribeiro.clientservice.services.exceptions.DatabaseIntegrityException;
import com.mprribeiro.clientservice.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found for id " + id));
		return new ClientDTO(client);
	}

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(PageRequest pageRequest) {
		var clients = clientRepository.findAll(pageRequest);
		return clients.map(client -> new ClientDTO(client));
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		var client = new Client();
		copyDTOToEntity(dto, client);
		client = clientRepository.save(client);
		return new ClientDTO(client);
	}

	public void delete(Long id) {
		try {
			clientRepository.deleteById(id);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entity not found for id " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseIntegrityException("Id " + id + " is associated to other entity");
		}
		
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			var client = clientRepository.getOne(id);
			copyDTOToEntity(dto, client);
			client = clientRepository.save(client);
			return new ClientDTO(client);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entity not found for id " + id);
		}
	}

	private void copyDTOToEntity(ClientDTO dto, Client client) {
		client.setName(dto.getName());
		client.setCpf(dto.getCpf());
		client.setIncome(dto.getIncome());
		client.setBirthDate(dto.getBirthDate());
		client.setChildren(dto.getChildren());
	}
}
