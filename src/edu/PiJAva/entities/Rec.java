/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.entities;

import edu.PiJAva.services.ServiceUser;

/**
 *
 * @author karra
 */
public class Rec {
ServiceUser U =new ServiceUser();

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
    private    int id_user ;

    private String nom;
           private String prenom;
           private         String email; 
           private                 String description;
           private                         String type;

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return U.getById(id_user).getLastname();
    }

    public String getPrenom() {
        return U.getById(id_user).getName();
    }

    public String getEmail() {
        return U.getById(id_user).getEmail();
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
        this.nom = U.getById(id_user).getName();
    }

    public void setPrenom(String prenom) {
        this.prenom = U.getById(id_user).getLastname();
    }

    public void setEmail(String email) {
        this.email = U.getById(id_user).getEmail();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Rec(int id,int id_user, String nom, String prenom, String email,String description ,String type) {
        this.id = id;
        this.id_user = id_user;

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.description=description;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Rec{" + "id=" + id + ", id_user=" + id_user + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", description=" + description + ", type=" + type + '}';
    }

   
    
}