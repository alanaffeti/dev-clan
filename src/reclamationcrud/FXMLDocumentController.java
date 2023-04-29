/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reclamationcrud;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfdescription;
    @FXML
    private Label tftype;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<?> table_rec;
    @FXML
    private TableColumn<?, ?> col_id;
    @FXML
    private TableColumn<?, ?> col_nom;
    @FXML
    private TableColumn<?, ?> col_prenom;
    @FXML
    private TableColumn<?, ?> col_email;
    @FXML
    private TableColumn<?, ?> col_description;
    @FXML
    private TableColumn<?, ?> col_type;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Ajouter(ActionEvent event) {
    }

    @FXML
    private void Delete(ActionEvent event) {
    }

    @FXML
    private void Update(ActionEvent event) {
    }
    
}
