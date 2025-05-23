/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import com.stripe.exception.StripeException;
import edu.PiJAva.entities.Contrat;
import edu.PiJAva.services.ContratCRUD;
import edu.PiJAva.services.Payment;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mouad
 */
public class PaymentController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Label total;
    @FXML
    private Button pay_btn;
    @FXML
    private TextField client_name;
    @FXML
    private TextField YY;
    @FXML
    private TextField cvc;
    @FXML
    private TextField email;
    @FXML
    private TextField num_card;
    @FXML
    private TextField MM;
    @FXML
    private Button back_btn;
//
     private Contrat contrat;
//
      private float prix;
//
//
//    public PaymentController(float prix) {
//        this.prix = prix;
//    }    
//

    /**
     * Initializes the controller class.
     */
//    public void setPrix(float prix) {
//    this.prix = prix;
//}   
      public void setPrice(float prix) {
    // Set the price in your controller's instance variable
    this.prix = prix;
    // Use the price to update the UI elements
    total.setText(String.valueOf(prix));
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
      public void setData(Contrat r) {
        this.contrat = r;
//        TerrainService ts = new TerrainService();
//        Terrain t = ts.diplay(r.getTerrain_id());
        prix = (r.getPrix());
//        int mm = LocalDate.now().getMonthValue();
//        int yy = LocalDate.now().getYear();
//        SpinnerValueFactory<Integer> valueFactory_month = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, mm, 1);// (min,max,startvalue,incrementValue) 
//        SpinnerValueFactory<Integer> valueFactory_year = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999999, yy, 1);// (min,max,startvalue,incrementValue) 
//        SpinnerValueFactory<Integer> valueFactory_cvc = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 1, 1);// (min,max,startvalue,incrementValue) 
//        MM.setValueFactory(valueFactory_month);
//        YY.setValueFactory(valueFactory_year);
//        cvc.setValueFactory(valueFactory_cvc);
 // String total_txt = "Total : " + String.valueOf(total_pay) + " Dt.";
        String total_txt = "Total : " + String.valueOf(prix) + " Dt.";

      // total.setText(total_txt);
String mm = String.valueOf(LocalDate.now().getMonthValue());
String yy = String.valueOf(LocalDate.now().getYear());
TextField MM = new TextField(mm);
TextField YY = new TextField(yy);
TextField cvc = new TextField("1");

// Parse the values to integers
int month = Integer.parseInt(MM.getText());
int year = Integer.parseInt(YY.getText());
int cvcValue = Integer.parseInt(cvc.getText());

    }

    @FXML
    private void payment(ActionEvent event)  throws StripeException{
        if (client_name.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You need to input your Name");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            client_name.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            
        } else if (email.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You need to input your Email");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            email.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
           
        } else if (!isValidEmail(email.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid Email address.");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            email.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else if (num_card.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You need to input your Card Number");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            num_card.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else if (!check_cvc(cvc.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The CVC number should contain three digits");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            cvc.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else if (!check_expDate(YY.getText(), MM.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a valid expiration date");
            alert.setTitle("Problem");
            alert.setHeaderText(null);
            alert.showAndWait();
            MM.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            YY.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
           
        } else {
            client_name.setStyle(null);
            email.setStyle(null);
            num_card.setStyle(null);
            cvc.setStyle(null);
            MM.setStyle(null);
            YY.setStyle(null);
            boolean isValid = check_card_num(num_card.getText());
            if (!isValid) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a valid Card number");
                alert.setTitle("Problem");
                alert.setHeaderText(null);
                alert.showAndWait();
                num_card.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            } else {
                num_card.setStyle(null);
                String name = client_name.getText();
                String email_txt = email.getText();
                String num = num_card.getText();
               int yy = Integer.parseInt(YY.getText());
int mm = Integer.parseInt(MM.getText());
                String cvc_num = String.valueOf(cvc.getText());
                boolean payment_result = Payment.processPayment(name, email_txt, prix, num, mm, yy, cvc_num);
                if (payment_result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Successful Payment.");
                    System.out.println("sar ");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                    
                    ContratCRUD rs = new ContratCRUD();
//                    rs.update(this.contrat.getId(),this.contrat);
                    redirect_to_successPage();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Payment Failed.");
                    alert.setTitle("Problem");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                   //redirect_to_FailPage();
                }
            }
        }

    }
    
    private void redirect_to_successPage() {
      try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Success_page.fxml"));
            Parent root = loader.load();
            //UPDATE The Controller with Data :
            //Success_pageController controller = loader.getController();
            //controller.setData(this.reservation);
            Scene scene = new Scene(root);
            Stage stage = (Stage) pay_btn.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void redirectToListReservation(ActionEvent event) {
        
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("addvoiture.fxml"));
            Parent root = (Parent) loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
           // refreshList();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public boolean isValidEmail(String email) {
        // Trim the input string to remove any leading or trailing whitespace
        email = email.trim();
        // Regular expression pattern to match an email address
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);

        // Match the pattern against the email address
        Matcher matcher = pattern.matcher(email);

        // Return true if the pattern matches, false otherwise
        return matcher.matches();
    }
    
    public static boolean check_cvc(String cvc) {
    if (cvc == null || cvc.length() != 3) {
        return false;
    }
    try {
        int cvcValue = Integer.parseInt(cvc);
        return cvcValue >= 1 && cvcValue <= 999;
    } catch (NumberFormatException e) {
        return false;
    }
}
    
//    private boolean check_cvc(int value) {
//        String cvc_txt = String.valueOf(value);
//        boolean valid = false;
//        if (cvc_txt.length() == 3) {
//            valid = true;
//        }
//        return valid;
//    }
    
    
    private boolean check_card_num(String cardNumber) {
        // Trim the input string to remove any leading or trailing whitespace
        cardNumber = cardNumber.trim();
        // Step 1: Check length
        int length = cardNumber.length();
        if (length < 13 || length > 19) {
            return false;
        }
        String regex = "^(?:(?:4[0-9]{12}(?:[0-9]{3})?)|(?:5[1-5][0-9]{14})|(?:6(?:011|5[0-9][0-9])[0-9]{12})|(?:3[47][0-9]{13})|(?:3(?:0[0-5]|[68][0-9])[0-9]{11})|(?:((?:2131|1800|35[0-9]{3})[0-9]{11})))$";
        // Create a Pattern object with the regular expression
        Pattern pattern = Pattern.compile(regex);

        // Match the pattern against the credit card number
        Matcher matcher = pattern.matcher(cardNumber);

        // Return true if the pattern matches, false otherwise
        return matcher.matches();
    }
    private boolean check_expDate(String yyStr, String mmStr) {
    boolean valid = false;
    LocalDate date = LocalDate.now();
    try {
        int yy = Integer.parseInt(yyStr);
        int mm = Integer.parseInt(mmStr);
        if ((yy >= date.getYear()) && (mm >= date.getMonthValue())) {
            valid = true;
        }
    } catch (NumberFormatException e) {
        valid = false;
    }
    return valid;
}

    
//    private boolean check_expDate(int value_y, int value_mm) {
//        boolean valid = false;
//        LocalDate date = LocalDate.now();
//        if ((value_y >= date.getYear()) && (value_mm >= date.getMonthValue())) {
//            valid = true;
//        }
//        return valid;
//    }

    

    
}
