/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.services;

import edu.PiJAva.entities.Mecanicien;
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
public class Mecaniciencrud {
    
     Connection cnx;
    public Mecaniciencrud(){
        cnx=MyConnection.getInstance().getCnx();
    }
    
    public void ajouter(Mecanicien m) {
        
        try {
            String requete = "INSERT into project (nom,prenom,cin,adresse_mail,num_tel,salaire,lieu_garage,facture_rep) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            
          
           pst.setString(1,m.getNom());
           pst.setString(2,m.getPrenom()); 
           pst.setString(3,m.getCin());
           pst.setString(4,m.getAdresse_mail());
           pst.setString(5,m.getNum_tel());
           pst.setFloat(6,m.getSalaire());
           //pst.setBoolean(7, m.isDisponibilite());
           pst.setString(7,m.getLieu_garage());
           pst.setString(8, m.getFacture_rep());
           
          
           pst.executeUpdate();
           System.out.println("mecaincien ajouteé avec succées !");
          
                    
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
        }
    }

        
 
// affiche
    public List<Mecanicien> afficher() {
        List<Mecanicien> myList =new ArrayList<>();
        try {
            String requete ="SELECT * FROM project";
            Statement st= MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                Mecanicien m =new Mecanicien();
                m.setId(rs.getInt(1));
                m.setNom(rs.getString("nom"));
                m.setPrenom(rs.getString("prenom"));
                m.setCin(rs.getString("cin"));
                m.setAdresse_mail(rs.getString("adresse_mail"));
                 m.setNum_tel(rs.getString("num_tel"));
                  m.setSalaire(rs.getFloat("salaire"));
                   m.setDisponibilite(rs.getBoolean("disponiblite"));
                    m.setLieu_garage(rs.getString("lieu_garage"));
                  m.setFacture_rep(rs.getString("facture_rep"));
                
                myList.add(m);
            }
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
        }

   return myList;
    
}
    
    
     public List<Mecanicien> afficher2(String nom) {
        List<Mecanicien> myList =new ArrayList<>();
        try {
            String requete ="SELECT * FROM project";
            Statement st= MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                Mecanicien m =new Mecanicien();
                m.setId(rs.getInt(1));
                m.setNom(rs.getString("nom"));
                m.setPrenom(rs.getString("prenom"));
                m.setCin(rs.getString("cin"));
                m.setAdresse_mail(rs.getString("adresse_mail"));
                 m.setNum_tel(rs.getString("num_tel"));
                  m.setSalaire(rs.getFloat("salaire"));
                   m.setDisponibilite(rs.getBoolean("disponiblite"));
                    m.setLieu_garage(rs.getString("lieu_garage"));
                  m.setFacture_rep(rs.getString("facture_rep"));
                
                myList.add(m);
            }
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
        }

   return myList;
    
}
    
    
    
    

    public void modifier(int id, Mecanicien t) {
        
        try {
            String query="UPDATE project SET "
                    + "`nom`='"+t.getNom()+"',"
                    + "`prenom`='"+t.getPrenom()+"',"
                    + "`cin`='"+t.getCin()+"',"
                    + "`adresse_mail`='"+t.getAdresse_mail()+"',"                    
                    + "`num_tel`='"+t.getNum_tel()+"',"
                    + "`salaire`='"+t.getSalaire()+"',"
                    //+ "`disponibilite`='"+t.isDisponibilite()+"',"
                    + "`lieu_garage`='"+t.getLieu_garage()+"',"
                    + "`facture_rep`='"+t.getFacture_rep()+"' WHERE id="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Mecaniciencrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimer(int id) {
        try {
            String query="DELETE FROM project WHERE id="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Mecaniciencrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
