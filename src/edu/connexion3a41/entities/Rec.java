/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.entities;

/**
 *
 * @author karra
 */
public class Rec {

    public Rec() {
    }

    public Rec(String nom, String prenom, String email, String description, String type) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.description = description;
        this.type = type;
    }
    
    private    int id ;
    private String nom;
           private String prenom;
           private         String email; 
           private                 String description;
           private                         String type;

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Rec(int id, String nom, String prenom, String email,String description ,String type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.description=description;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Rec{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", description=" + description + ", type=" + type + '}';
    }
    
}