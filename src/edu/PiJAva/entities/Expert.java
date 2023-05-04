/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.entities;

/**
 *
 * @author EXPERT
 */
public class Expert {
     private int id;
    private String nom;
    private String prenom;
    private int cin;

    public Expert() {
    }

    public Expert(int id, String nom, String prenom, int cin) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
    }

    public Expert(String nom, String prenom, int cin) {
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
    }

    public int getId() {
        return id;
    }
        public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
        public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
        public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getCin() {
        return cin;
    }
    
    public void setCin(int cin) {
        this.cin = cin;
    }
    
    
    
    
}
