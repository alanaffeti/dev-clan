/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.services;

import edu.PiJAva.entities.Contrat;
import edu.PiJAva.entities.Voiture;
import edu.PiJAva.tools.MyConnection;
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
public class VoitureCRUD  {

    
    public void addEntity(Voiture t) {
        try {
            String requete = "INSERT INTO voiture (marque,typevoiture,matricule,modele,datefabrication,typecarburant,kilometrage,etat,id_user)"
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
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
            pst.setInt(9, t.getId_user());


            pst.executeUpdate();
            System.out.println("Success!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    
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
                p.setId_user(rs.getInt("id_user"));


                myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

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

    
    public List<Voiture> getContratsByVoitureId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
      public  List<Object> getTitresEtDemandes() {
    List<Object> resultat = new ArrayList<>();
try {
    String req = "SELECT voiture.typevoiture, COUNT(voiture.id) AS nombre_typevoiture\n" +
                 "FROM voiture\n" +
                 "LEFT JOIN voiture ON voiture.id = voiture.offre_id\n" +
                 "GROUP BY voiture.id";
    Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            st.executeUpdate(req);
ResultSet RS = st.executeQuery(req);
    // Stockage des valeurs dans des listes
    List<String> titres = new ArrayList<>();
    List<Integer> demandes = new ArrayList<>();
    while (RS.next()) {
        String titreOffre = RS.getString("titre");
        int nombreDemandes = RS.getInt("nombre_demandes");
        titres.add(titreOffre);
        demandes.add(nombreDemandes);
    }

    // Ajout des listes à la variable resultat
    resultat.add(titres);
    resultat.add(demandes);
} catch (SQLException ex) {
    System.out.println(ex.getMessage());
}
return resultat;
    }

    
    public List<Contrat> displayV(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
