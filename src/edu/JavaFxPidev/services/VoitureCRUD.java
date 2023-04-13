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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mouad
 */
public class VoitureCRUD implements EntityCRUD<Voiture> {

    @Override
    public void addEntity(Voiture t) {
        try {
            String requete = "INSERT INTO voiture (marque,typevoiture,matricule,modele,datefabrication,typecarburant,kilometrage,etat)"
                    + "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1, t.getMarque());
            pst.setString(2, t.getTypevoiture());
            pst.setString(3, t.getMatricule());
            pst.setString(4, t.getModele());
            pst.setDate(5, new java.sql.Date(t.getDatefabrication().getTime()));
            pst.setString(6, t.getTypecarburant());
            pst.setInt(7, t.getKilometrage());
            pst.setString(8, t.getEtat());

            pst.executeUpdate();
            System.out.println("Success!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Voiture> display() {
        List<Voiture> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM voiture";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Voiture p = new Voiture();
                p.setId(rs.getInt(1));
                p.setMarque(rs.getString("marque"));
                p.setTypevoiture(rs.getString("typevoiture"));
                p.setMatricule(rs.getString("matricule"));
                p.setModele(rs.getString("modele"));
                p.setDatefabrication(rs.getDate("datefabrication"));
                p.setTypecarburant(rs.getString("typecarburant"));
                p.setKilometrage(rs.getInt("kilometrage"));
                p.setEtat(rs.getString("etat"));

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
            String query = "DELETE FROM `voiture` WHERE id=" + id;
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(VoitureCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(int id, Voiture t) {

        try {
            String query = "UPDATE `voiture` SET "
                    + "`marque`='" + t.getMarque() + "',"
                    + "`typecarburant`='" + t.getTypevoiture() + "',"
                    + "`matricule`='" + t.getMatricule() + "',"
                    + "`modele`='" + t.getModele() + "',"
                    + "`datefabrication`='" + t.getDatefabrication() + "',"
                    + "`typecarburant`='" + t.getTypecarburant() + "',"
                    + "`kilometrage`='" + t.getKilometrage() + "',"
                    + "`etat`='" + t.getEtat() + "'"
                    + " WHERE id=" + id;

            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(VoitureCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Voiture> getContratsByVoitureId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
