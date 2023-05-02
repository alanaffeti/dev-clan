/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Connexion.Dbconnect;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author DELL
 */
public class Remboursement {
    int id ;
    Date date_remb ;
    Double montant_remb ;

    public Remboursement() {
    }

    public Remboursement(Date date_remb, Double montant_remb) {
        this.date_remb = date_remb;
        this.montant_remb = montant_remb;
    }

    public Remboursement(int id, Date date_remb, Double montant_remb) {
        this.id = id;
        this.date_remb = date_remb;
        this.montant_remb = montant_remb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_remb() {
        return date_remb;
    }

    public void setDate_remb(Date date_remb) {
        this.date_remb = date_remb;
    }

    public Double getMontant_remb() {
        return montant_remb;
    }

    public void setMontant_remb(Double montant_remb) {
        this.montant_remb = montant_remb;
    }
    
    public void update(int id, Remboursement r) {
        try {
    String query = "UPDATE remboursement SET "
            + "`date_remb`='" + r.getDate_remb()+ "',"
            + "`montant_remb`='" + r.getMontant_remb()+ "' "
            + "WHERE id=" + id;

    Statement st = Dbconnect.getInstance().getCnx().createStatement();
    st.executeUpdate(query);
} catch (SQLException ex) {
    System.out.println(ex.getMessage());
}


    }
    
    
    
}
