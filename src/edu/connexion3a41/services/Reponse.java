/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.services;

import edu.connexion3a41.entities.Rec;
import edu.connexion3a41.entities.Rep;
import edu.connexion3a41.interfaces.EntityCRUD;
import edu.connexion3a41.tools.MyConnection;
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
 * @author karra
 */
public class Reponse implements EntityCRUD<Rep>{

   
   public void addEntity2(Rep t) {
        try {
            String requete="INSERT INTO reponse (nom,prenom,type) VALUES (?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1, t.getNom());
            pst.setString(2, t.getPrenom());
            pst.setString(3, t.getType());

            pst.executeUpdate();
            System.out.println("Success!");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
                                    
    }

  
    public List<Rep> afficher() {
        
        List<Rep>myList= new ArrayList();
        
        try {
            String requete = "SELECT * FROM reponse";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()){
                Rep c = new Rep();
                c.setId(rs.getInt("id"));
                 c.setNom(rs.getString("nom"));                
                 c.setPrenom(rs.getString("prenom"));
                
                 c.setType(rs.getString("type"));

                myList.add(c); 
            }
            
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList ;
        
        
    }
   public List<Rep> afficherByNom(String nom) {
        
    List<Rep> myList = new ArrayList();
        
    try {
        String requete = "SELECT * FROM reponse WHERE nom LIKE ?";
        PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
        ps.setString(1, "%" + nom + "%");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()){
            Rep c = new Rep();
            c.setId(rs.getInt("id"));
            c.setNom(rs.getString("nom"));                
            c.setPrenom(rs.getString("prenom"));
          
            c.setType(rs.getString("type"));

            myList.add(c); 
        }
            
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    
    return myList ;
}


    

public void deleteEntity(int id) {
    try {
        String requete = "DELETE FROM reponse WHERE id = " + id;
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        st.executeUpdate(requete);
        System.out.println("Reclamation supprimée");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void updateEntity(int id, Rep rep) {
    try {
        String requete = "UPDATE reponse SET nom = ?, prenom = ?, type = ? WHERE id = ?";
        PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
        ps.setString(1, rep.getNom());
        ps.setString(2, rep.getPrenom());
     
        ps.setString(3, rep.getType());
        ps.setInt(4, id);
        ps.executeUpdate();
        System.out.println("Reclamation mise à jour");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

}
