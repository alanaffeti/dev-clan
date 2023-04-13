/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.JavaFxPidev.gui;

import edu.JavaFxPidev.entities.Contrat;
import edu.JavaFxPidev.entities.Voiture;
import edu.JavaFxPidev.services.ContratCRUD;
import edu.JavaFxPidev.services.VoitureCRUD;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mouad
 */
public class AddcontratController implements Initializable {

    ContratCRUD contrat = new ContratCRUD();

    @FXML
    private TextField nom_id;
    @FXML
    private TextField prenom_id;
    @FXML
    private TextField cin_id;
    @FXML
    private TextField region_id;
    @FXML
    private DatePicker datedebut_id;
    @FXML
    private Button savecontratbtn;
    @FXML
    private ComboBox<String> type_id;
    @FXML
    private Button deletecontratbtn;
    @FXML
    private Button updatecontratbtn;
    @FXML
    private DatePicker datefin_id1;
    @FXML
    private TextField prix_id;
    private UpdatecontratController updatecontratcontroller;

    @FXML
    private TableColumn<Contrat, Integer> col_cid;
    @FXML
    private TableColumn<Contrat, Integer> col_vid;
    @FXML
    private TableColumn<Contrat, String> col_datedebut;
    @FXML
    private TableColumn<Contrat, String> col_datefin;
    @FXML
    private TableColumn<Contrat, Integer> col_prix;
    @FXML
    private TableColumn<Contrat, String> col_type;
    @FXML
    private TableColumn<Contrat, String> col_nom;
    @FXML
    private TableColumn<Contrat, String> col_prenom;
    @FXML
    private TableColumn<Contrat, Integer> col_cin;
    @FXML
    private TableColumn<Contrat, String> col_region;
    @FXML
    private TableView<Contrat> tablec_id;
    ObservableList<Contrat> data = FXCollections.observableArrayList();
    @FXML
    private Button back;

    public void refreshList() {
        data.clear();

        data = FXCollections.observableArrayList(contrat.display());
        col_cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_vid.setCellValueFactory(new PropertyValueFactory<>("voiture_id"));
        col_datedebut.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        col_datefin.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        col_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nomconducteur"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenomconducteur"));
        col_cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        col_region.setCellValueFactory(new PropertyValueFactory<>("region"));

        tablec_id.setItems(data);
    }

    private Voiture selectedvoiture;

    public void setSelectedCar(Voiture voiture) {
        selectedvoiture = voiture;
    }

    private String[] typeList = {"Assurance responsabilité civile  ", "Assurance tous risques ", "Assurance collision", "Assurance vol ", "Assurance incendie "};

    public void contratTypeList() {
        List<String> type = new ArrayList<>();
        for (String x : typeList) {
            type.add(x);
        }
        ObservableList listData = FXCollections.observableArrayList(type);
        type_id.setItems(listData);

    }

    public TableView<Contrat> getTableView() {
        return tablec_id;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contratTypeList();
        refreshList();
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
        Contrat v = new Contrat();
        v.setVoiture_id(selectedvoiture.getId());
        v.setNomconducteur(nom_id.getText());
        v.setPrenomconducteur(prenom_id.getText());

        // v.setTypevoiture((String) typevoiture_id.getSelectionModel().getSelectedItem());
        v.setCin(Integer.valueOf(cin_id.getText()));
        v.setRegion(region_id.getText());
        v.setDatedebut(Date.valueOf(datedebut_id.getValue()));
        v.setDatefin(Date.valueOf(datefin_id1.getValue()));
        v.setPrix(Float.parseFloat(prix_id.getText()));

        v.setType((String) type_id.getSelectionModel().getSelectedItem());

//
        contrat.addEntity(v);
        refreshList();
    }
    }

    @FXML
    private void deletecontrat(ActionEvent event) {
        if (tablec_id.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce contrat ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            int id = tablec_id.getSelectionModel().getSelectedItem().getId();
            contrat.delete(id);
            refreshList();
        }
            
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune voiture sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un contrat dans la table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void updatecontrat(ActionEvent event) {
        if (tablec_id.getSelectionModel().getSelectedItem() != null) {
            
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updatecontrat.fxml"));
                UpdatecontratController controller = new UpdatecontratController(tablec_id.getSelectionModel().getSelectedItem());
                fxmlLoader.setController(controller);

                updatecontratcontroller = fxmlLoader.getController();
                updatecontratcontroller.setAddvoitureController(this);

                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();

            }

        } else {
           Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune voiture sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un contrat dans la table.");
            alert.showAndWait();
        }

    }

    @FXML
    private void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("addvoiture.fxml"));
            Parent root = (Parent) loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            refreshList();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    
    
     public String controleDeSaisie(){
    String erreur = "";
    
    if (nom_id.getText().trim().isEmpty()) {
        erreur += "- Nom vide\n";
    } else if (!nom_id.getText().matches("^[a-zA-Z ]+$")) {
        erreur += "- Nom ne doit contenir que des lettres et des espaces\n";
    }
    
    if (prenom_id.getText().trim().isEmpty()) {
        erreur += "- Prenom vide\n";
    } else if (!prenom_id.getText().matches("^[a-zA-Z ]+$")) {
        erreur += "- Prenom ne doit contenir que des lettres et des espaces\n";
    }
    
    if (cin_id.getText().trim().isEmpty()) {
        erreur += "- Carte d'identité vide\n";
    } else if (!cin_id.getText().matches("^\\d{8}$")) {
        erreur += "- carte d'identité doit contenir exactement 8 chiffres\n";
    }
    
    if (region_id.getText().trim().isEmpty()) {
        erreur += "- Region vide\n";
    } else if (!region_id.getText().matches("^[a-zA-Z ]+$")) {
        erreur += "- Region ne doit contenir que des lettres et des espaces\n";
    }
    
    if (prix_id.getText().trim().isEmpty()) {
        erreur += "- Prix vide\n";
    } else if (!prix_id.getText().matches("^[1-9]*\\.?[1-9]+$")) {
        erreur += "- Prix doit être un nombre positif\n";
    }
    
    if (datedebut_id.getValue() == null) {
        erreur += "- Date de début vide\n";
    } else if (datedebut_id.getValue().isBefore(LocalDate.now())) {
        erreur += "- Date de début doit être dans le futur\n";
    }
    
    if (datefin_id1.getValue() == null) {
        erreur += "- Date de fin vide\n";
    } else if (datefin_id1.getValue().isBefore(LocalDate.now())) {
        erreur += "- Date de fin doit être dans le futur\n";
    }
    
    if (type_id.getValue() == null) {
        erreur += "- Type de carburant vide\n";
    }
    
    if(datedebut_id.getValue() != null && datefin_id1.getValue() != null){
        if(datefin_id1.getValue().isBefore(datedebut_id.getValue())){
            erreur+="-La date de fin doit être postérieure à la date de début\n";
        }
    }
    
    return erreur;
}
}
