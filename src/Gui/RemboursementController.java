/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Connexion.Dbconnect;
import Entities.Facture;
import Entities.Remboursement;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author DELL
 */
public class RemboursementController implements Initializable {

    @FXML
    private TableView<Remboursement> listremboursement;
    @FXML
    private TableColumn<Remboursement, String> col_id;
    @FXML
    private TableColumn<Remboursement, String> col_date;
    @FXML
    private TableColumn<Remboursement, String> col_montant;
    @FXML
    private Button Ajout;
    @FXML
    private Button Update;
    @FXML
    private Button delete;
    private ModifremboursementController modifremboursementController ;
    
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Remboursement remboursement = null ;
    
    ObservableList<Remboursement>  RemboursementList = FXCollections.observableArrayList();
    @FXML
    private TextField recherche;
    @FXML
    private Button btnPdf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Actualiser();
        recherche();
        // TODO
    }    

    @FXML
    private void getAddremboursement(ActionEvent event) {
        
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/Gui/Ajoutremboursement.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            
        
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void Actualiser (){
        RemboursementList.clear();
        RemboursementList = FXCollections.observableArrayList(display());
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date_remb"));
        col_montant.setCellValueFactory(new PropertyValueFactory<>("montant_remb"));
        
        listremboursement.setItems(RemboursementList);

    }
    
    public List<Remboursement> display() {
        List<Remboursement> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM remboursement";
            Statement st = Dbconnect.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Remboursement r = new Remboursement();
                r.setId(rs.getInt(1));
                r.setDate_remb(rs.getDate("date_remb"));
                r.setMontant_remb(rs.getDouble("montant_remb"));
             

                myList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @FXML
    private void modifier(ActionEvent event) {
        
        if (listremboursement.getSelectionModel().getSelectedItem() != null) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Modifremboursement.fxml"));
                ModifremboursementController controller = new ModifremboursementController(listremboursement.getSelectionModel().getSelectedItem());
                fxmlLoader.setController(controller);

                modifremboursementController= fxmlLoader.getController();
                modifremboursementController.setmodifController(this);

                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();

            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun remboursement sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un remboursement dans la table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void Supprimer(ActionEvent event) {
        
        if (listremboursement.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce remboursement ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            int id = listremboursement.getSelectionModel().getSelectedItem().getId();
            delete(id);
            Actualiser();
        }
            
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun remboursement sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un remboursement dans la table.");
            alert.showAndWait();
        }
    }

    private void delete(int id) {
        try {
            String query = "DELETE FROM `remboursement` WHERE id=" + id;
            Statement st = Dbconnect.getInstance().getCnx()
                    .createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
        }
    }
    
    public void recherche(){
        //remplire lobservablelist
        RemboursementList.clear();
        RemboursementList.addAll(display());
        //liste filtrer
        FilteredList<Remboursement> filtreddata=new FilteredList<>(RemboursementList,u->true);
        //creation du listener a partir du textfield
        recherche.textProperty().addListener((observable,oldValue,newValue)->{
            filtreddata.setPredicate(remboursement->{
                if(newValue==null||newValue.isEmpty()){
                    return true;
                }
                if(String.valueOf(remboursement.getDate_remb()).indexOf(newValue)!=-1){
                    return true;
                }
               
                else if(String.valueOf(remboursement.getId()).indexOf(newValue)!=-1){
                    return true;
                }
                 else if(String.valueOf(remboursement.getMontant_remb()).indexOf(newValue)!=-1){
                    return true;
                }
                 
              
                else{
                    return false;
                }
            });
            listremboursement.setItems(filtreddata);
        });
    
}

    @FXML
    private void generatePDF(ActionEvent event) {
        
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\DELL\\OneDrive\\Bureau\\remboursement.pdf"));
            document.open();
            Paragraph title = new Paragraph("Liste Des Remboursements");
            title.setAlignment(Element.ALIGN_CENTER);
            title.setFont(FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 36, BaseColor.BLUE));
            document.add(title);
            
            document.add(new Paragraph("\n\n\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));


            PdfPTable pdfTable = new PdfPTable(listremboursement.getColumns().size());

            //Ajouter les en-têtes de colonne au tableau PDF
            for (TableColumn column : listremboursement.getColumns()) {
                PdfPCell cell = new PdfPCell(new Phrase(column.getText()));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(cell);
            }

            // Ajouter les lignes de données au tableau PDF
            for (int i = 0; i < listremboursement.getItems().size(); i++) {
                for (int j = 0; j < listremboursement.getColumns().size(); j++) {
                    String cellValue = listremboursement.getColumns().get(j).getCellData(i).toString();
                    pdfTable.addCell(cellValue);
                }
            }

            // Ajouter le tableau PDF au document
            document.add(pdfTable);

            // Fermer le document
            document.close();

             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Le PDF a été généré avec succès !");
        alert.setContentText("Voulez-vous ouvrir le fichier maintenant ?");

        ButtonType buttonTypeYes = new ButtonType("Oui");
        ButtonType buttonTypeNo = new ButtonType("Non");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            // Ouvrir le fichier PDF avec le programme par défaut du système
            File file = new File("C:\\Users\\DELL\\OneDrive\\Bureau\\remboursement.pdf");
            Desktop.getDesktop().open(file);
        } else {
            // Ne rien faire
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
        
    }
}