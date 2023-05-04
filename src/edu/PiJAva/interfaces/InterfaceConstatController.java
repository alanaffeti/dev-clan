/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.tool.xml.XMLWorkerHelper;
import edu.PiJAva.entities.Constat;
import edu.PiJAva.entities.User;
import edu.PiJAva.services.ConstatCRUD;
import edu.PiJAva.services.ServiceUser;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
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

/**
 * FXML Controller class
 *
 * @author chaab
 */
public class InterfaceConstatController implements Initializable {

    User us = new User();
    ServiceUser su = new ServiceUser();
    @FXML
    private DatePicker dpdate;
    @FXML
    private TextField tflieu;
    @FXML
    private TextField tfmatricule;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfnom;
    @FXML
    private TableView<Constat> tvuser;
    @FXML
    private TableColumn<Constat, String> cnom;
    @FXML
    private TableColumn<Constat, String> cprenom;
    @FXML
    private TableColumn<Constat, String> clieu;
    @FXML
    private TableColumn<Constat, String> cmatricule;
    @FXML
    private TableColumn<Constat, Date> cdate;
    @FXML
    private Rectangle imgconsta;
    private String cImageUrl = "";
    private static String pictures = "";
    ConstatCRUD cr = new ConstatCRUD();
    ObservableList<Constat> data = FXCollections.observableArrayList();
    @FXML
    private Button pdf;
    @FXML
    private TextField search;
    private Canvas canvas;
    @FXML
    private Button clear;
    @FXML
    private Button qr;
    @FXML
    private ImageView qrCodeImageView;
    Constat u = new Constat();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        us=su.getUserByEmail(LoginController.iduserglobal);
        // code d'initialisation de la classe
        tfmatricule.setText("000TN0000");
        // ...

        // ajoute un gestionnaire d'événements pour la tableview
        tvuser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Constat constat = tvuser.getSelectionModel().getSelectedItem();
                if (constat != null) {
                    tfnom.setText(constat.getNom());
                    tfprenom.setText(constat.getPrenom());
                    tflieu.setText(constat.getLieu());
                    tfmatricule.setText(constat.getMatricule());
                    cImageUrl = constat.getImage_degats();
                    // définit la date en utilisant la date stockée dans l'objet "Constat"
                    Date date = (Date) constat.getDate();
                    LocalDate localDate = date.toLocalDate();
                    dpdate.setValue(localDate);
                    if (constat.getImage_degats() != null && !constat.getImage_degats().isEmpty()) {
                        Image image = new Image(constat.getImage_degats());
                        imgconsta.setFill(new ImagePattern(image));
                    } else {
                        imgconsta.setFill(Color.TRANSPARENT);
                    }
                }

            }
        });
    }

    @FXML
    private void Dashbord(ActionEvent event) {
    }

    @FXML
    private void DepotConstat(ActionEvent event) {
    }

    @FXML
    private void Facture(ActionEvent event) {
    }

    @FXML
    private void Profile(ActionEvent event) {
    }

    @FXML
    private void Reclamation(ActionEvent event) {
    }

    @FXML
    private void ListUser(ActionEvent event) {
    }

    @FXML
    private void supprimer(ActionEvent event) {
        if (tvuser.getSelectionModel().getSelectedItem() != null) {
            int id = tvuser.getSelectionModel().getSelectedItem().getId();

            // Create a confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cet constat?");
            Optional<ButtonType> result = alert.showAndWait();

            // If the user confirms the deletion, call the supprimer() method and refresh the list
            if (result.isPresent() && result.get() == ButtonType.OK) {
                cr.supprimerEntity(id);
                resfreshuser();
            }
        } else {
            // Display an error message if no item is selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un Constat");
            alert.showAndWait();
        }
    }

    @FXML

    private void modifier(ActionEvent event) {
        Constat constat = tvuser.getSelectionModel().getSelectedItem();
        if (constat != null) {
            int id = constat.getId();
            Constat u = new Constat();
            u.setNom(tfnom.getText());
            u.setPrenom(tfprenom.getText());
            u.setLieu(tflieu.getText());
            u.setMatricule(tfmatricule.getText());
            u.setImage_degats(cImageUrl);
            // définit la date en utilisant la date stockée dans l'objet "Constat"
            u.setDate(Date.valueOf(dpdate.getValue()));
            u.setImage_degats(cImageUrl); // obtient la date sélectionnée à partir du DatePicker
            cr.modifierEntity(id, u);
            resfreshuser();
        }
    }

    @FXML
    private void ajouter(ActionEvent event) {
        String erreur = controleDeSaisie();
        if (erreur.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        } else {

            u.setNom(tfnom.getText());
            u.setPrenom(tfprenom.getText());
            u.setLieu(tflieu.getText());
            u.setMatricule(tfmatricule.getText());

            u.setDate(Date.valueOf(dpdate.getValue()));
            u.setImage_degats(cImageUrl);
            u.setUser_id(us.getId());
            cr.addEntity(u);
            resfreshuser();
        }

    }

    public String controleDeSaisie() {
        Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n"
                + ")*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);

        String erreur = "";
        if (tfnom.getText().trim().isEmpty()) {
            erreur += "-nom vide\n";
        }
        if (tfprenom.getText().trim().isEmpty()) {
            erreur += "-prenom vide\n";
        }
        if (tflieu.getText().trim().isEmpty()) {
            erreur += "-lieu vide\n";
        }
        if (tfmatricule.getText().trim().isEmpty()) {
            erreur += "-matricule vide\n";
        } else {
            // vérifie le format du matricule avec une expression régulière
            Pattern matriculePattern = Pattern.compile("^\\d{3}TN\\d{4}$");
            Matcher matriculeMatcher = matriculePattern.matcher(tfmatricule.getText().trim());
            if (!matriculeMatcher.matches()) {
                erreur += "-matricule invalide\n";
            }
        }

        if (dpdate.getValue() == null) {
            erreur += "-date vide\n";
        }

        return erreur;
    }

    @FXML
    private void uploadsiguppic(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setInitialDirectory(new File("C:\\Users\\21694\\Desktop\\Java\\PiJavaEmna\\src\\IMAGES"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String TempprofilePicture = file.toURI().toString();
            System.out.println(TempprofilePicture);
            pictures = file.getName();
            System.out.println(pictures);
            Image image;
            image = new Image(TempprofilePicture);

            cImageUrl = TempprofilePicture;
            Image images = new Image(TempprofilePicture);
            ImagePattern pattern = new ImagePattern(image);
            imgconsta.setFill(pattern);
            imgconsta.setStroke(Color.SEAGREEN);
            imgconsta.setEffect(new DropShadow(20, Color.BLACK));
        }
    }

    @FXML
    private void resfreshuser() {
        data.clear();
        data = FXCollections.observableArrayList(cr.display());
        cnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        clieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        cmatricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        cdate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tvuser.setItems(data);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                tvuser.setItems(data);
            }
            String value = newValue.toLowerCase();
            ObservableList<Constat> subentries = FXCollections.observableArrayList();
            for (Constat entry : tvuser.getItems()) {
                boolean match = true;

                String nom = entry.getNom();
                String prenom = entry.getLieu();

                if (!nom.toLowerCase().contains(value)
                        && !prenom.toLowerCase().contains(value)) {
                    match = false;
                }
                if (match) {
                    subentries.add(entry);
                }
            }
            tvuser.setItems(subentries);
        });
    }

    @FXML
    private void recherche(ActionEvent event) {

    }

    @FXML
    void generatePDF(ActionEvent event) {
        // Récupérer l'élément sélectionné dans la table
        Constat selectedconstat = tvuser.getSelectionModel().getSelectedItem();

        if (selectedconstat == null) {
            // Afficher un message d'erreur si aucun élément n'est sélectionné
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un Constat !");
            alert.showAndWait();
            return;
        }

        try {
            // Créer un FileChooser pour permettre à l'utilisateur de sélectionner l'emplacement du fichier PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le PDF");
            fileChooser.setInitialFileName("constat.pdf");
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                // Créer le fichier PDF avec l'emplacement sélectionné
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                // Créer une table PDF à une colonne pour afficher les détails de l'utilisateur sélectionné
                PdfPTable pdfTable = new PdfPTable(1);

                // Ajouter une cellule pour chaque propriété de l'utilisateur sélectionné
                pdfTable.addCell(new PdfPCell(new Phrase("Nom :")));
                pdfTable.addCell(new PdfPCell(new Phrase(selectedconstat.getNom())));

                pdfTable.addCell(new PdfPCell(new Phrase("Prenom :")));
                pdfTable.addCell(new PdfPCell(new Phrase(String.valueOf(selectedconstat.getPrenom()))));

                pdfTable.addCell(new PdfPCell(new Phrase("Matricule :")));
                pdfTable.addCell(new PdfPCell(new Phrase(selectedconstat.getMatricule())));
                pdfTable.addCell(new PdfPCell(new Phrase("lieu :")));
                pdfTable.addCell(new PdfPCell(new Phrase(selectedconstat.getLieu())));
                pdfTable.addCell(new PdfPCell(new Phrase("Date :")));
                pdfTable.addCell(new PdfPCell(new Phrase(selectedconstat.getDate().toString())));
                pdfTable.addCell(new PdfPCell(new Phrase("L'image du dégat  :")));
                pdfTable.addCell(new PdfPCell(new Phrase(selectedconstat.getImage_degats())));

                // Ajouter le tableau PDF au document
                document.add(pdfTable);

                // Fermer le document
                document.close();

                // Ouvrir le fichier PDF avec l'application par défaut
                Desktop.getDesktop().open(file);

                System.out.println("PDF généré avec succès");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clear() {
        tfnom.setText("");
        tfprenom.setText("");
        tfmatricule.setText("");
        tflieu.setText("");
        dpdate.setValue(null);
        imgconsta.setFill(null);

    }

    @FXML
    private void generateQR(ActionEvent event) throws WriterException {

//        Mecaniciencrud me=new Mecaniciencrud();
//        Mecanicien p = new Mecanicien();
        Constat con = tvuser.getSelectionModel().getSelectedItem();

        // Generate QR code with text "Hello, world!"
        String text = String.format("Nom : %s%nPrénom : %s%nMatricule : %s%nAdresse : %s%ndate : %s",
                con.getNom(), con.getPrenom(), con.getMatricule(), con.getLieu(), con.getDate());

        int width = 300;
        int height = 300;
        String format = "png";
        File file = new File("C:\\Users\\21694\\Desktop\\Java\\PiJavaEmna\\src\\IMAGES/", "qrcode.png");
    
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            qrCodeImageView.setImage(image);
       
        
           
    }
}
