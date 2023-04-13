/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.JavaFxPidev.services;

import edu.JavaFxPidev.entities.Contrat;
import edu.JavaFxPidev.entities.Voiture;
import edu.JavaFxPidev.interfaces.EntityCRUD;
import edu.JavaFxPidev.tools.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mouad
 */
public class ContratCRUD implements EntityCRUD<Contrat> {

    @Override
    public void addEntity(Contrat t) {
        try {
            String requete = "INSERT INTO contrat (voiture_id,datedebut,datefin,prix,type,nomconducteur,prenomconducteur,cin,region)"
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, t.getVoiture_id());
            pst.setDate(2, new java.sql.Date(t.getDatedebut().getTime()));
            pst.setDate(3, new java.sql.Date(t.getDatefin().getTime()));
            pst.setFloat(4, t.getPrix());
            pst.setString(5, t.getType());
            pst.setString(6, t.getNomconducteur());
            pst.setString(7, t.getPrenomconducteur());
            pst.setInt(8, t.getCin());
            pst.setString(9, t.getRegion());

            pst.executeUpdate();
            System.out.println("Success!");
//            VoitureCRUD voitureCRUD = new VoitureCRUD();
//            Voiture selectedVoiture = voitureCRUD.getById(t.getVoiture_id());
//            if (selectedVoiture != null) {
//                List<Contrat> contracts = selectedVoiture.getContrats();
//                contracts.add(t);
//                selectedVoiture.setContrats(contracts);
//                voitureCRUD.update(selectedVoiture.getId(), selectedVoiture);
//            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Contrat> display() {
        List<Contrat> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM contrat";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Contrat p = new Contrat();
                p.setId(rs.getInt(1));
                p.setVoiture_id(rs.getInt("voiture_id"));
                p.setDatedebut(rs.getDate("datedebut"));
                p.setDatefin(rs.getDate("datefin"));
                p.setPrix(rs.getFloat("prix"));
                p.setType(rs.getString("type"));
                p.setNomconducteur(rs.getString("nomconducteur"));
                p.setPrenomconducteur(rs.getString("prenomconducteur"));
                p.setCin(rs.getInt("cin"));
                p.setRegion(rs.getString("region"));

                myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public void delete(int id) {
        try {
            String query = "DELETE FROM `contrat` WHERE id=" + id;
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(VoitureCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(int id, Contrat t) {
        try {
            String query = "UPDATE `contrat` SET "
                    
                    + "`datedebut`='" + t.getDatedebut()+ "',"
                    + "`datefin`='" + t.getDatefin()+ "',"
                    + "`prix`='" + t.getPrix()+ "',"
                    + "`type`='" + t.getType()+ "',"
                    + "`nomconducteur`='" + t.getNomconducteur()+ "',"
                    + "`prenomconducteur`='" + t.getPrenomconducteur()+ "',"
                    + "`cin`='" + t.getCin()+ "'," 
                    + "`region`='" + t.getRegion()+ "'"
                    + " WHERE id=" + id;

            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(VoitureCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Contrat> getContratsByVoitureId(int voitureId) {
        List<Contrat> contrats = new ArrayList<>();
        try {
            String query = "SELECT * FROM contrat WHERE voiture_id=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(query);
            pst.setInt(1, voitureId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Contrat contrat = new Contrat(
                        rs.getInt("id"),
                        rs.getInt("voiture_id"),
                        rs.getDate("datedebut"),
                        rs.getDate("datefin"),
                        rs.getFloat("prix"),
                        rs.getString("type"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getInt("cin"),
                        rs.getString("region")
                );
                contrats.add(contrat);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return contrats;
    }

}
