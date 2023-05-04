/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.entities;

import edu.PiJAva.services.ServiceUser;
import java.util.Date;

/**
 *
 * @author EXPERT
 */
public class Constat {
    private int id;
    private int user_id;
    private String nom;
    private String prenom;
    private String matricule;
    private String lieu;
    private Date date;
    private String image_degats;

    ServiceUser U =new ServiceUser();
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    
    



    public Constat() {
    }

    public Constat(int id, int user_id, String nom, String prenom, String matricule, String lieu, Date date, String image_degats) {
        this.id = id;
        this.user_id = user_id;
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.lieu = lieu;
        this.date = date;
        this.image_degats = image_degats;
    }

    public Constat(int user_id, String nom, String prenom, String matricule, String lieu, Date date, String image_degats) {
        this.user_id = user_id;
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.lieu = lieu;
        this.date = date;
        this.image_degats = image_degats;
    }

  

    

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return U.getById(user_id).getLastname();
    }

    public void setNom(String nom) {
        this.nom = U.getById(user_id).getLastname();;
    }

    public String getPrenom() {
        return U.getById(user_id).getName();
    }

    public void setPrenom(String prenom) {
        this.prenom = U.getById(user_id).getName();
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }


    public String getImage_degats() {
        return image_degats;
    }

    public void setImage_degats(String image_degats) {
        this.image_degats = image_degats;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
     

    
}
