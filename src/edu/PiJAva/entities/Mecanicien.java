/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.entities;

/**
 *
 * @author famou
 */
public class Mecanicien {
    
     private int id;
    private String nom;
    private String prenom;
    private String cin;
    private String adresse_mail;
    private String num_tel;
    private float salaire;
    private boolean disponibilite;
    private String lieu_garage;
    private String facture_rep; 
     
    

    public Mecanicien(String nom, String prenom,String cin ,String mail,String num_tel,float salaire,String lieu_garage,String img) {
        
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.adresse_mail = mail;
        this.num_tel = num_tel;
        this.salaire= salaire;
        //this.disponibilite= dispo;
        this.lieu_garage= lieu_garage;
        this.facture_rep= img;
    }
    public Mecanicien(String nom, String prenom, String cin) {
        
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
    }
    public Mecanicien() {
     
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Personne{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", cin="+cin+ '}';
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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getAdresse_mail() {
        return adresse_mail;
    }

    public void setAdresse_mail(String adresse_mail) {
        this.adresse_mail = adresse_mail;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public float getSalaire() {
        return salaire;
    }

    public void setSalaire(float salaire) {
        this.salaire = salaire;
    }

    public boolean isDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public String getLieu_garage() {
        return lieu_garage;
    }

    public void setLieu_garage(String lieu_garage) {
        this.lieu_garage = lieu_garage;
    }

    public String getFacture_rep() {
        return facture_rep;
    }

    public void setFacture_rep(String facture_rep) {
        this.facture_rep = facture_rep;
    }
    
    
}
