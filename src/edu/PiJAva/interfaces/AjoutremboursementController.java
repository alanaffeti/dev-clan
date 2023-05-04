/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import edu.PiJAva.entities.Facture;
import edu.PiJAva.entities.Remboursement;
import edu.PiJAva.tools.MyConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import edu.PiJAva.tools.Sms;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class AjoutremboursementController implements Initializable {

    @FXML
    private DatePicker dateFld;
    @FXML
    private TextField montantFld;
    @FXML
    private Button ajout;
    @FXML
    private Button vider;
    
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Remboursement remboursement = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    private RemboursementController remboursementController;

    @FXML
    private void ajout_remboursement(ActionEvent event) {
        
        connection = MyConnection.getInstance().getCnx();
        
        Date date_remb = Date.valueOf(dateFld.getValue());
        double montant_remb = Double.parseDouble(montantFld.getText());
        
        insert();
        Stage stage = (Stage) ajout.getScene().getWindow();
        stage.close();
        remboursementController.Actualiser();
        
    }

    @FXML
    private void vider(ActionEvent event) {
        
        
        montantFld.setText(null);
    }

    private void insert() {
        try {
        String requete = "INSERT INTO `remboursement`( `date_remb`, `montant_remb`) VALUES (?,?)";

        preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(requete);

        if ( dateFld != null  && montantFld != null) {
            
            preparedStatement.setDate(1, java.sql.Date.valueOf(dateFld.getValue()));
preparedStatement.setString(2, montantFld.getText());
            preparedStatement.execute();
            System.out.println("Success!");
        } else {
            System.out.println("Une ou plusieurs variables sont null !");
        }

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
    




    
}
