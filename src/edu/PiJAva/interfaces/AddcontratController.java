/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import static com.itextpdf.text.pdf.PdfName.C;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;

import edu.PiJAva.entities.Contrat;
import edu.PiJAva.entities.Voiture;
import edu.PiJAva.services.AutocompletionlTextField;
import edu.PiJAva.services.ContratCRUD;
import edu.PiJAva.services.PDFGenerator;
import edu.PiJAva.services.VoitureCRUD;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static javafx.scene.input.KeyCode.O;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mouad
 */
public class AddcontratController implements Initializable {

    PDFGenerator pdf = new PDFGenerator();

    private FileChooser fileChooser = new FileChooser();

    private AutocompletionlTextField autocompletionlTextField;

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
    @FXML
    private TextField rechercheC_id;
    @FXML
    private Button exportpdf_id;
    @FXML
    private Button stat;
    @FXML
    private Button pay_id;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        autocompletionlTextField = new AutocompletionlTextField();
        autocompletionlTextField.setupAutoCompletion(nom_id);
        autocompletionlTextField.setupAutoCompletion(prenom_id);
        autocompletionlTextField.setupAutoCompletion(cin_id);
        autocompletionlTextField.setupAutoCompletion(region_id);
        autocompletionlTextField.setupAutoCompletion(prix_id);

        contratTypeList();
        refreshList();
        recherche_avance();


    }
//    private int selectedCarId;
//
//public void setSelectedCar(int carId) {
//    selectedCarId = carId;
//}
private int selectedvoiture;
    

    public void setSelectedCar(int voiture) {
        selectedvoiture = voiture;

    }
    public void refreshList() {
        data.clear();
        System.out.println(selectedvoiture);
        data = FXCollections.observableArrayList(contrat.displayV(selectedvoiture));
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
//public void refreshList() {
//    data.clear();
//
//    List<Contrat> contrats = contrat.displayV(selectedCarId);
//    data = FXCollections.observableArrayList(contrats);
//
//    col_cid.setCellValueFactory(new PropertyValueFactory<>("id"));
//    col_vid.setCellValueFactory(new PropertyValueFactory<>("voiture_id"));
//    col_datedebut.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
//    col_datefin.setCellValueFactory(new PropertyValueFactory<>("datefin"));
//    col_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
//    col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
//    col_nom.setCellValueFactory(new PropertyValueFactory<>("nomconducteur"));
//    col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenomconducteur"));
//    col_cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
//    col_region.setCellValueFactory(new PropertyValueFactory<>("region"));
//
//    tablec_id.setItems(data);
//}
//    

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
            v.setVoiture_id(selectedvoiture);
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
            autocompletionlTextField.LearnWord(nom_id, nom_id.getText());
            autocompletionlTextField.LearnWord(prenom_id, prenom_id.getText());

            autocompletionlTextField.LearnWord(nom_id, nom_id.getText());

            autocompletionlTextField.LearnWord(cin_id, cin_id.getText());

            autocompletionlTextField.LearnWord(region_id, region_id.getText());

            autocompletionlTextField.LearnWord(prix_id, prix_id.getText());

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
            if (result.get() == ButtonType.OK) {
                int id = tablec_id.getSelectionModel().getSelectedItem().getId();
                contrat.delete(id);
                refreshList();
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

    public String controleDeSaisie() {
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
        } else if (!prix_id.getText().matches("^[0-9]+(\\.[0-9]+)?$")) {
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

        if (datedebut_id.getValue() != null && datefin_id1.getValue() != null) {
            if (datefin_id1.getValue().isBefore(datedebut_id.getValue())) {
                erreur += "-La date de fin doit être postérieure à la date de début\n";
            }
        }

        return erreur;
    }

    public void recherche_avance() {
        //remplire lobservablelist
        data.clear();
        data.addAll(contrat.display());
        //liste filtrer
        FilteredList<Contrat> filtreddata = new FilteredList<>(data, u -> true);
        //creation du listener a partir du textfield
        rechercheC_id.textProperty().addListener((observable, oldValue, newValue) -> {
            filtreddata.setPredicate(contrat -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                if (String.valueOf(contrat.getId()).indexOf(newValue) != -1) {
                    return true;
                } else if (contrat.getNomconducteur().indexOf(newValue) != -1) {
                    return true;
                } else if (contrat.getPrenomconducteur().indexOf(newValue) != -1) {
                    return true;
                } else if (String.valueOf(contrat.getDatedebut()).indexOf(newValue) != -1) {
                    return true;
                } else if (String.valueOf(contrat.getDatefin()).indexOf(newValue) != -1) {
                    return true;
                } else if (String.valueOf(contrat.getVoiture_id()).indexOf(newValue) != -1) {
                    return true;
                } else if (String.valueOf(contrat.getPrix()).indexOf(newValue) != -1) {
                    return true;
                } else if (String.valueOf(contrat.getType()).indexOf(newValue) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
            tablec_id.setItems(filtreddata);
        });
    }

    @FXML
    private void exportPdf(ActionEvent event) throws FileNotFoundException, DocumentException {

        if (tablec_id.getSelectionModel().getSelectedItem() != null) {
            Contrat c = tablec_id.getSelectionModel().getSelectedItem();

            try {
                try {
                    pdf.generatePdf("Contrat", c);
                } catch (BadElementException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (DocumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (IOException ex) {
                Logger.getLogger(AddcontratController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(AddcontratController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AddcontratController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void stat(ActionEvent event) throws IOException {
         FXMain.setScene("stats");
   
    }

    @FXML
   private void paiment(ActionEvent event) {

 if (tablec_id.getSelectionModel().getSelectedItem() != null) {   
    try {
        Contrat selectedContrat = tablec_id.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("Payment.fxml"));
        Parent root = (Parent) loader.load();
        PaymentController paymentController = loader.getController();
        paymentController.setPrice(selectedContrat.getPrix());

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        refreshList();
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
   
    }

}
   }
}
    // Get the selected contract from the table view
//      if (tablec_id.getSelectionModel().getSelectedItem() != null) {   
//     try {
//            Contrat selectedContrat = tablec_id.getSelectionModel().getSelectedItem();
//
//            FXMLLoader loader = new FXMLLoader(
//            getClass().getResource("payment.fxml"));
//            Parent root = (Parent) loader.load();
//            PaymentController contractViewController = loader.getController();
//            contractViewController.setSelectedContrat(selectedContrat);
//            contractViewController.setPrix(selectedContrat.getPrix()); // Add this line to set the prix
//
//            
//            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            currentStage.setScene(new Scene(root));
//     }
//     catch (IOException ex) {
//         System.out.println("aaaaaaaaaaaaaaaaaa");
//        }
//        
//        
//    }