/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import edu.PiJAva.entities.Rec;
import edu.PiJAva.entities.User;
import edu.PiJAva.services.PersonneCRUD;
import edu.PiJAva.services.ServiceUser;
import edu.PiJAva.tools.MailAPI;
import edu.PiJAva.tools.SmsAPI1;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.mail.MessagingException;
import static jdk.nashorn.internal.objects.NativeString.search;
import org.controlsfx.control.Notifications;


/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class InterfaceReclamationController implements Initializable {
 User u1 =new User();
    ServiceUser su1=new ServiceUser();
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfdescription;
    @FXML
    private TableColumn<Rec, Integer> col_id;
    @FXML
    private TableColumn<Rec, String> col_nom;
    @FXML
    private TableColumn<Rec, String> col_prenom;
    @FXML
    private TableColumn<Rec, String> col_email;
    @FXML
    private TableColumn<Rec, String> col_description;
    @FXML
    private TableColumn<Rec, String> col_type;
    Rec u =new Rec();
    PersonneCRUD su = new PersonneCRUD();
    @FXML
    private RadioButton rbnormale;
    @FXML
    private RadioButton rbadmin;
    @FXML
    private TableView<Rec> tvrec;
    ObservableList<Rec> 
     data=FXCollections.observableArrayList();
    @FXML
    private Button tf_add;
    @FXML
    private TextField tf_rech;
    @FXML
    private Button tf_btnrech;
    @FXML
    private Button tf_btnrech1;
    @FXML
    private Button reponse;
    @FXML
    private Button reponse1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        u1=su1.getUserByEmail(LoginController.iduserglobal);
        // TODO
    }    
            int badWordCount=0;
            int countdown=60;

    @FXML
    private void add(ActionEvent event) throws MessagingException {
        String erreur=controleDeSaisie();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
            return;
        }
             // Check for bad words
    List<String> badWords = Arrays.asList("akrem", "ola","merde");
    for (String badWord : badWords) {
        if (tfdescription.getText().toLowerCase().contains(badWord)) {
            badWordCount++;
            if (badWordCount >= 3) {
                // Disable the post button and show a countdown timer
               // 60 seconds = 1 minute
                Label countdownLabel = new Label(String.format("Posting is disabled for %d seconds", countdown));
                HBox countdownBox = new HBox(countdownLabel);
                countdownBox.setAlignment(Pos.CENTER);
                countdownBox.setPadding(new Insets(10));
                Alert banAlert = new Alert(Alert.AlertType.ERROR);
                banAlert.setTitle("Error");
                banAlert.setHeaderText("You have been banned for 1 minute for using bad language");
                banAlert.getDialogPane().setContent(countdownBox);
                banAlert.show();

                // Start the countdown timer
                Timeline timeline = new Timeline();
                timeline.setCycleCount(countdown);
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        countdown--;
                        if (countdown == 0) {
                            // Enable the post button and close the alert
                            tf_add.setDisable(false);
                            banAlert.setResult(ButtonType.OK);
                        } else {
                            countdownLabel.setText(String.format("Posting is disabled for %d seconds", countdown));
                        }
                    }
                }));
                timeline.play();

                return;
            }
            Alert badWordAlert = new Alert(Alert.AlertType.ERROR);
            badWordAlert.setTitle("Error");
            badWordAlert.setHeaderText("Your reclamation contains a bad word");
            badWordAlert.showAndWait();
            return;
        }
    }
            u.setId_user(u1.getId());
            u.setNom(tfnom.getText());
            u.setPrenom(tfprenom.getText());
            u.setEmail(tfemail.getText());
            u.setDescription(tfdescription.getText());
            u.setType(rbnormale.isSelected() ? "normale" : "admin");
            su.addEntity2(u);
            Refresh();
     
            MailAPI.sendMail(u.getEmail(), "Accusé de réception ", "Bonjour Mr/Mme  " + u.getNom() +" "+ u.getPrenom() +  " Nous avons bien reçu votre réclamation.Nous sommes sincèrement désolés pour ce désagrément.Nous mettons tout en œuvre pour résoudre ce problème au plus vite et reviendrons vers vous via un SMS ");
             Notifications notificationBuilder = Notifications.create()
                .title("Message").text("Reclamation Ajoutée avec succés!").graphic(null)
                .hideAfter(javafx.util.Duration.seconds(4))
                .position(Pos.CENTER).onAction(new EventHandler<ActionEvent>(){
                 public void handle(ActionEvent event)
                 {
                     System.out.println("clicked ON");
                 }
                });
        notificationBuilder.darkStyle();
        notificationBuilder.show();
        
    }

    

    @FXML
    private void edit(ActionEvent event) {
                String erreur=controleDeSaisie();
                if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
            return;
        }
            List<String> badWords = Arrays.asList("akrem", "ola","merde");
    for (String badWord : badWords) {
        if (tfdescription.getText().toLowerCase().contains(badWord)) {
            badWordCount++;
            if (badWordCount >= 3) {
                // Disable the post button and show a countdown timer
               // 60 seconds = 1 minute
                Label countdownLabel = new Label(String.format("Posting is disabled for %d seconds", countdown));
                HBox countdownBox = new HBox(countdownLabel);
                countdownBox.setAlignment(Pos.CENTER);
                countdownBox.setPadding(new Insets(10));
                Alert banAlert = new Alert(Alert.AlertType.ERROR);
                banAlert.setTitle("Error");
                banAlert.setHeaderText("You have been banned for 1 minute for using bad language");
                banAlert.getDialogPane().setContent(countdownBox);
                banAlert.show();

                // Start the countdown timer
                Timeline timeline = new Timeline();
                timeline.setCycleCount(countdown);
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        countdown--;
                        if (countdown == 0) {
                            // Enable the post button and close the alert
                            tf_add.setDisable(false);
                            banAlert.setResult(ButtonType.OK);
                        } else {
                            countdownLabel.setText(String.format("Posting is disabled for %d seconds", countdown));
                        }
                    }
                }));
                timeline.play();

                return;
            }
            Alert badWordAlert = new Alert(Alert.AlertType.ERROR);
            badWordAlert.setTitle("Error");
            badWordAlert.setHeaderText("Your reclamation contains a bad word");
            badWordAlert.showAndWait();
            return;
        }
    }
            

        if(tvrec.getSelectionModel().getSelectedItem()!=null){
            int id=tvrec.getSelectionModel().getSelectedItem().getId();
            
            
            u.setId_user(u.getId_user());

            u.setNom(tfnom.getText());
            u.setPrenom(tfprenom.getText());
            u.setEmail(tfemail.getText());
            u.setDescription(tfdescription.getText());
            u.setType(rbnormale.isSelected() ? "normale" : "admin");
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

            
            su.updateEntity(id,u);
            Refresh();
            
        }else {
        // Display an error message if no item is selected
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une Reclamation");
        alert.showAndWait();
    }
    }
    

    @FXML
    private void delete(ActionEvent event) {
          if(tvrec.getSelectionModel().getSelectedItem()!=null){
            int id=tvrec.getSelectionModel().getSelectedItem().getId();
             Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette Reclamation?");
        Optional<ButtonType> result = alert.showAndWait();
         // If the user confirms the deletion, call the supprimer() method and refresh the list
        if (result.isPresent() && result.get() == ButtonType.OK) {
            su.deleteEntity(id);
 Notifications notificationBuilder = Notifications.create()
                .title("Message").text("Reclamation supprimée avec succés!").graphic(null)
                .hideAfter(javafx.util.Duration.seconds(4))
                .position(Pos.CENTER).onAction(new EventHandler<ActionEvent>(){
                 public void handle(ActionEvent event)
                 {
                     System.out.println("clicked ON");
                 }
                });
        notificationBuilder.darkStyle();
        notificationBuilder.show();
            Refresh();
            
        }

          
        
    } else {
        // Display an error message if no item is selected
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une Reclamation");
        alert.showAndWait();

    }
    }
    
public String controleDeSaisie(){
        Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
				")*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(tfemail.getText());
        String erreur="";
        if(tfnom.getText().trim().isEmpty()){
            erreur+="-nom vide\n";
        }
        if(tfprenom.getText().trim().isEmpty()){
            erreur+="-prenom vide\n";
        }
        if(tfemail.getText().trim().isEmpty()){
            erreur+="-email vide\n";
        }
        if(tfdescription.getText().trim().isEmpty()){
            erreur+="-description vide\n";
        }
       
        
        if(!matcher.find()){
            erreur+="-email incorrect\n";
        }
       
        return erreur;    
}

    @FXML
    private void Refresh() {
           data.clear();
        data=FXCollections.observableArrayList(su.afficher());
        //col_id_user.setCellValueFactory(new PropertyValueFactory<>("id_user"));

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        
        tvrec.setItems(data);
        tf_rech.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tvrec.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Rec> subentries = FXCollections.observableArrayList();
            for (Rec entry : tvrec.getItems()) {
                boolean match = true;
                
                String nom = entry.getNom();
                String prenom = entry.getPrenom();                

                if (!nom.toLowerCase().contains(value)&&
                        !prenom.toLowerCase().contains(value))
                         {
                    match = false;
                }
                if (match) {
                    subentries.add(entry);
                }
            }
                tvrec.setItems(subentries);
                 });
    }
    

    @FXML
    private void fillforum(MouseEvent event) {
          u=tvrec.getSelectionModel().getSelectedItem();
//        tfnom.setText(u.getNom());
//        tfprenom.setText(u.getPrenom());
//        tfemail.setText(u.getEmail());
        tfdescription.setText(u.getDescription());
        if ("normale".equals(u.getType()) ){rbnormale.setSelected(true);
        } 
        else rbadmin.setSelected(true);
        
    }

    @FXML
    private void rech(ActionEvent event) {
        String nom=tf_rech.getText();
        data.clear();
        data=FXCollections.observableArrayList(su.afficherByNom(nom));
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        
        tvrec.setItems(data);
    }

    @FXML
    private void statistique(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("stat.fxml"));
Parent root = loader.load();
Scene scene = new Scene(root);
Stage stage = new Stage();
stage.setTitle("Stats");
stage.setScene(scene);
stage.show();
    }

    @FXML
    private void reponse(ActionEvent event) {
        if (tvrec.getSelectionModel().getSelectedItem() != null) {   
     try {
            Rec selectedrec = tvrec.getSelectionModel().getSelectedItem();
                            
            FXMLLoader loader = new FXMLLoader(
            getClass().getResource("InterfaceReponse.fxml"));
            Parent root = (Parent) loader.load();
            InterfaceReponseController contractViewController = loader.getController();
            contractViewController.setSelectedCar(selectedrec);

            
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
     }
     catch (IOException ex) {
         System.out.println("Error loading AddcontratController: " + ex.getMessage());
    ex.printStackTrace();
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

    @FXML
    private void backr(ActionEvent event) {
                FXMain.setScene("Admin");

    }
}
