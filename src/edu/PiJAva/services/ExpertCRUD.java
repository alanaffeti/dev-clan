/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.services;

import edu.PiJAva.entities.Expert;
import edu.PiJAva.services.EntityCRUD;
import edu.PiJAva.services.EntityCRUD;
import edu.PiJAva.tools.MyConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 *
 * @author EXPERT
 */
public class ExpertCRUD {

    
    public void addEntity(Expert t) {
   String requete = "INSERT INTO expert (nom, prenom, cin) VALUES (?, ?, ?)";
    try {
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, t.getNom());
        pst.setString(2, t.getPrenom());
        pst.setInt(3, t.getCin());
        pst.executeUpdate();
        System.out.println("Expert ajouté avec succès !");
    } catch (SQLException ex) {
        System.out.println("Erreur lors de l'ajout de l'expert : " + ex.getMessage());
    }
}

    
    public List<Expert> display() {
    List<Expert> myList = new ArrayList<>();
    try {
        String requete = "SELECT * FROM expert";
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            Expert e = new Expert();
            e.setId(rs.getInt("id"));
            e.setNom(rs.getString("nom"));
            e.setPrenom(rs.getString("prenom"));
            e.setCin(rs.getInt("cin"));
            myList.add(e);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return myList;
}

   // @Override
//    public void modifierEntity( int id ,Expert t) {
//    String requete = "UPDATE expert SET nom = ?, prenom = ?, cin = ? WHERE id = ?";
//    try {
//        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
//        pst.setString(1, t.getNom());
//        pst.setString(2, t.getPrenom());
//        pst.setInt(3, t.getCin());
//        pst.setInt(4, t.getId());
//        int result = pst.executeUpdate();
//        if (result > 0) {
//            System.out.println("Expert modifié avec succès !");
//        } else {
//            System.out.println("Aucune modification n'a été effectuée.");
//        }
//    } catch (SQLException ex) {
//        System.out.println("Erreur lors de la modification de l'expert : " + ex.getMessage());
//    }
//}


    public void supprimerEntity(int id) {
        String requete = "DELETE FROM expert WHERE id = ?";
    try {
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, id);
        int result = pst.executeUpdate();
        if (result > 0) {
            System.out.println("Expert supprimé avec succès !");
        } else {
            System.out.println("Aucun expert n'a été supprimé.");
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la suppression de l'expert : " + ex.getMessage());
    }
}


    public void modifierEntity(int id, Expert t) {
        String requete ="UPDATE expert SET Nom = ?, Prenom = ?, cin= ? WHERE id = ?";
    try {
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, t.getNom());
        pst.setString(2, t.getPrenom());
        pst.setInt(3, t.getCin());
        pst.setInt(4, id);
        int resultat = pst.executeUpdate();
        if (resultat > 0) {
            System.out.println("La modification a été effectuée avec succès.");
        } else {
            System.out.println("Aucune ligne n'a été modifiée.");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }
    
}
