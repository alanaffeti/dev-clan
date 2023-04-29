/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.JavaFxPidev.gui;

import edu.JavaFxPidev.entities.Voiture;
import edu.JavaFxPidev.services.VoitureCRUD;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mouad
 */
public class AddvoitureController implements Initializable {
    
    VoitureCRUD voiture = new VoitureCRUD();

    @FXML
    private TextField marque_id;
    @FXML
    private TextField modele_id;
    @FXML
    private TextField matricule_id;
    @FXML
    private TextField kilometrage_id;
    @FXML
    private ComboBox<?> typevoiture_id;
    @FXML
    private ComboBox<?> etat_id;
    @FXML
    private ComboBox<?> typecarburant_id;

    @FXML
    private DatePicker datefabrication_id;

    private String[] etatList = {"En circulation", "Hors circulation", "En réparation", "Volée"};
    @FXML
    private TableColumn<Voiture, Integer> col_id;
    @FXML
    private TableColumn<Voiture, String> col_marque;
    @FXML
    private TableColumn<Voiture, String> col_modele;
    @FXML
    private TableColumn<Voiture, String> col_matricule;
    @FXML
    private TableColumn<Voiture, Integer> col_kilometrage;
    @FXML
    private TableColumn<Voiture, String> col_typecarburant;
    @FXML
    private TableColumn<Voiture, String> col_typevoiture;
    @FXML
    private TableColumn<Voiture, String> col_datefabrication;
    @FXML
    private TableView<Voiture> table_id;
    ObservableList<Voiture> data = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> col_etat;
    private UpdatevoitureController updatevoiturecontroller;
    @FXML
    private Button addbtn;
    @FXML
    private Button deletebtn;
    @FXML
    private Button updatebtn1;
    @FXML
    private Button addcontratbtn;
    @FXML
    private BorderPane borderpane_id;
    @FXML
    private TextField recherche_id;
                

    
    public void voitureEtatList() {
        List<String> etat = new ArrayList<>();
        for (String x : etatList) {
            etat.add(x);
        }
        ObservableList listData = FXCollections.observableArrayList(etat);
        etat_id.setItems(listData);

    }
    private String[] typeCarburantList = {"Essence ", "Diesel ", "Gaz naturel", "Électricité ", "Hydrogène "};

    public void voitureTypeCarburantList() {
        List<String> typecarburant = new ArrayList<>();
        for (String x : typeCarburantList) {
            typecarburant.add(x);
        }
        ObservableList listData = FXCollections.observableArrayList(typecarburant);
        typecarburant_id.setItems(listData);

    }
    private String[] typeVoitureList = {"Berline ", "Coupé ", "SUV ", "Monospace ", "Cabriolet ", "Crossover ", "Compacte "};

    public void voitureTypeVoitureList() {
        List<String> typevoiture = new ArrayList<>();
        for (String x : typeVoitureList) {
            typevoiture.add(x);
        }
        ObservableList listData = FXCollections.observableArrayList(typevoiture);
        typevoiture_id.setItems(listData);

    }
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        voitureEtatList();
        voitureTypeCarburantList();
        voitureTypeVoitureList();

        refreshList();
                        recherche_avance();



    }
        public TableView<Voiture> getTableView() {
        return table_id;
    }
        
    @FXML
    private void addvoiture(ActionEvent event) {
        String erreur = controleDeSaisie();
        if (erreur.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        } else {
        Voiture v = new Voiture();
        v.setMarque(marque_id.getText());
        v.setTypevoiture((String) typevoiture_id.getSelectionModel().getSelectedItem());
        v.setMatricule(matricule_id.getText());
        v.setModele(modele_id.getText());
        v.setDatefabrication(Date.valueOf(datefabrication_id.getValue()));
        v.setTypecarburant((String) typecarburant_id.getSelectionModel().getSelectedItem());
        v.setKilometrage(Integer.valueOf(kilometrage_id.getText()));
        v.setEtat((String) etat_id.getSelectionModel().getSelectedItem());
//
        voiture.addEntity(v);
        refreshList();
    }
    }

    public void refreshList() {
        data.clear();
        data = FXCollections.observableArrayList(voiture.display());
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        col_modele.setCellValueFactory(new PropertyValueFactory<>("modele"));
        col_matricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        col_kilometrage.setCellValueFactory(new PropertyValueFactory<>("kilometrage"));
        col_typecarburant.setCellValueFactory(new PropertyValueFactory<>("typecarburant"));
        col_typevoiture.setCellValueFactory(new PropertyValueFactory<>("typevoiture"));
        col_datefabrication.setCellValueFactory(new PropertyValueFactory<>("datefabrication"));
        col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));


        table_id.setItems(data);
    }

    @FXML
    private void deletevoiture(ActionEvent event) {
        if (table_id.getSelectionModel().getSelectedItem() != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette voiture ?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            int id = table_id.getSelectionModel().getSelectedItem().getId();
            voiture.delete(id);
                        refreshList();

        }
        }
            
            
        
        else 
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune voiture sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une voiture dans la table.");
            alert.showAndWait();
        }

    }

    

    @FXML
    private void upvoiture(ActionEvent event) {
         if (table_id.getSelectionModel().getSelectedItem() != null) {
            try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updatevoiture.fxml"));
            UpdatevoitureController controller = new UpdatevoitureController(table_id.getSelectionModel().getSelectedItem());
            fxmlLoader.setController(controller);
            
            
            
            updatevoiturecontroller = fxmlLoader.getController();
            updatevoiturecontroller.setAddvoitureController(this);
        
        
        
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            } catch (Exception e) {
                    e.printStackTrace();

                System.out.println("can't load the windoww");
            }

        } else {
          Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune voiture sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une voiture dans la table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void addcontrat(ActionEvent event) {
     if (table_id.getSelectionModel().getSelectedItem() != null) {   
     try {
            Voiture selectedVoiture = table_id.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(
            getClass().getResource("addcontrat.fxml"));
            Parent root = (Parent) loader.load();
            AddcontratController contractViewController = loader.getController();
            contractViewController.setSelectedCar(selectedVoiture);

            
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
     }
     catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }
     else{
         Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune voiture sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une voiture dans la table.");
            alert.showAndWait();
     }
    }
    
    
    
    
    
    
    public String controleDeSaisie(){
        
        String erreur="";
        if(marque_id.getText().trim().isEmpty()){
            erreur+="-marque vide\n";
        }
        if(modele_id.getText().trim().isEmpty()){
            erreur+="-modele vide\n";
        }
        if(matricule_id.getText().trim().isEmpty()){
            erreur+="-matricule vide\n";
        }
        else if(!matricule_id.getText().matches("^\\d{4}\\stunisie\\s\\d{3}$")) {
    erreur+="-matricule doit être sous forme de YYYY tunisie XXX où YYYY est 4 chiffres et XXX est 3 chiffres.\n";
}
        if(kilometrage_id.getText().trim().isEmpty()){
            erreur+="-kilometrage vide\n";
        }
        if(!kilometrage_id.getText().matches("\\d+")){
            erreur+="-kilometrage doit contenir des chiffres uniquement\\n";
        }
        if(datefabrication_id.getValue()==null){
            erreur+="-date de fabrication  vide\n";
        }
        if(etat_id.getValue()== null){
            erreur+="-etat vide\n";
        }
        if(typecarburant_id.getValue()== null){
            erreur+="-type carburant vide\n";
        }
        
        
        return erreur;
    }

//    @FXML
//    private void handleFileChooserButtonAction(ActionEvent event) {
//   FileChooser fileChooser = new FileChooser();
//    fileChooser.setTitle("Select Image File");
//    fileChooser.getExtensionFilters().addAll(
//        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
//    );
//    Stage fileChooserStage = new Stage();
//    File selectedFile = fileChooser.showOpenDialog(fileChooserStage);    
//    if (selectedFile != null) {
//        String imageUrl = selectedFile.toURI().toString();
//        System.out.println("Selected file: " + selectedFile.getPath());
//        try {
//            imageanalyse imageAnalysis = new imageanalyse(imageUrl);
//            String year = imageAnalysis.getYear();
//            String make = imageAnalysis.getMake();
//            String model = imageAnalysis.getModel();
//            System.out.println("Year: " + year);
//            System.out.println("Make: " + make);
//            System.out.println("Model: " + model);
//            modele_id.setText(model);
//            datefabrication_id.setValue(LocalDate.of(Integer.parseInt(year), 1, 1));
//        } catch (Exception e) {
//            System.err.println("Error analyzing image: " + e.getMessage());
//            e.printStackTrace();
//        }
//    } 
//    }}
    public void recherche_avance(){
        //remplire lobservablelist
        data.clear();
        data.addAll(voiture.display());
        //liste filtrer
        FilteredList<Voiture> filtreddata=new FilteredList<>(data,u->true);
        //creation du listener a partir du textfield
        recherche_id.textProperty().addListener((observable,oldValue,newValue)->{
            filtreddata.setPredicate(voiture->{
                if(newValue==null||newValue.isEmpty()){
                    return true;
                }
                if(String.valueOf(voiture.getId()).indexOf(newValue)!=-1){
                    return true;
                }
                else if(voiture.getMarque().indexOf(newValue)!=-1){
                    return true;
                }
                
                else if(voiture.getModele().indexOf(newValue)!=-1){
                    return true;
                }
                else if(voiture.getMatricule().indexOf(newValue)!=-1){
                    return true;
                }
                
                else if(String.valueOf(voiture.getDatefabrication()).indexOf(newValue)!=-1){
                    return true;
                }
                else if(String.valueOf(voiture.getKilometrage()).indexOf(newValue)!=-1){
                    return true;
                }
                else if(String.valueOf(voiture.getTypecarburant()).indexOf(newValue)!=-1){
                    return true;
                }
                 else if(String.valueOf(voiture.getTypevoiture()).indexOf(newValue)!=-1){
                    return true;
                }
                 else if(String.valueOf(voiture.getEtat()).indexOf(newValue)!=-1){
                    return true;
                }
                else{
                    return false;
                }
            });
            table_id.setItems(filtreddata);
        });
    }

    
}
