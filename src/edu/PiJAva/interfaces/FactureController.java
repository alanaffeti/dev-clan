/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import edu.PiJAva.entities.Facture;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.PiJAva.tools.MyConnection;
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
import javafx.scene.control.Alert.AlertType;
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
public class FactureController implements Initializable {

    @FXML
    private Button ajout;
    
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Facture facture = null ;
    
    ObservableList<Facture>  FactureList = FXCollections.observableArrayList();
    @FXML
    private TableView<Facture> listfacture;
    @FXML
    private TableColumn<Facture,String > col_id;
    @FXML
    private TableColumn<Facture, String> col_quantite;
    @FXML
    private TableColumn<Facture, String> col_nompiece;
    @FXML
    private TableColumn<Facture, String> col_nomclient;
    @FXML
    private TableColumn<Facture, String> col_idclient;
    @FXML
    private TableColumn<Facture, String> col_montantfacture;
    @FXML
    private Button delete;
    @FXML
    private Button modifier;
    @FXML
    private TextField recherche;
    @FXML
    private Button remboursement;
    private ModiffactureController modiffactureController ;
    @FXML
    private Button btnPdf;
    @FXML
    private Button ref;
    @FXML
    private Button backf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Actualiser();
        recherche ();
        // TODO
    }    

    @FXML
    private void getAjoutfacture(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("Ajoutfacture.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            
        
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void Actualiser()
    {
        FactureList.clear();
        FactureList = FXCollections.observableArrayList(display());
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        col_nompiece.setCellValueFactory(new PropertyValueFactory<>("nom_piece"));
        col_nomclient.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
        col_idclient.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        col_montantfacture.setCellValueFactory(new PropertyValueFactory<>("montant_facture"));
        
        listfacture.setItems(FactureList);
    }
    
    public List<Facture> display() {
        List<Facture> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM facture";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Facture f = new Facture();
                f.setId(rs.getInt(1));
                f.setQuantite(rs.getInt("quantite"));
                f.setNom_piece(rs.getString("nom_piece"));
                f.setNom_client(rs.getString("nom_client"));
                f.setId_client(rs.getInt("id_client"));
                f.setMontant_facture(rs.getDouble("montant_facture"));
             

                myList.add(f);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @FXML
    private void supprimer(ActionEvent event) {
        
        if (listfacture.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce contrat ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            int id = listfacture.getSelectionModel().getSelectedItem().getId();
            delete(id);
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
    private void modifier_facture(ActionEvent event) {
        
         if (listfacture.getSelectionModel().getSelectedItem() != null) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Modiffacture.fxml"));
                ModiffactureController controller = new ModiffactureController(listfacture.getSelectionModel().getSelectedItem());
                fxmlLoader.setController(controller);

                modiffactureController= fxmlLoader.getController();
                modiffactureController.setmodifController(this);

                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();

            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune facture sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une facture dans la table.");
            alert.showAndWait();
        }
        
    }

    private void delete(int id) {
        try {
            String query = "DELETE FROM `facture` WHERE id=" + id;
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
        }
    }
    
    public void recherche(){
        //remplire lobservablelist
        FactureList.clear();
        FactureList.addAll(display());
        //liste filtrer
        FilteredList<Facture> filtreddata=new FilteredList<>(FactureList,u->true);
        //creation du listener a partir du textfield
        recherche.textProperty().addListener((observable,oldValue,newValue)->{
            filtreddata.setPredicate(facture->{
                if(newValue==null||newValue.isEmpty()){
                    return true;
                }
                if(facture.getNom_piece().indexOf(newValue)!=-1){
                    return true;
                }
                else if(facture.getNom_client().indexOf(newValue)!=-1){
                    return true;
                }
           
                else if(String.valueOf(facture.getId()).indexOf(newValue)!=-1){
                    return true;
                }
                 else if(String.valueOf(facture.getQuantite()).indexOf(newValue)!=-1){
                    return true;
                }
                 else if(String.valueOf(facture.getId_client()).indexOf(newValue)!=-1){
                    return true;
                }
                 else if(String.valueOf(facture.getMontant_facture()).indexOf(newValue)!=-1){
                    return true;
                }
                else{
                    return false;
                }
            });
            listfacture.setItems(filtreddata);
        });
    }

    @FXML
    private void getRemboursement(ActionEvent event) {
        
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("Remboursement.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            
        
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void generatePDF(ActionEvent event) {
        
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\21694\\Downloads\\facture.pdf"));
            document.open();
            Paragraph title = new Paragraph("Liste Des Factures");
            title.setAlignment(Element.ALIGN_CENTER);
            title.setFont(FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 36, BaseColor.BLUE));
            document.add(title);
            
            document.add(new Paragraph("\n\n\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));


            PdfPTable pdfTable = new PdfPTable(listfacture.getColumns().size());

            //Ajouter les en-têtes de colonne au tableau PDF
            for (TableColumn column : listfacture.getColumns()) {
                PdfPCell cell = new PdfPCell(new Phrase(column.getText()));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(cell);
            }

            // Ajouter les lignes de données au tableau PDF
            for (int i = 0; i < listfacture.getItems().size(); i++) {
                for (int j = 0; j < listfacture.getColumns().size(); j++) {
                    String cellValue = listfacture.getColumns().get(j).getCellData(i).toString();
                    pdfTable.addCell(cellValue);
                }
            }

            // Ajouter le tableau PDF au document
            document.add(pdfTable);

            // Fermer le document
            document.close();
            Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Le PDF a été généré avec succès !");
        alert.setContentText("Voulez-vous ouvrir le fichier maintenant ?");

        ButtonType buttonTypeYes = new ButtonType("Oui");
        ButtonType buttonTypeNo = new ButtonType("Non");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            // Ouvrir le fichier PDF avec le programme par défaut du système
            File file = new File("C:\\Users\\21694\\Downloads\\facture.pdf");
            Desktop.getDesktop().open(file);
        } else {
            // Ne rien faire
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

            
        
        
    }

    @FXML
    private void ref(ActionEvent event) {
        Actualiser();
    }

    @FXML
    private void backf(ActionEvent event) {
                FXMain.setScene("Admin.fxml");

    }
    
    
    
}
