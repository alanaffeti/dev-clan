/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.JavaFxPidev.entities;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Mouad
 */
public class Voiture {
    private int id;
    private String marque;
    private String typevoiture;
    private String matricule;
    private String modele;
    private Date datefabrication;
    private String typecarburant;
    private int kilometrage;
    private String etat;
    private List<Contrat> contrats;
    
    
     public void addContrat(Contrat contrat) {
        contrats.add(contrat);
    }

    public List<Contrat> getContrats() {
        return contrats;
    }

    public void setContrats(List<Contrat> contrats) {
        this.contrats = contrats;
    }
    public Voiture() {
    }

    public Voiture(String marque, String typevoiture, String matricule, String modele, Date datefabrication, String typecarburant, int kilometrage, String etat) {
        this.marque = marque;
        this.typevoiture = typevoiture;
        this.matricule = matricule;
        this.modele = modele;
        this.datefabrication = datefabrication;
        this.typecarburant = typecarburant;
        this.kilometrage = kilometrage;
        this.etat = etat;
    }

    public Voiture(int id, String marque, String typevoiture, String matricule, String modele, Date datefabrication, String typecarburant, int kilometrage, String etat) {
        this.id = id;
        this.marque = marque;
        this.typevoiture = typevoiture;
        this.matricule = matricule;
        this.modele = modele;
        this.datefabrication = datefabrication;
        this.typecarburant = typecarburant;
        this.kilometrage = kilometrage;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getTypevoiture() {
        return typevoiture;
    }

    public void setTypevoiture(String typevoiture) {
        this.typevoiture = typevoiture;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public Date getDatefabrication() {
        return datefabrication;
    }

    public void setDatefabrication(Date datefabrication) {
        this.datefabrication = datefabrication;
    }

    public String getTypecarburant() {
        return typecarburant;
    }

    public void setTypecarburant(String typecarburant) {
        this.typecarburant = typecarburant;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Voiture{" + "id=" + id + ", marque=" + marque + ", typevoiture=" + typevoiture + ", matricule=" + matricule + ", modele=" + modele + ", datefabrication=" + datefabrication + ", typecarburant=" + typecarburant + ", kilometrage=" + kilometrage + ", etat=" + etat + '}';
    }
    
    
}
