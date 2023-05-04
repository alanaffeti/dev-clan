/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import edu.PiJAva.entities.Expert;
import edu.PiJAva.entities.User;
import edu.PiJAva.services.ExpertCRUD;
import edu.PiJAva.services.ServiceUser;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author EXPERT
 */
public class InterfaceExpertController implements Initializable {
 User u =new User();
    ServiceUser su=new ServiceUser();
    @FXML
    private TableView<Expert> tvexpert;
    @FXML
    private TableColumn<?, ?> cenom;
    @FXML
    private TableColumn<?, ?> ceprenom;
    @FXML
    private TableColumn<?, ?> cecin;
    @FXML
    private TextField ecin;
    @FXML
    private TextField eprenom;
    @FXML
    private TextField enom;
    @FXML
    private Button emodif;
    @FXML
    private Button ajoutE;
    @FXML
    private Button clearexp;
    @FXML
    private Button suppexpert;
    @FXML
    private Button expertrefreche;
     ExpertCRUD cr = new ExpertCRUD();
     ObservableList<Expert> data=FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvexpert.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Expert expert = (Expert) tvexpert.getSelectionModel().getSelectedItem();
                if (expert != null) {
                    enom.setText(expert.getNom());
                    eprenom.setText(expert.getPrenom());
                    ecin.setText(String.valueOf(expert.getCin()));
        
                }
             }
           
                });
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
    }

    @FXML
    private void ListUser(ActionEvent event) {
    }

    @FXML
    private void modifierExp(ActionEvent event) {
         Expert expert = tvexpert.getSelectionModel().getSelectedItem();
        if (expert != null) {
            int id = expert.getId();
            Expert u = new Expert();
            u.setNom(enom.getText());
            u.setPrenom(eprenom.getText());
            int ecinValue = Integer.parseInt(ecin.getText());
            u.setCin(ecinValue);
            cr.modifierEntity(id, u);
            resfreshexpert();

    }
    }

    @FXML
    private void ajouterExp(ActionEvent event) {
         String erreur=controleDeSaisie();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
        else{
            Expert u=new Expert();
            u.setNom(enom.getText());
            u.setPrenom(eprenom.getText());
           int ecinValue = Integer.parseInt(ecin.getText());
            u.setCin(ecinValue);
            cr.addEntity(u);
            resfreshexpert();
        }
        
    }
     public String controleDeSaisie(){
        
        String erreur="";
        if(enom.getText().trim().isEmpty()){
            erreur+="-nom vide\n";
        }
        if(eprenom.getText().trim().isEmpty()){
            erreur+="-prenom vide\n";
        }
        if(ecin.getText().trim().isEmpty()){
            erreur+="-lieu vide\n";
        }
        
        return erreur;
    }

    @FXML
    private void clearExp(ActionEvent event) {
        enom.setText("");
        eprenom.setText(""); 
        ecin.setText("");
       
    }

    @FXML
    private void supprimerExp(ActionEvent event) {
                if (tvexpert.getSelectionModel().getSelectedItem() != null) {
        int id = tvexpert.getSelectionModel().getSelectedItem().getId();
        
        // Create a confirmation dialog box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet Expert?");
        Optional<ButtonType> result = alert.showAndWait();
        
        // If the user confirms the deletion, call the supprimer() method and refresh the list
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cr.supprimerEntity(id);
            resfreshexpert();
        }
    } else {
        // Display an error message if no item is selected
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner un Expert");
        alert.showAndWait();
    }
    }

    @FXML
    private void resfreshexpert() {
        data.clear();
        data=FXCollections.observableArrayList(cr.display());       
        cenom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ceprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cecin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        tvexpert.setItems(data);
    }
    }

   
    

