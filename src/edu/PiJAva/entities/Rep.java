/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.entities;

/**
 *
 * @author karra
 */
public class Rep {

    public Rep() {
    }

    public Rep(String nom, String prenom,  String type) {
        this.nom = nom;
        this.prenom = prenom;
        this.type = type;
    }
    
    private    int id ;
    private String nom;
           private String prenom;
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

   

   

    public void setType(String type) {
        this.type = type;
    }
    public Rep(int id, String nom, String prenom, String type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Rep{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", type=" + type + '}';
    }
    
}