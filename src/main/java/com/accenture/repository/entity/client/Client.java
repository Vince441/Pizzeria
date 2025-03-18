package com.accenture.repository.entity.client;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private Integer totalAchat;

    public Client(String nom, String prenom, String email, Integer totalAchat) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.totalAchat = totalAchat;
    }
}
