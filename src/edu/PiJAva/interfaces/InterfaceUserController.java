/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author 21694
 */
public class InterfaceUserController implements Initializable {

    @FXML
    private Label TxUserName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Dashbord(ActionEvent event) {
    }

    @FXML
    private void DepotConstat(ActionEvent event) {
    }

    @FXML
    private void Facture(ActionEvent event) {
    }

    @FXML
    private void Profile(ActionEvent event) {
    }

    @FXML
    private void Reclamation(ActionEvent event) {
        FXMain.setScene("InterfaceReclamation");
    }

    @FXML
    private void ListUser(ActionEvent event) {
    }

    @FXML
    private void LogOut(ActionEvent event) {
    }

}
