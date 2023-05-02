/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Connexion.Dbconnect;
import Entities.Facture;
import Tools.Mail;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import tools.Sms;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class AjoutfactureController implements Initializable {

    @FXML
    private Button btnAjout;
    @FXML
    private Button btnVider;
    @FXML
    private TextField idFld;
    @FXML
    private TextField quantiteFld;
    @FXML
    private TextField nompieceFld;
    @FXML
    private TextField nomclientFld;
    @FXML
    private TextField idclientFld;
    @FXML
    private TextField montantfactureFld;
    
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    //Facture facture = null;
    
    ObservableList<Facture>  FactureList = FXCollections.observableArrayList();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    private FactureController factureController;
     
    @FXML
    private void Ajoutfacture(ActionEvent event) {
        
        connection = Dbconnect.getInstance().getCnx();
        
        int quantite = Integer.parseInt(quantiteFld.getText());
        String nom_piece = nompieceFld.getText();
        String nom_client = nomclientFld.getText();
        int id_client = Integer.parseInt(idclientFld.getText());
        double montant_facture = Double.parseDouble(montantfactureFld.getText());
        
       

       insert();
//       factureController.Actualiser();
        Stage stage = (Stage) btnAjout.getScene().getWindow();
        stage.close();
        String numero = "+21694131390"; 
        String contenu = "Nouvelle facture ajoutée " ;
        Sms.send("+21694131390", " Nouvelle facture ajoutée" );

        

        
        
    }
    private void insert() {
    try {
        String requete = "INSERT INTO `facture`( `quantite`, `nom_piece`, `nom_client`, `id_client`, `montant_facture`) VALUES (?,?,?,?,?)";

        preparedStatement = Dbconnect.getInstance().getCnx().prepareStatement(requete);

        if (quantiteFld != null && nompieceFld != null && nomclientFld != null && idclientFld != null && montantfactureFld != null) {
//            preparedStatement.setString(1, idFld.getText());
            preparedStatement.setString(1, quantiteFld.getText());
            preparedStatement.setString(2, nompieceFld.getText());
            preparedStatement.setString(3, nomclientFld.getText());
            preparedStatement.setString(4, idclientFld.getText());
            preparedStatement.setString(5, montantfactureFld.getText());
            preparedStatement.execute();
            System.out.println("Success!");
                            

        } else {
            System.out.println("Une ou plusieurs variables sont null !");
        }

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
    

    @FXML
    private void Vider(ActionEvent event) {
        
        
        quantiteFld.setText(null);
        nompieceFld.setText(null);
        nomclientFld.setText(null);
        idclientFld.setText(null);
        montantfactureFld.setText(null);
    }

    
   
}