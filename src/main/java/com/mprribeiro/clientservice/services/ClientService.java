package com.mprribeiro.clientservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mprribeiro.clientservice.dto.ClientDTO;
import com.mprribeiro.clientservice.repositories.ClientRepository;
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
}
