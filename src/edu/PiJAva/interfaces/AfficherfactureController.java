/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import com.itextpdf.text.DocumentException;
import edu.PiJAva.entities.Factures;
import edu.PiJAva.entities.Mecanicien;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import edu.PiJAva.services.Facturecrud;
import edu.PiJAva.services.Mecaniciencrud;
import edu.PiJAva.services.ServicePdfA;

/**
 * FXML Controller class
 *
 * @author famou
 */
public class AfficherfactureController implements Initializable {
    
    
    
      ObservableList<Factures> FactureList = FXCollections.observableArrayList();
    
    Factures f= new Factures();
    Facturecrud fa=new Facturecrud();
    

    @FXML
    private TableView<Factures> factlist;
    @FXML
    private TableColumn<Factures, String> col_id;
    @FXML
    private TableColumn<Factures, String> col_article;
    @FXML
    private TableColumn<Factures, String> col_quantite;
    @FXML
    private TableColumn<Factures, String> col_description;
    @FXML
    private TableColumn<Factures, String> col_prixsanstva;
    @FXML
    private TableColumn<Factures, String> col_prixavectva;
    @FXML
    private TableColumn<Factures, String> col_total;
    @FXML
    private Button tfajout;
    @FXML
    private Button tfrefresh;
    @FXML
    private Button tfdelete;
    @FXML
    private Button tfmodifier;
    @FXML
    private TextField tfarticle;
    @FXML
    private TextField tfprix2;
    @FXML
    private TextField tfprix1;
    @FXML
    private TextField tftotal;
    @FXML
    private TextField tfquantite;
    @FXML
    private TextArea tfdesc;
    @FXML
    private Button tftotal1;
    
    
    
    
    @FXML
    private void calculertotal(ActionEvent event) {
        
          try {
        double prix2 = Double.parseDouble(tfprix2.getText());
        int quantite = Integer.parseInt(tfquantite.getText());
        double total = prix2 * quantite;
        tftotal.setText(Double.toString(total));
    } catch (NumberFormatException e) {
        // handle invalid input
        tftotal1.setText("Invalid input");
    }
        
    }
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        // TODO
    
    factlist.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Factures fact = factlist.getSelectionModel().getSelectedItem();
                if (fact != null) {
                    tfarticle.setText(fact.getArticle());
                    tfdesc.setText(fact.getDescription());
                    
                    tfquantite.setText(String.valueOf(fact.getQuantite()));
                    tfprix1.setText(String.valueOf(fact.getPrixsanstva()));
                    tfprix2.setText(String.valueOf(fact.getPrixavectva()));
                    tftotal.setText(String.valueOf(fact.getTotal()));
                    
//                    Integer.parseInt(tfquantite.getText());
////                   tfquantite.setText(Integer.toString(fact.getQuantite()));
//                   Float.parseFloat(tfprix1.getText());
//                   Float.parseFloat(tfprix2.getText());
//                   Float.parseFloat(tftotal.getText());
                   // tfprix1..valueOf(fact.getPrixsanstva());
//                     tfprix2.setText(Float.toString(fact.getPrixavectva()));
//                    tftotal.setText(Float.toString(fact.getTotal()));
                   
                    
                 
                                   
                    

                    
                }
            }

        
        });
    
    
    
    }    
    
    
    
    
    
  
 public String controleDeSaisie(){
        Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
				")*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);
        
       
        String erreur="";
        if(tfarticle.getText().trim().isEmpty()){
            erreur+="-article vide\n";
        }
        if(tfquantite.getText().trim().isEmpty()){
            erreur+="-quantite vide\n";
        }
        
        if(tfdesc.getText().trim().isEmpty()){
        erreur+="-description vide\n";
    }
           if(tfprix1.getText().trim().isEmpty()){
        erreur+="-prix_sans_tva vide\n";
    }
           if(tfprix2.getText().trim().isEmpty()){
        erreur+="-prix_avec_tva vide\n";
    }
        return erreur;
    }
    
     @FXML
     private void Ajoutfacture(ActionEvent event){
     String erreur=controleDeSaisie();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
        else{
            Factures u=new Factures();
             u.setArticle(tfarticle.getText());
             u.setQuantite(Integer.parseInt(tfquantite.getText()));
           
             u.setDescription(tfdesc.getText());
             u.setPrixsanstva(Float.parseFloat(tfprix1.getText()));
             u.setPrixavectva(Float.parseFloat(tfprix2.getText()));
          
                u.setTotal(Float.parseFloat(tftotal.getText()));           
           
            fa.ajouter(u);
            
            
             Notifications notificationBuilder = Notifications.create()
            .title("Ajout facture")
            .text("Facture a ete bien Ajouté  !")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.showInformation();
            
            
            
            Actualiser();
        }
     }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   

    
    @FXML
    private void Actualiser() {
        
           FactureList.clear();
        FactureList = FXCollections.observableArrayList(fa.afficher());
        col_id.setCellValueFactory(new PropertyValueFactory<>("id_fact"));
        col_article.setCellValueFactory(new PropertyValueFactory<>("article"));
        col_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_prixsanstva.setCellValueFactory(new PropertyValueFactory<>("prixsanstva"));
        col_prixavectva.setCellValueFactory(new PropertyValueFactory<>("prixavectva"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        
      
        factlist.setItems(FactureList);
        
        
        
    }
    
    
    
    
    
    
    private void Actualiser(ActionEvent event) {
        
           FactureList.clear();
        FactureList = FXCollections.observableArrayList(fa.afficher());
        col_id.setCellValueFactory(new PropertyValueFactory<>("id_fact"));
        col_article.setCellValueFactory(new PropertyValueFactory<>("article"));
        col_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_prixsanstva.setCellValueFactory(new PropertyValueFactory<>("prixsanstva"));
        col_prixavectva.setCellValueFactory(new PropertyValueFactory<>("prixavectva"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        
      
        factlist.setItems(FactureList);
        
        
        
    }

    @FXML
    private void supprimer(ActionEvent event) {
    
    if (factlist.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette facture ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            int id = factlist.getSelectionModel().getSelectedItem().getId_fact();
            fa.supprimer(id);
            
             Notifications notificationBuilder = Notifications.create()
            .title("Supprimer Facture")
            .text("Facture a ete bien Supprimer  !")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.showInformation();
            
            
            
            Actualiser();
            
        }
            
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune facture sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une facture dans la table.");
            alert.showAndWait();
        }
    
    
    }

    @FXML
    private void modifier(ActionEvent event) {
    
    Factures fact = factlist.getSelectionModel().getSelectedItem();
        if (fact != null) {
            int id = fact.getId_fact();
            Factures u = new Factures();
            u.setArticle(tfarticle.getText());
            u.setQuantite(Integer.parseInt(tfquantite.getText()));
            u.setDescription(tfdesc.getText());
            u.setPrixsanstva(Float.parseFloat(tfprix1.getText()));
            u.setPrixavectva(Float.parseFloat(tfprix2.getText()));
            
           u.setTotal(Float.parseFloat(tftotal.getText()));            
            

           
           
            fa.modifier(id,u);
            
             Notifications notificationBuilder = Notifications.create()
            .title("Modifier facture")
            .text("Facture a ete bien Modifié  !")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.showInformation();
            
            
            
            
            Actualiser();
        }
    
    
    }

    @FXML
    private void PDF(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de création du PDF");
        alert.setHeaderText("Etes vous sur de vouloir créer un PDF qui contient la liste des pieces a changer ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
             ServicePdfA sp= new ServicePdfA();
        sp.liste_PromotionPDF();
        
        
         Notifications notificationBuilder = Notifications.create()
            .title("GENERATION PDF")
            .text("PDF a ete bien generé  !")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.showInformation();
        
        
        }
        
    }

    
    }
    

