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
public class Client {
     private int id;
    private String nom_client;
    private String prenom_client;
    private String matricule;
    private String image;
    private String image_voiture_reparee;
    private String  image_piece_endommage;
    private String image_nouveau_pieces;
    private String  image_facture_reparation;
    //jointure avec la table mecanicien
    private String  lieu_garage; 
     
    

    public Client(String nom_client, String prenom_client,String matricule ,String image,String image_voiture_repa,String image_piece_endom,String image_nouveau_pieces,String image_facture_rep)
    {
        
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.matricule = matricule;
        this.image = image;
        this.image_voiture_reparee = image_voiture_repa;
        this.image_piece_endommage= image_piece_endom;
        this.image_nouveau_pieces=  image_nouveau_pieces;
        this.image_facture_reparation= image_facture_rep;
        
        
    }
    public Client(String nom_client, String prenom_client, String matricule) {
        
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.matricule = matricule;
    }
    public Client() {
     
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getPrenom_client() {
        return prenom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_voiture_reparee() {
        return image_voiture_reparee;
    }

    public void setImage_voiture_reparee(String image_voiture_reparee) {
        this.image_voiture_reparee = image_voiture_reparee;
    }

    public String getImage_piece_endommage() {
        return image_piece_endommage;
    }

    public void setImage_piece_endommage(String image_piece_endommage) {
        this.image_piece_endommage = image_piece_endommage;
    }

    public String getImage_nouveau_pieces() {
        return image_nouveau_pieces;
    }

    public void setImage_nouveau_pieces(String image_nouveau_pieces) {
        this.image_nouveau_pieces = image_nouveau_pieces;
    }

    public String getImage_facture_reparation() {
        return image_facture_reparation;
    }

    public void setImage_facture_reparation(String image_facture_reparation) {
        this.image_facture_reparation = image_facture_reparation;
    }

    public String getLieu_garage() {
        return lieu_garage;
    }

    public void setLieu_garage(String lieu_garage) {
        this.lieu_garage = lieu_garage;
    }

  
    
}
