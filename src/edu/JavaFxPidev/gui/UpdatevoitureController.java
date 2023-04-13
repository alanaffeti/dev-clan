/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.JavaFxPidev.gui;

import edu.JavaFxPidev.entities.Voiture;
import edu.JavaFxPidev.services.VoitureCRUD;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mouad
 */
public class UpdatevoitureController implements Initializable {
     private AddvoitureController addvoitureController;

    // ...

    public void setAddvoitureController(AddvoitureController addvoitureController) {
        this.addvoitureController = addvoitureController;
    }

    private final Voiture selectedVoiture;
    @FXML
    private TextField umarque_id;
    @FXML
    private TextField umodele_id;
    @FXML
    private TextField umatricule_id;
    @FXML
    private TextField ukilometrage_id;
    @FXML
    private ComboBox<String> uetat_id;
    @FXML
    private ComboBox<String> utypecarburant_id;
    @FXML
    private DatePicker udatefabrication_id;
    @FXML
    private ComboBox<String> utypevoiture_id;

    public UpdatevoitureController(Voiture voiture) {
        selectedVoiture = voiture;
    }
    VoitureCRUD voiture = new VoitureCRUD();

    @FXML
    private Button updatebtn;
    private String[] etatList = {"En circulation", "Hors circulation", "En réparation", "Volée"};

    public void voitureEtatList() {
        List<String> etat = new ArrayList<>();
        for (String x : etatList) {
            etat.add(x);
        }
        ObservableList listData = FXCollections.observableArrayList(etat);
        uetat_id.setItems(listData);

    }
    private String[] typeCarburantList = {"Essence ", "Diesel ", "Gaz naturel", "Électricité ", "Hydrogène "};

    public void voitureTypeCarburantList() {
        List<String> typecarburant = new ArrayList<>();
        for (String x : typeCarburantList) {
            typecarburant.add(x);
        }
        ObservableList listData = FXCollections.observableArrayList(typecarburant);
        utypecarburant_id.setItems(listData);

    }
    private String[] typeVoitureList = {"Berline ", "Coupé ", "SUV ", "Monospace ", "Cabriolet ", "Crossover ", "Compacte "};

    public void voitureTypeVoitureList() {
        List<String> typevoiture = new ArrayList<>();
        for (String x : typeVoitureList) {
            typevoiture.add(x);
        }
        ObservableList listData = FXCollections.observableArrayList(typevoiture);
        utypevoiture_id.setItems(listData);

    }
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        voitureEtatList();
        voitureTypeCarburantList();
        voitureTypeVoitureList();

        
        
        Voiture v = selectedVoiture;
        umarque_id.setText(v.getMarque());
        umatricule_id.setText(v.getMatricule());
        umodele_id.setText(v.getModele());
        
        utypevoiture_id.setValue(v.getTypevoiture());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(String.valueOf(v.getDatefabrication()), formatter);
        udatefabrication_id.setValue(localDate);
       
        utypecarburant_id.setValue(v.getTypecarburant());
        //v.setTypecarburant((String) typecarburant_id.getSelectionModel().getSelectedItem());
        ukilometrage_id.setText(String.valueOf(v.getKilometrage()));
        
        uetat_id.setValue(v.getEtat());
        //v.setEtat((String) etat_id.getSelectionModel().getSelectedItem());

    }


    @FXML
    private void updatevoiture(ActionEvent event) {
         String erreur = controleDeSaisie();
        if (erreur.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        } else {
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir modifier ce contrat ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
        selectedVoiture.setMarque(umarque_id.getText());
        selectedVoiture.setModele(umodele_id.getText());
        selectedVoiture.setMatricule(umatricule_id.getText());
        selectedVoiture.setKilometrage(Integer.valueOf(ukilometrage_id.getText()));
        selectedVoiture.setDatefabrication(Date.valueOf(udatefabrication_id.getValue()));

        selectedVoiture.setEtat((String) uetat_id.getSelectionModel().getSelectedItem());
        selectedVoiture.setTypecarburant((String) utypecarburant_id.getSelectionModel().getSelectedItem());
        selectedVoiture.setTypevoiture((String) utypevoiture_id.getSelectionModel().getSelectedItem());

        voiture.update(selectedVoiture.getId(), selectedVoiture);
        Stage stage = (Stage) updatebtn.getScene().getWindow();
        stage.close();
        

        addvoitureController.refreshList();
        }
        

    }
    }
    
    
    
    
    public String controleDeSaisie(){
        
        String erreur="";
        if(umarque_id.getText().trim().isEmpty()){
            erreur+="-marque vide\n";
        }
        if(umodele_id.getText().trim().isEmpty()){
            erreur+="-modele vide\n";
        }
        if(umatricule_id.getText().trim().isEmpty()){
            erreur+="-matricule vide\n";
        }
        if(ukilometrage_id.getText().trim().isEmpty()){
            erreur+="-kilometrage vide\n";
        }
        if(!ukilometrage_id.getText().matches("\\d+")){
            erreur+="-kilometrage doit contenir des chiffres uniquement\\n";
        }
        if(udatefabrication_id.getValue()==null){
            erreur+="-date de fabrication  vide\n";
        }
        if(uetat_id.getValue()== null){
            erreur+="-etat vide\n";
        }
        if(utypecarburant_id.getValue()== null){
            erreur+="-type carburant vide\n";
        }
        
        
        return erreur;
    }

}
