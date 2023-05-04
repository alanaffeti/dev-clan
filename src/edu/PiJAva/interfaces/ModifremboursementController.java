/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import edu.PiJAva.entities.Remboursement;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class ModifremboursementController implements Initializable {

    @FXML
    private DatePicker dateFld;
    @FXML
    private TextField montantFld;
    @FXML
    private Button vider;
     
    
    Remboursement remboursement = new Remboursement () ;
    private RemboursementController remboursementController;
    @FXML
    private Button btnEnregistrer;
    public void setmodifController(RemboursementController remboursementController) {
        this.remboursementController = remboursementController;
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    private final Remboursement selectedRemboursement ;
    
    public ModifremboursementController(Remboursement remboursement) {
        selectedRemboursement = remboursement;
    }


    @FXML
    private void vider(ActionEvent event) {
        montantFld.setText(null);
    }

    @FXML
    private void modif_remboursement(ActionEvent event) {
        selectedRemboursement.setDate_remb(Date.valueOf(dateFld.getValue()));
        selectedRemboursement.setMontant_remb(Double.valueOf(montantFld.getText()));
        

        remboursement.update(selectedRemboursement.getId(), selectedRemboursement);
        Stage stage = (Stage) btnEnregistrer.getScene().getWindow();
        stage.close();

        remboursementController.Actualiser();
    }
    
}
