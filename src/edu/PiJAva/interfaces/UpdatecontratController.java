/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import edu.PiJAva.entities.Contrat;
import edu.PiJAva.entities.Voiture;
import edu.PiJAva.services.ContratCRUD;
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
import javafx.fxml.Initializable;
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
public class UpdatecontratController implements Initializable {

    @FXML
    private TextField nomc_id;
    @FXML
    private TextField prenomc_id;
    @FXML
    private TextField cinc_id;
    @FXML
    private TextField regionc_id;
    @FXML
    private DatePicker datedebutc_id;
    @FXML
    private Button savectbtn;
    @FXML
    private ComboBox<String> typec_id;
    @FXML
    private DatePicker datefinc_id1;
    @FXML
    private TextField prixc_id;
    private final Contrat selectedContrat;

    public UpdatecontratController(Contrat contrat) {
        selectedContrat = contrat;
    }
    private AddcontratController addcontratController;

    ContratCRUD contrat = new ContratCRUD();

    public void setAddvoitureController(AddcontratController addcontratController) {
        this.addcontratController = addcontratController;
    }
    private String[] typeList = {"Assurance responsabilité civile  ", "Assurance tous risques ", "Assurance collision", "Assurance vol ", "Assurance incendie "};

    public void contratTypeList() {
        List<String> type = new ArrayList<>();
        for (String x : typeList) {
            type.add(x);
        }
        ObservableList listData = FXCollections.observableArrayList(type);
        typec_id.setItems(listData);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        contratTypeList();
        Contrat v = selectedContrat;
        nomc_id.setText(v.getNomconducteur());
        prenomc_id.setText(v.getPrenomconducteur());
        cinc_id.setText(String.valueOf(v.getCin()));

        regionc_id.setText(v.getRegion());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(String.valueOf(v.getDatedebut()), formatter);
        datedebutc_id.setValue(localDate);

        LocalDate localDate1 = LocalDate.parse(String.valueOf(v.getDatefin()), formatter);
        datefinc_id1.setValue(localDate1);

        prixc_id.setText(String.valueOf(v.getPrix()));
        //v.setTypecarburant((String) typecarburant_id.getSelectionModel().getSelectedItem());
        typec_id.setValue(v.getType());

        //v.setEtat((String) etat_id.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void savecontrat(ActionEvent event) {
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
             selectedContrat.setNomconducteur(nomc_id.getText());
        selectedContrat.setPrenomconducteur(prenomc_id.getText());
        selectedContrat.setCin(Integer.valueOf(cinc_id.getText()));
        selectedContrat.setRegion(regionc_id.getText());
        selectedContrat.setDatedebut(Date.valueOf(datedebutc_id.getValue()));
        selectedContrat.setDatefin(Date.valueOf(datefinc_id1.getValue()));

        selectedContrat.setPrix(Float.parseFloat(prixc_id.getText()));
        selectedContrat.setType((String) typec_id.getSelectionModel().getSelectedItem());

        contrat.update(selectedContrat.getId(), selectedContrat);
        Stage stage = (Stage) savectbtn.getScene().getWindow();
        stage.close();

        addcontratController.refreshList();
        }
        
       

    }
    }
    
     public String controleDeSaisie(){
    String erreur = "";
    
    if (nomc_id.getText().trim().isEmpty()) {
        erreur += "- Nom vide\n";
    } else if (!nomc_id.getText().matches("^[a-zA-Z ]+$")) {
        erreur += "- Nom ne doit contenir que des lettres et des espaces\n";
    }
    
    if (prenomc_id.getText().trim().isEmpty()) {
        erreur += "- Prenom vide\n";
    } else if (!prenomc_id.getText().matches("^[a-zA-Z ]+$")) {
        erreur += "- Prenom ne doit contenir que des lettres et des espaces\n";
    }
    
    if (cinc_id.getText().trim().isEmpty()) {
        erreur += "- Carte d'identité vide\n";
    } else if (!cinc_id.getText().matches("^\\d{8}$")) {
        erreur += "- carte d'identité doit contenir exactement 8 chiffres\n";
    }
    
    if (regionc_id.getText().trim().isEmpty()) {
        erreur += "- Region vide\n";
    } else if (!regionc_id.getText().matches("^[a-zA-Z ]+$")) {
        erreur += "- Region ne doit contenir que des lettres et des espaces\n";
    }
    
    if (prixc_id.getText().trim().isEmpty()) {
        erreur += "- Prix vide\n";
    } else if (!prixc_id.getText().matches("^[1-9]*\\.?[1-9]+$")) {
        erreur += "- Prix doit être un nombre positif\n";
    }
    
    if (datedebutc_id.getValue() == null) {
        erreur += "- Date de début vide\n";
    } else if (datedebutc_id.getValue().isBefore(LocalDate.now())) {
        erreur += "- Date de début doit être dans le futur\n";
    }
    
    if (datefinc_id1.getValue() == null) {
        erreur += "- Date de fin vide\n";
    } else if (datefinc_id1.getValue().isBefore(LocalDate.now())) {
        erreur += "- Date de fin doit être dans le futur\n";
    }
    
    if (typec_id.getValue() == null) {
        erreur += "- Type de carburant vide\n";
    }
    
    if(datedebutc_id.getValue() != null && datefinc_id1.getValue() != null){
        if(datefinc_id1.getValue().isBefore(datedebutc_id.getValue())){
            erreur+="-La date de fin doit être postérieure à la date de début\n";
        }
    }
    
    return erreur;
}

}
