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
import edu.PiJAva.entities.Client;
import edu.PiJAva.entities.Mecanicien;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.controlsfx.control.Notifications;
import edu.PiJAva.services.Mecaniciencrud;
import edu.PiJAva.tools.MailAPI;

/**
 * FXML Controller class
 *
 * @author famou
 */
public class AffichermecaController implements Initializable {
    
      ObservableList<Mecanicien> MecanicienList = FXCollections.observableArrayList();
    
    Mecanicien m= new Mecanicien();
    Mecaniciencrud me=new Mecaniciencrud();

    @FXML
    private Button tfajout;
    @FXML
    private Button tfmodif;
    @FXML
    private Button tfdelete;
    @FXML
    private Button tfactual;
    @FXML
    private TableView<Mecanicien> listmeca;
    @FXML
    private TableColumn<Mecanicien, String> col_id;
    @FXML
    private TableColumn<Mecanicien, String> col_nom;
    @FXML
    private TableColumn<Mecanicien, String> col_prenom;
    @FXML
    private TableColumn<Mecanicien, String> col_cin;
    @FXML
    private TableColumn<Mecanicien, String> col_adresse;
    @FXML
    private TableColumn<Mecanicien, String> col_numtel;
    @FXML
    private TableColumn<Mecanicien, String> col_salaire;
    @FXML
    private TableColumn<Mecanicien, String> col_dispo;
    @FXML
    private TableColumn<Mecanicien, String> col_lieu;
    @FXML
    private TableColumn<Mecanicien, String> col_facture;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfnom1;
    @FXML
    private TextField tfnom2;
    @FXML
    private TextField tfnom3;
    @FXML
    private TextField tfnom4;
    @FXML
    private TextField tfnom5;
    @FXML
    private TextField tfnom31;
    @FXML
    private Button btnimport1;
private String cImageUrl = "";
private static String pictures="";    
    @FXML
    private Rectangle img1;
    @FXML
    private TextField tfrecherche;
    @FXML
    private Button tfqr;
/**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        listmeca.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Mecanicien meca = listmeca.getSelectionModel().getSelectedItem();
                if (meca != null) {
                    tfnom.setText(meca.getNom());
                    tfnom1.setText(meca.getPrenom());
                    tfnom2.setText(meca.getCin());
                    tfnom5.setText(meca.getAdresse_mail());
                    tfnom3.setText(meca.getNum_tel());
                      //String.valueOf(tfnom4.getText());
                      tfnom4.setText(String.valueOf(meca.getSalaire()));
//                   // meca.setSalaire(Float.parseFloat(tfnom4.getText()));
                    tfnom31.setText(meca.getLieu_garage());
                    
                    cImageUrl = meca.getFacture_rep();                   
                    

                    
                }
            }
            
            
        });
        
       
        
        
        
        
    }    
    
    
    public String controleDeSaisie(){
        Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
				")*@[A-Z0-9-]+(?:\\.[A-Z0-9-]+)*$", Pattern.CASE_INSENSITIVE);
        
       
        String erreur="";
        if(tfnom.getText().trim().isEmpty()){
            erreur+="-nom vide\n";
        }
        if(tfnom1.getText().trim().isEmpty()){
            erreur+="-prenom vide\n";
        }
        
        if(tfnom2.getText().trim().isEmpty()){
        erreur+="-cin vide\n";
    }
        
        String email = tfnom5.getText().trim();
    if (!email.contains("@") || (!email.endsWith(".com") && !email.endsWith(".fr") && !email.endsWith(".tn"))) {
        erreur += "- Adresse e-mail invalide\n";
    }
    
    if(tfnom2.getText().length()<8 || tfnom2.getText().length()>8 ){
        erreur+="-cin doit etre egale a 8 char\n";
    }
       
       
        
        return erreur;
    }
    
    @FXML
    private void actualiserr(ActionEvent event) {
    
     MecanicienList.clear();
        MecanicienList = FXCollections.observableArrayList(me.afficher());
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        col_numtel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        col_salaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        col_dispo.setCellValueFactory(new PropertyValueFactory<>("disponiblite"));
        col_lieu.setCellValueFactory(new PropertyValueFactory<>("lieu_garage"));
        col_facture.setCellValueFactory(new PropertyValueFactory<>("facture_rep"));
       
        listmeca.setItems(MecanicienList);
        search_meca();
          
          
    
    }
    
    

    @FXML
     private void ajouter(ActionEvent event){
     String erreur=controleDeSaisie();
        if(erreur.length()>0){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("invalide");
            alert.setContentText(erreur);
            alert.showAndWait();
        }
        else{
            Mecanicien u=new Mecanicien();
            u.setNom(tfnom.getText());
            u.setPrenom(tfnom1.getText());
           
            u.setCin(tfnom2.getText());
            u.setAdresse_mail(tfnom5.getText());
            u.setNum_tel(tfnom3.getText());
          
            u.setSalaire(Float.parseFloat(tfnom4.getText()));
            u.setLieu_garage(tfnom31.getText());
           
             u.setFacture_rep(cImageUrl);            
           
            me.ajouter(u);
            
              Notifications notificationBuilder = Notifications.create()
            .title("Ajout Mecanicien")
            .text("Mecanicien a ete bien Ajouté  !")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.showInformation();
            
            
            
            
            actualiserr1();
           // MailAPI.sendMail(.getText(), "Confirmation pour le depot daccident ","merci pour votre depot");
        }
     }
    
    
    
    
    
//    @FXML
//    private void ajouter(ActionEvent event) {
//    
//    try {
//            Parent parent = FXMLLoader.load(getClass().getResource("/interfaces/Mecanicien.fxml"));
//            Scene scene = new Scene(parent);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.initStyle(StageStyle.UTILITY);
//            stage.show();
//            
//        
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
//    
//    
//    
//    }

    @FXML
    private void modif_meca(ActionEvent event) {
        
        
         Mecanicien meca = listmeca.getSelectionModel().getSelectedItem();
        if (meca != null) {
            int id = meca.getId();
            Mecanicien u = new Mecanicien();
            u.setNom(tfnom.getText());
            u.setPrenom(tfnom1.getText());
            u.setCin(tfnom2.getText());
            u.setAdresse_mail(tfnom5.getText());
            u.setNum_tel(tfnom3.getText());
            
            u.setSalaire(Float.parseFloat(tfnom4.getText()));
            u.setLieu_garage(tfnom31.getText());
            
         
            u.setFacture_rep(cImageUrl);            
            

           
           
            me.modifier(id,u);
            
            
             Notifications notificationBuilder = Notifications.create()
            .title("modification Mecanicien")
            .text("Mecanicien a ete bien Modifié  !")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.showInformation();
            
            
            actualiserr1();
        }
        
        
        
    }

    @FXML
    private void supp_meca(ActionEvent event) {
    
    if (listmeca.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce mecanicien ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            int id = listmeca.getSelectionModel().getSelectedItem().getId();
            me.supprimer(id);
            
            Notifications notificationBuilder = Notifications.create()
            .title("Suppression Mecanicien")
            .text("Mecanicien a etait bien supprimer  !")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.showInformation();
            
            
            
            
            actualiserr1();
            
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

    private void actualiserr1() {
    
     MecanicienList.clear();
        MecanicienList = FXCollections.observableArrayList(me.afficher());
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        col_numtel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        col_salaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        col_dispo.setCellValueFactory(new PropertyValueFactory<>("disponiblite"));
        col_lieu.setCellValueFactory(new PropertyValueFactory<>("lieu_garage"));
        col_facture.setCellValueFactory(new PropertyValueFactory<>("facture_rep"));
       
        listmeca.setItems(MecanicienList);
    
    
    }

    @FXML
    private void import1(ActionEvent event) {
        
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
        Mecanicien p = new Mecanicien();
        
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_cin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        col_adresse.setCellValueFactory(new PropertyValueFactory<>("adresse_mail"));
        col_numtel.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
        col_salaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        col_lieu.setCellValueFactory(new PropertyValueFactory<>("lieu_garage"));
        col_facture.setCellValueFactory(new PropertyValueFactory<>("facture_rep"));
        
       
        listmeca.setItems(MecanicienList);
       
        FilteredList<Mecanicien> filteredData = new FilteredList<>(MecanicienList, b -> true);
       
        tfrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Mecanicien art) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (art.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (art.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
                                else if (art.getCin().indexOf(lowerCaseFilter)!=-1){
				     return true;
                                }
                                else if (art.getNum_tel().indexOf(lowerCaseFilter)!=-1){
				     return true;
                                }
                                
                                
                               
                                else if (String.valueOf(art.getSalaire()).indexOf(lowerCaseFilter)!=-1){
				     return true;
                                }
                                else if (String.valueOf(art.getLieu_garage()).indexOf(lowerCaseFilter)!=-1){
				     return true;
                                }
                                if (art.getFacture_rep().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}
                                
                               
				     else  
				    	 return false; // Does not match.
			});
		});
        SortedList<Mecanicien> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(listmeca.comparatorProperty());
        listmeca.setItems(sortedData);
   }
    
    
//     private ImageView QrCode(String project) throws FileNotFoundException {
//
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//       // String myWeb = project;
//        int width = 300;
//        int height = 300;
//        String fileType = "png";
//        
//        BufferedImage bufferedImage = null;
//        try {
//            BitMatrix byteMatrix = qrCodeWriter.encode(myWeb, BarcodeFormat.QR_CODE, width, height);
//            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            bufferedImage.createGraphics();
//            
//            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
//            graphics.setColor(java.awt.Color.WHITE);
//            graphics.fillRect(0, 0, width, height);
//            graphics.setColor(java.awt.Color.BLACK);
//            
//            for (int i = 0; i < height; i++) {
//                for (int j = 0; j < width; j++) {
//                    if (byteMatrix.get(i, j)) {
//                        graphics.fillRect(i, j, 1, 1);
//                    }
//                }
//            }
//            
//            System.out.println("Success...");
//            
//        } catch (WriterException ex) {
//           
//        }
//        
//        ImageView qrView = new ImageView();
//        qrView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
//        qrView.setFitHeight(50);
//        qrView.setFitWidth(50);
//        
//        
//        return qrView;
//    }
//     
//      public void down(String l,String p) throws FileNotFoundException{
//        saveToFile(p,QrCode(l).getImage());
//    }
// 
//    public static void saveToFile(String p,Image image) {
//    
//        File outputFile = new File("C:\\Users\\famou\\OneDrive\\Documents\\NetBeansProjects\\Projectpi\\src\\images "+p+"qr.png");
//    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
//    try {
//      ImageIO.write(bImage, "png", outputFile);
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//  }

    @FXML
    private void QrCode(ActionEvent event) {
        
//        Mecaniciencrud me=new Mecaniciencrud();
//        Mecanicien p = new Mecanicien();
        
        Mecanicien meca = listmeca.getSelectionModel().getSelectedItem();
               
      
          // Generate QR code with text "Hello, world!"
     String text = String.format("Nom : %s%nPrénom : %s%nEmail : %s%nAdresse : %s%nTéléphone : %s", 
                            meca.getNom(), meca.getPrenom(), meca.getAdresse_mail(), meca.getLieu_garage(), meca.getNum_tel());
    
                
     int width = 300;
    int height = 300;
    String format = "png";
    File file = new File("C:\\Users\\21694\\Desktop\\Java\\PiJavaEmna\\src\\IMAGES/","qrcode.png");
    try {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        MatrixToImageWriter.writeToFile(bitMatrix, format, file);
        
         Notifications notificationBuilder = Notifications.create()
            .title("GENERATION QRCODE POUR MECANICIEN")
            .text("QRCODE a ete bien generé  !")
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.darkStyle();
            notificationBuilder.showInformation();
        
    } catch (WriterException | IOException ex) {
        ex.printStackTrace();
    }
    }
    
    
    
}
