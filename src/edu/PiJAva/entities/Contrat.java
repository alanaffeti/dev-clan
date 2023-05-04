/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.entities;

import java.util.Date;

/**
 *
 * @author Mouad
 */
public class Contrat {
    private int id;
    private int voiture_id;
    private Date datedebut;
    private Date datefin;
    private float prix;
    private String type;
    private String nomconducteur;
    private String prenomconducteur;
    private int cin;
    private String region;

    public Contrat() {
    }

    public Contrat(int voiture_id, Date datedebut, Date datefin, float prix, String type, String nomconducteur, String prenomconducteur, int cin, String region) {
        this.voiture_id = voiture_id;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.prix = prix;
        this.type = type;
        this.nomconducteur = nomconducteur;
        this.prenomconducteur = prenomconducteur;
        this.cin = cin;
        this.region = region;
    }

    public Contrat(int id, int voiture_id, Date datedebut, Date datefin, float prix, String type, String nomconducteur, String prenomconducteur, int cin, String region) {
        this.id = id;
        this.voiture_id = voiture_id;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.prix = prix;
        this.type = type;
        this.nomconducteur = nomconducteur;
        this.prenomconducteur = prenomconducteur;
        this.cin = cin;
        this.region = region;
    }

   

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNomconducteur() {
        return nomconducteur;
    }

    public void setNomconducteur(String nomconducteur) {
        this.nomconducteur = nomconducteur;
    }

    public String getPrenomconducteur() {
        return prenomconducteur;
    }

    public void setPrenomconducteur(String prenomconducteur) {
        this.prenomconducteur = prenomconducteur;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getVoiture_id() {
        return voiture_id;
    }

    public void setVoiture_id(int voiture_id) {
        this.voiture_id = voiture_id;
    }

    @Override
    public String toString() {
        return "Contrat{" + "id=" + id + ", datedebut=" + datedebut + ", datefin=" + datefin + ", prix=" + prix + ", type=" + type + ", nomconducteur=" + nomconducteur + ", prenomconducteur=" + prenomconducteur + ", cin=" + cin + ", region=" + region + '}';
    }
    
}
