/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.entities;

import edu.PiJAva.tools.MyConnection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author DELL
 */
public class Facture {
    int id  ;
    int quantite ;
    String nom_piece ;
    String nom_client ;
    int id_client;
    double montant_facture ;

    public Facture() {
    }

    public Facture(int quantite, String nom_piece, String nom_client, int id_client, double montant_facture) {
        this.quantite = quantite;
        this.nom_piece = nom_piece;
        this.nom_client = nom_client;
        this.id_client = id_client;
        this.montant_facture = montant_facture;
    }

    public Facture(int id, int quantite, String nom_piece, String nom_client, int id_client, double montant_facture) {
        this.id = id;
        this.quantite = quantite;
        this.nom_piece = nom_piece;
        this.nom_client = nom_client;
        this.id_client = id_client;
        this.montant_facture = montant_facture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getNom_piece() {
        return nom_piece;
    }

    public void setNom_piece(String nom_piece) {
        this.nom_piece = nom_piece;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public double getMontant_facture() {
        return montant_facture;
    }

    public void setMontant_facture(double montant_facture) {
        this.montant_facture = montant_facture;
    }

    @Override
    public String toString() {
        return "Facture{" + "id=" + id + ", quantite=" + quantite + ", nom_piece=" + nom_piece + ", nom_client=" + nom_client + ", id_client=" + id_client + ", montant_facture=" + montant_facture + '}';
    }
    
    public void update(int id, Facture f) {
        try {
    String query = "UPDATE facture SET "
            + "`quantite`='" + f.getQuantite()+ "',"
            + "`nom_piece`='" + f.getNom_piece()+ "',"
            + "`nom_client`='" + f.getNom_client()+ "',"
            + "`id_client`='" + f.getId_client()+ "',"
            + "`montant_facture`='" + f.getMontant_facture()+ "'"
            + " WHERE id=" + id;

     Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            
            st.executeUpdate(query);
} catch (SQLException ex) {
    System.out.println(ex.getMessage());
}

    }
    
    
}
