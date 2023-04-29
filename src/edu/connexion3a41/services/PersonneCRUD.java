/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.services;

import edu.connexion3a41.entities.Rec;
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
public class PersonneCRUD implements EntityCRUD<Rec>{
 
   public void addEntity2(Rec t) {
        try {
            String requete="INSERT INTO assurensea (nom,prenom,email,description,type) VALUES (?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setString(1, t.getNom());
            pst.setString(2, t.getPrenom());
            pst.setString(3, t.getEmail());
            pst.setString(4, t.getDescription());
            pst.setString(5, t.getType());

            pst.executeUpdate();
            System.out.println("Success!");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
                                    
    }

 

    public List<Rec> afficher() {
        
        List<Rec>myList= new ArrayList();
        
        try {
            String requete = "SELECT * FROM assurensea";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()){
                Rec c = new Rec();
                c.setId(rs.getInt("id"));
                 c.setNom(rs.getString("nom"));                
                 c.setPrenom(rs.getString("prenom"));
                 c.setEmail(rs.getString("email")); 
                c.setDescription(rs.getString("description"));
                 c.setType(rs.getString("type"));

                myList.add(c); 
            }
            
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList ;
        
        
    }
   public List<Rec> afficherByNom(String nom) {
        
    List<Rec> myList = new ArrayList();
        
    try {
        String requete = "SELECT * FROM assurensea WHERE nom LIKE ?";
        PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
        ps.setString(1, "%" + nom + "%");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()){
            Rec c = new Rec();
            c.setId(rs.getInt("id"));
            c.setNom(rs.getString("nom"));                
            c.setPrenom(rs.getString("prenom"));
            c.setEmail(rs.getString("email")); 
            c.setDescription(rs.getString("description"));
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
        String requete = "DELETE FROM assurensea WHERE id = " + id;
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        st.executeUpdate(requete);
        System.out.println("Reclamation supprimée");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void updateEntity(int id, Rec rec) {
    try {
        String requete = "UPDATE assurensea SET nom = ?, prenom = ?, email = ?, description = ?, type = ? WHERE id = ?";
        PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(requete);
        ps.setString(1, rec.getNom());
        ps.setString(2, rec.getPrenom());
        ps.setString(3, rec.getEmail());
        ps.setString(4, rec.getDescription());
        ps.setString(5, rec.getType());
        ps.setInt(6, id);
        ps.executeUpdate();
        System.out.println("Reclamation mise à jour");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
public List<Rec> displayAllRec() {
    // Your code to retrieve all reclamation objects from the database
    List<Rec> reclamations = new ArrayList<Rec>();
    // Code to retrieve reclamation objects from the database
    return reclamations;
}
public int nbadmin(){
     String query="SELECT COUNT(*) FROM assurensea WHERE type='admin' ";
      
      int nb =0;
        
        try {
        	PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
             nb= rs.getInt(1);
              System.out.println(nb);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return nb;
    }
    
    public int nbnormal(){
     String query="SELECT COUNT(*) FROM assurensea WHERE type='normale' ";
      
      int nb =0;
        
        try {
        	PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
             nb= rs.getInt(1);
              System.out.println(nb);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return nb;
    }
}
