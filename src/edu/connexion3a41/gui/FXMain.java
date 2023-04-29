/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class FXMain extends Application {
    public static FXMain _self;
    public static Stage _stage;
          private String[] badWords = {"badword1", "badword2", "badword3"};

    @Override
    public void start(Stage primaryStage) throws Exception {
            TextField textField = new TextField();
        Label label = new Label();

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String filteredText = newValue;
            for (String badWord : badWords) {
                filteredText = filteredText.replaceAll("(?i)" + badWord, "");
            }
            textField.setText(filteredText);
        });

        VBox root = new VBox(10, textField, label);
        root.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    
        _stage = primaryStage;
         _self = this;
         setScene("InterfaceReclamation"); 
        // setScene("InterfaceReponse");
        // setScene("Stat"); 

         
        
    }
    
     public static void setScene(String sceneName)
    {
        try {
            
    
            Parent root = FXMLLoader.load(_self.getClass().getResource(sceneName + ".fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("InterfaceAdmin.fxml"));
            
            Scene scene = new Scene(root);
           
            _stage.setTitle("assurensea");
            
             // Set the stage icon
            
            _stage.setScene(scene);
            _stage.show();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}