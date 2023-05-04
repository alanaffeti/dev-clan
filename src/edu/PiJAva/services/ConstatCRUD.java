
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.services;

import edu.PiJAva.entities.Constat;
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

/**
 *
 * @author EXPERT
 */
public class ConstatCRUD {

   
    public void addEntity(Constat t) {
         String requete ="INSERT INTO constat (nom,prenom,matricule,lieu,date,image_degats,user_id)"
                +"VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst =MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1,t.getNom());
            pst.setString(2,t.getPrenom());
            pst.setString(3, t.getMatricule());
            pst.setString(4,t.getLieu());
            pst.setDate(5, (Date) t.getDate());
            pst.setString(6, t.getImage_degats());
            pst.setInt(7, t.getUser_id());
            pst.executeUpdate();
            System.out.println("Success!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public List<Constat> display() {
           List<Constat> myList =new ArrayList<>();
        try {
            String requete ="SELECT * FROM constat";
            Statement st=  MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                Constat c =new Constat();
                c.setId(rs.getInt(1));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setLieu(rs.getString("lieu"));
                c.setMatricule(rs.getString("matricule"));
                c.setDate(rs.getDate("date"));
                c.setImage_degats(rs.getString("image_degats"));
                c.setUser_id(rs.getInt("user_id"));
                myList.add(c);
            }
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
        }
        return myList;
        
    }


public void modifierEntity(int id, Constat t) {
    String requete ="UPDATE constat SET Nom = ?, Prenom = ?, matricule = ?, lieu = ?, date = ?, image_degats = ? WHERE id = ?";
    try {
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setString(1, t.getNom());
        pst.setString(2, t.getPrenom());
        pst.setString(3, t.getMatricule());
        pst.setString(4, t.getLieu());
        pst.setDate(5, (Date) t.getDate());
        pst.setString(6, t.getImage_degats());
        pst.setInt(7, id);
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



public void supprimerEntity(int id) {
    String requete = "DELETE FROM constat WHERE id = ?";
    try {
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
        pst.setInt(1, id);
        int resultat = pst.executeUpdate();
        if (resultat > 0) {
            System.out.println("La suppression a été effectuée avec succès.");
        } else {
            System.out.println("Aucune ligne n'a été supprimée.");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}


    
}
     

    
