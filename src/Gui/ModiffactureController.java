/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.Facture;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class ModiffactureController implements Initializable {

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
    @FXML
    private Button btnEnregistrer;
    @FXML
    private Button btnVider;

    Facture facture = new Facture () ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    private FactureController factureController;
    public void setmodifController(FactureController factureController) {
        this.factureController = factureController;
    }
    
    
    private final Facture selectedFacture;

    public ModiffactureController(Facture facture) {
        selectedFacture = facture;
    }


    @FXML
    private void Modiffacture(ActionEvent event) {
        
//        String erreur = controleDeSaisie();
//        if (erreur.length() > 0) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("invalide");
//            alert.setContentText(erreur);
//            alert.showAndWait();
//        } else {
//             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirmation de suppression");
//        alert.setHeaderText(null);
//        alert.setContentText("Êtes-vous sûr de vouloir modifier ce contrat ?");
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ButtonType.OK)
//        {
        selectedFacture.setQuantite(Integer.valueOf(quantiteFld.getText()));
        selectedFacture.setNom_piece(nompieceFld.getText());
        selectedFacture.setNom_client(nomclientFld.getText());
        selectedFacture.setId_client(Integer.valueOf(idclientFld.getText()));
        selectedFacture.setMontant_facture(Integer.valueOf(montantfactureFld.getText()));
        

        facture.update(selectedFacture.getId(), selectedFacture);
        Stage stage = (Stage) btnEnregistrer.getScene().getWindow();
        stage.close();

        factureController.Actualiser();
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
