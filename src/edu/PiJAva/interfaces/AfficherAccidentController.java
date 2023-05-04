/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import edu.PiJAva.entities.Client;
import edu.PiJAva.services.Accidentcrud;
import edu.PiJAva.tools.MailAPI;
import edu.PiJAva.tools.MyConnection;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javax.mail.MessagingException;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author chaab
 */
public class AfficherAccidentController implements Initializable {

    Connection cnx = MyConnection.getInstance().getCnx();
    
    
    @FXML
    private TableView<Client> listaccident;
    @FXML
    private TableColumn<Client, Integer> col_id;
    @FXML
    private TableColumn<Client, String> col_nomclient;
    @FXML
    private TableColumn<Client, String> col_prenomclient;
    @FXML
    private TableColumn<Client, String> col_matricule;
    @FXML
    private Button tfrefresh;
    @FXML
    private Button tfmodifier;
    @FXML
    private Button tfajout;
    @FXML
    private Button tfdelete;
    @FXML
    private Rectangle img2;
    @FXML
    private Rectangle img1;
    @FXML
    private Rectangle img3;
    @FXML
    private Rectangle img5;
    @FXML
    private Rectangle img4;
    @FXML
    private Button btnimport5;
    @FXML
    private Button btnimport4;
    @FXML
    private Button btnimport3;
    @FXML
    private Button btnimport2;
    @FXML
    private Button btnimport1;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfmatricule;
    @FXML
    private TextField tfnom;
 private String cImageUrl = ""; private String cImageUrl1 = "";
 private String cImageUrl2 = "";
 private String cImageUrl3 = "";
 private String cImageUrl4 = "";
    Accidentcrud cr = new Accidentcrud();
ObservableList<Client> data=FXCollections.observableArrayList();
    private static String pictures="";
    @FXML
    private TextField tfrecherche;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         // ajoute un gestionnaire d'événements pour la tableview
        listaccident.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client client = listaccident.getSelectionModel().getSelectedItem();
                if (client != null) {
                    tfnom.setText(client.getNom_client());
                    tfprenom.setText(client.getPrenom_client());
                    tfmatricule.setText(client.getMatricule());                  
                    cImageUrl = client.getImage();                   
                    cImageUrl1 = client.getImage_facture_reparation();
                    cImageUrl2 = client.getImage_nouveau_pieces();
                    cImageUrl3 = client.getImage_piece_endommage();
                    cImageUrl4 = client.getImage_voiture_reparee();

                    
                }
            }
        });
    }    

    @FXML
    private void Actualiser_Liste() {
         data.clear();
        data=FXCollections.observableArrayList(cr.afficher());       
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_nomclient.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
        col_prenomclient.setCellValueFactory(new PropertyValueFactory<>("prenom_client"));
        col_matricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
              
        listaccident.setItems(data);
        search_meca();
    }
public String controleDeSaisie(){
        Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
				")*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);
       
        String erreur="";
        if(tfnom.getText().trim().isEmpty()){
            erreur+="-nom vide\n";
        }
        if(tfprenom.getText().trim().isEmpty()){
            erreur+="-prenom vide\n";
        }
        
        if(tfmatricule.getText().trim().isEmpty()){
        erreur+="-matricule vide\n";
    }
        
        else {
        // vérifie le format du matricule avec une expression régulière
        Pattern matriculePattern = Pattern.compile("^\\d{3}TN\\d{4}$");
        Matcher matriculeMatcher = matriculePattern.matcher(tfmatricule.getText().trim());
        if (!matriculeMatcher.matches()) {
            erreur+="-matricule invalide\n";
        }
    }
        
        
        
        
      
       
       
        
        return erreur;
    }
    @FXML
    private void modifier_acc(ActionEvent event) {
        Client client = listaccident.getSelectionModel().getSelectedItem();
        if (client != null) {
            int id = client.getId();
            Client u = new Client();
            u.setNom_client(tfnom.getText());
            u.setPrenom_client(tfprenom.getText());
            u.setMatricule(tfmatricule.getText());
            
            u.setImage(cImageUrl);            
            u.setImage_facture_reparation(cImageUrl1);
            u.setImage_nouveau_pieces(cImageUrl2);
            u.setImage_piece_endommage(cImageUrl3);
            u.setImage_voiture_reparee(cImageUrl4);

           
           
            cr.modifier(id, u);
            
             Notifications notificationBuilder = Notifications.create()
            .title("Modifier Accident")
            .text("Accident a ete bien modifié  !")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.showInformation();
            
            
            Actualiser_Liste();
        }
    }

    @FXML
    private void Ajoutaccident(ActionEvent event) throws MessagingException {
        String erreur=controleDeSaisie();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
        else{
            Client u=new Client();
            u.setNom_client(tfnom.getText());
            u.setPrenom_client(tfprenom.getText());
           
            u.setMatricule(tfmatricule.getText());
           
             u.setImage(cImageUrl);            
            u.setImage_facture_reparation(cImageUrl1);
            u.setImage_nouveau_pieces(cImageUrl2);
            u.setImage_piece_endommage(cImageUrl3);
            u.setImage_voiture_reparee(cImageUrl4);
            cr.ajouter(u);
             MailAPI.sendMail("naffetiala88@gmail.com","Confirmation pour le depot daccident ","Merci pour votre depot Votre dossier sera traitée le plus tot possible par nos experts   ");
            
              Notifications notificationBuilder = Notifications.create()
            .title("Ajout Accident")
            .text("Accident a ete bien Ajouté //CONSULTER VOTRE BOITE E-MAIL//  !")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.showInformation();
             
             Actualiser_Liste();
        }
    }

    @FXML
    private void supprimer(ActionEvent event) {
        if (listaccident.getSelectionModel().getSelectedItem() != null) {
        int id = listaccident.getSelectionModel().getSelectedItem().getId();
        
        // Create a confirmation dialog box
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer?");
        Optional<ButtonType> result = alert.showAndWait();
        
        // If the user confirms the deletion, call the supprimer() method and refresh the list
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cr.supprimerr(id);
            
             Notifications notificationBuilder = Notifications.create()
            .title("suppression Accident")
            .text("Accident a ete bien Supprimé  !")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.showInformation();
            
            
            Actualiser_Liste();
        }
    } else {
        // Display an error message if no item is selected
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner ");
        alert.showAndWait();
    }
    }

    @FXML
    private void impo5(ActionEvent event) {
                 FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setInitialDirectory(new File("C:\\Users\\21694\\Desktop\\Java\\PiJavaEmna\\src\\IMAGES"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String TempprofilePicture = file.toURI().toString();
            System.out.println(TempprofilePicture);
            pictures= file.getName();
            System.out.println(pictures);
            Image image;
             image = new Image(TempprofilePicture);
            
            cImageUrl4 = TempprofilePicture;
            Image images = new Image(TempprofilePicture);
            ImagePattern pattern = new ImagePattern(image);
            img5.setFill(pattern);
            img5.setStroke(Color.SEAGREEN);
            img5.setEffect(new DropShadow(20, Color.BLACK));
        }
    }

    @FXML
    private void impo4(ActionEvent event) {
                 FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setInitialDirectory(new File("C:\\Users\\21694\\Desktop\\Java\\PiJavaEmna\\src\\IMAGES"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String TempprofilePicture = file.toURI().toString();
            System.out.println(TempprofilePicture);
            pictures= file.getName();
            System.out.println(pictures);
            Image image;
             image = new Image(TempprofilePicture);
            
            cImageUrl3 = TempprofilePicture;
            Image images = new Image(TempprofilePicture);
            ImagePattern pattern = new ImagePattern(image);
            img4.setFill(pattern);
            img4.setStroke(Color.SEAGREEN);
            img4.setEffect(new DropShadow(20, Color.BLACK));
        }
    }

    @FXML
    private void impo3(ActionEvent event) {
                 FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setInitialDirectory(new File("C:\\Users\\21694\\Desktop\\Java\\PiJavaEmna\\src\\IMAGES"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String TempprofilePicture = file.toURI().toString();
            System.out.println(TempprofilePicture);
            pictures= file.getName();
            System.out.println(pictures);
            Image image;
             image = new Image(TempprofilePicture);
            
            cImageUrl2 = TempprofilePicture;
            Image images = new Image(TempprofilePicture);
            ImagePattern pattern = new ImagePattern(image);
            img3.setFill(pattern);
            img3.setStroke(Color.SEAGREEN);
            img3.setEffect(new DropShadow(20, Color.BLACK));
        }
    }

    @FXML
    private void impo2(ActionEvent event) {
             FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setInitialDirectory(new File("C:\\Users\\21694\\Desktop\\Java\\PiJavaEmna\\src\\IMAGES"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String TempprofilePicture = file.toURI().toString();
            System.out.println(TempprofilePicture);
            pictures= file.getName();
            System.out.println(pictures);
            Image image;
             image = new Image(TempprofilePicture);
            
            cImageUrl1 = TempprofilePicture;
            Image images = new Image(TempprofilePicture);
            ImagePattern pattern = new ImagePattern(image);
            img2.setFill(pattern);
            img2.setStroke(Color.SEAGREEN);
            img2.setEffect(new DropShadow(20, Color.BLACK));
        }
    }

    @FXML
    private void impo1(ActionEvent event) {
         FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setInitialDirectory(new File("C:\\Users\\21694\\Desktop\\Java\\PiJavaEmna\\src\\IMAGES"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String TempprofilePicture = file.toURI().toString();
            System.out.println(TempprofilePicture);
            pictures= file.getName();
            System.out.println(pictures);
            Image image;
             image = new Image(TempprofilePicture);
            
            cImageUrl = TempprofilePicture;
            Image images = new Image(TempprofilePicture);
            ImagePattern pattern = new ImagePattern(image);
            img1.setFill(pattern);
            img1.setStroke(Color.SEAGREEN);
            img1.setEffect(new DropShadow(20, Color.BLACK));
        }
    }
    
    
    
    void search_meca() {
        Client p = new Client();
        
        col_nomclient.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
        col_prenomclient.setCellValueFactory(new PropertyValueFactory<>("prenom_client"));
        col_matricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        
        
       
        listaccident.setItems(data);
       
        FilteredList<Client> filteredData = new FilteredList<>(data, b -> true);
       
        tfrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Client art) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (art.getNom_client().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (art.getPrenom_client().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
                                else if (art.getMatricule().indexOf(lowerCaseFilter)!=-1){
				     return true;
                                }
                                
                                
                                
                               
                              
                                
                               
				     else  
				    	 return false; // Does not match.
			});
		});
        SortedList<Client> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(listaccident.comparatorProperty());
        listaccident.setItems(sortedData);
   }

    private void triDesc(ActionEvent event) {
        
        
        ObservableList<Client> list = FXCollections.observableArrayList();
        try {
            
            String requete = "select * from accident ORDER BY `promotion`.`remise` ASC";
           
              PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
              
   
             list.add(new Client(rs.getString(1), rs.getString(2), rs.getString(3))); 
       
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } 
        listaccident.setItems(list);
        // col_id.setCellValueFactory(new PropertyValueFactory<Client,Integer>("id"));
        col_nomclient.setCellValueFactory(new PropertyValueFactory<Client,String>("nom_client"));
        col_prenomclient.setCellValueFactory(new PropertyValueFactory<Client,String>("prenom_client"));
        col_matricule.setCellValueFactory(new PropertyValueFactory<Client,String>("matricule"));
        
        
        
        
        
        
    }
    
     

    private void triAsc(ActionEvent event) {
         ObservableList<Client> list = FXCollections.observableArrayList();
        try {
            
            String requete = "select * from accident ORDER BY `promotion`.`remise` DESC";
           
              PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
              
   
             list.add(new Client(rs.getString(1), rs.getString(2), rs.getString(3))); 
       
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } 
        listaccident.setItems(list);
//        col_id.setCellValueFactory(new PropertyValueFactory<Promotion,Integer>("id"));
        col_nomclient.setCellValueFactory(new PropertyValueFactory<Client,String>("nom_client"));
        col_prenomclient.setCellValueFactory(new PropertyValueFactory<Client,String>("prenom_client"));
        col_matricule.setCellValueFactory(new PropertyValueFactory<Client,String>("matricule"));
       
         
        
        
        
    }
 
    
    
    
    
}
