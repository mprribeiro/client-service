package com.mprribeiro.clientservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mprribeiro.clientservice.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
