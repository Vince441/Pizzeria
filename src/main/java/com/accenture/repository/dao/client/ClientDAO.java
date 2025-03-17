package com.accenture.repository.dao.client;

import com.accenture.repository.entity.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDAO extends JpaRepository<Client, Integer> {
}
