/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.services;

import edu.PiJAva.entities.Factures;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.PiJAva.tools.MyConnection;

/**
 *
 * @author famou
 */
public class Facturecrud {
    
     Connection cnx;
    public Facturecrud(){
        cnx=MyConnection.getInstance().getCnx();
    }
    
    public void ajouter(Factures f) {
        
        try {
            String requete = "INSERT into facturemeca (article,quantite,description,prixsanstva,prixavectva,total) values(?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            
          
           pst.setString(1,f.getArticle());
           pst.setInt(2,f.getQuantite()); 
           pst.setString(3,f.getDescription());
           pst.setFloat(4,f.getPrixsanstva());
           pst.setFloat(5,f.getPrixavectva());
           pst.setFloat(6,f.getTotal());
          
           pst.executeUpdate();
           System.out.println("facture ajouteé avec succées !");
          
                    
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
        }
    }

        
 
// affiche
    public List<Factures> afficher() {
        List<Factures> myList =new ArrayList<>();
        try {
            String requete ="SELECT * FROM facturemeca";
            Statement st= MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                Factures f =new Factures();
                f.setId_fact(rs.getInt("id_fact"));
                f.setArticle(rs.getString("article"));
                f.setQuantite(rs.getInt("quantite"));
                f.setDescription(rs.getString("description"));
                f.setPrixsanstva(rs.getFloat("prixsanstva"));
                f.setPrixavectva(rs.getFloat("prixavectva"));
                f.setTotal(rs.getFloat("total"));
                 
                  
                myList.add(f);
            }
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
        }

   return myList;
    
}

    public void modifier(int id, Factures t) {
        
        try {
            String query="UPDATE facturemeca SET "
                    
                    + "`article`='"+t.getArticle()+"',"
                    + "`quantite`='"+t.getQuantite()+"',"
                    + "`description`='"+t.getDescription()+"',"
                    + "`prixsanstva`='"+t.getPrixsanstva()+"',"                    
                    + "`prixavectva`='"+t.getPrixavectva()+"',"
                    + "`total`='"+t.getTotal()+"' WHERE id_fact="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Facturecrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimer(int id) {
        try {
            String query="DELETE FROM facturemeca WHERE id_fact="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Facturecrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
}
