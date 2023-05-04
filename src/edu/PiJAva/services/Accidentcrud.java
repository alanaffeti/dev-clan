/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.services;

import edu.PiJAva.entities.Client;
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
public class Accidentcrud {
          Connection cnx;
    public Accidentcrud(){
        cnx=MyConnection.getInstance().getCnx();
    }
    
    public void ajouter(Client ac) {
        
        try {
            String requete = "INSERT into accident (nom_client,prenom_client,matricule,image,image_voiture_reparee,image_piece_endommage,image_nouveau_pieces,image_facture_reparation) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            
          
           pst.setString(1,ac.getNom_client());
           pst.setString(2,ac.getPrenom_client()); 
           pst.setString(3,ac.getMatricule());
           pst.setString(4,ac.getImage());
           pst.setString(5,ac.getImage_voiture_reparee());
           pst.setString(6,ac.getImage_piece_endommage());
         //  pst.setBoolean(7, m.isDisponibilite());
           pst.setString(7,ac.getImage_nouveau_pieces());
           pst.setString(8, ac.getImage_facture_reparation());
           
          
           pst.executeUpdate();
           System.out.println("photos accident ajouteés avec succées !");
          
                    
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
        }
    }

        
 
// affiche
    public List<Client> afficher() {
        List<Client> myList =new ArrayList<>();
        try {
            String requete ="SELECT * FROM accident";
            Statement st= MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                Client m1 =new Client();
                m1.setId(rs.getInt(1));
                m1.setNom_client(rs.getString("nom_client"));
                m1.setPrenom_client(rs.getString("prenom_client"));
                m1.setMatricule(rs.getString("matricule"));
                m1.setImage(rs.getString("image"));
                 m1.setImage_voiture_reparee(rs.getString("image_voiture_reparee"));
                  m1.setImage_piece_endommage(rs.getString("image_piece_endommage"));
                  // m.setDisponibilite(rs.getBoolean("disponibilite"));
                    m1.setImage_nouveau_pieces(rs.getString("image_nouveau_pieces"));
                    m1.setImage_facture_reparation(rs.getString("image_facture_reparation"));
                
                myList.add(m1);
            }
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
        }

   return myList;
    
}

    public void modifier(int id, Client t) {
        
        try {
            String query="UPDATE accident SET "
                    + "`nom_client`='"+t.getNom_client()+"',"
                    + "`prenom_client`='"+t.getPrenom_client()+"',"
                    + "`matricule`='"+t.getMatricule()+"',"
                    + "`image`='"+t.getImage()+"',"                    
                    + "`image_voiture_reparee`='"+t.getImage_voiture_reparee()+"',"
                    + "`image_piece_endommage`='"+t.getImage_piece_endommage()+"',"
                    + "`image_nouveau_pieces`='"+t.getImage_nouveau_pieces()+"',"
                    + "`image_facture_reparation`='"+t.getImage_facture_reparation()+"' WHERE id="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Accidentcrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimerr(int id) {
        try {
            String query="DELETE FROM accident WHERE id="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Accidentcrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
}
