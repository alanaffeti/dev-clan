/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import edu.PiJAva.entities.Rec;
import edu.PiJAva.entities.Rep;
import edu.PiJAva.services.Reponse;
import edu.PiJAva.tools.SmsAPI1;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class InterfaceReponseController implements Initializable {

    @FXML
    private TableView<Rep> tvrep;
    @FXML
    private TableColumn<Rep, Integer> col_id;
    @FXML
    private TableColumn<Rep, String> col_nom;
    @FXML
    private TableColumn<Rep, String> col_prenom;
    @FXML
    private TableColumn<Rep, String> col_type;
    @FXML
    private Button tf_add;
    @FXML
    private RadioButton rbnormale;
    @FXML
    private RadioButton rbadmin;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
     ObservableList<Rep>data=FXCollections.observableArrayList();
     Rep v =new Rep();
    Reponse sv = new Reponse();
    
    
    private Rec selectedrec;
    @FXML
    private Button backr;
    

    public void setSelectedCar(Rec rec) {
        selectedrec = rec;

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void add(ActionEvent event) {
        String erreur=controleDeSaisie();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
            return;

        }
          v.setNom(tfnom.getText());
            v.setPrenom(tfprenom.getText());
            v.setType(rbnormale.isSelected() ? "traité" : "non traité");
            sv.addEntity2(v);
            SmsAPI1.send("+21655638527", "Votre reclamation a bien été traité");

            Refresh();
    }

    @FXML
    private void edit(ActionEvent event) {
                    String erreur=controleDeSaisie();
                if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        }

        if(tvrep.getSelectionModel().getSelectedItem()!=null){
            int id=tvrep.getSelectionModel().getSelectedItem().getId();
            v.setNom(tfnom.getText());
            v.setPrenom(tfprenom.getText());
           
            v.setType(rbnormale.isSelected() ? "traité" : "non traité");
             Notifications notificationBuilder = Notifications.create()
                .title("Message").text("Reclamation modifée avec succés!").graphic(null)
                .hideAfter(javafx.util.Duration.seconds(4))
                .position(Pos.CENTER).onAction(new EventHandler<ActionEvent>(){
                 public void handle(ActionEvent event)
                 {
                     System.out.println("clicked ON");
                 }
                });
        notificationBuilder.darkStyle();
        notificationBuilder.show();

            
            sv.updateEntity(id,v);
            Refresh();
            
        }else {
        // Display an error message if no item is selected
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une Reclamation");
        alert.showAndWait();
    }
    }
    
   

    @FXML
    private void delete(ActionEvent event) {
         if(tvrep.getSelectionModel().getSelectedItem()!=null){
            int id=tvrep.getSelectionModel().getSelectedItem().getId();
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette Reclamation?");
        Optional<ButtonType> result = alert.showAndWait();
         // If the user confirms the deletion, call the supprimer() method and refresh the list
        if (result.isPresent() && result.get() == ButtonType.OK) {
            sv.deleteEntity(id);
    }
}}

      @FXML
    private void Refresh() {
           data.clear();
        data=FXCollections.observableArrayList(sv.afficher());
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        col_type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        
        tvrep.setItems(data);
    }
    private void fillforum(MouseEvent event) {
          v=tvrep.getSelectionModel().getSelectedItem();
        tfnom.setText(v.getNom());
        tfprenom.setText(v.getPrenom());
        if ("traité".equals(v.getType()) ){rbnormale.setSelected(true);
        } 
        else rbadmin.setSelected(true);
        
    }
public String controleDeSaisie(){
       
        String erreur="";
        if(tfnom.getText().trim().isEmpty()){
            erreur+="-nom vide\n";
        }
        if(tfprenom.getText().trim().isEmpty()){
            erreur+="-prenom vide\n";
        }
     
       
        
      
       
        return erreur;    
}

    @FXML
    private void backr(ActionEvent event) {
                FXMain.setScene("InterfaceReclamation");

    }
}
