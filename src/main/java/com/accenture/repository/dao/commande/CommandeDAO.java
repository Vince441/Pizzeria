package com.accenture.repository.dao.commande;

import com.accenture.repository.entity.commande.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeDAO extends JpaRepository<Commande, Integer> {
}
