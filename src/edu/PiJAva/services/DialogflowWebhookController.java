/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package edu.JavaFxPidev.services;
//import com.google.cloud.dialogflow.v2.SessionsClient;
//import com.google.cloud.dialogflow.v2.SessionsSettings;
//import edu.JavaFxPidev.entities.Voiture;
//import java.io.FileInputStream;
//import java.util.List;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import com.google.auth.oauth2.FixedCredentialsProvider;
//
//
///**
// *
// * @author Mouad
// */
//@RestController
//
//public class DialogflowWebhookController {
//    
//    String projectId = "xrobot-ffyh";
//SessionsSettings settings = SessionsSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(ServiceAccountCredentials.fromStream(new FileInputStream("C:/Users/Mouad/Downloads/xxxx-385118-19c981752423.json")))).build();
//SessionsClient sessionsClient = SessionsClient.create(settings);
//    
//    
//     @PostMapping("/webhook")
//    public ResponseEntity<String> handleWebhook(@RequestBody String requestBody) {
//     // Retrieve all car information from the database
//    VoitureCRUD voitureCRUD = new VoitureCRUD();
//    List<Voiture> cars = voitureCRUD.display();
//    
//    // Format the car information
//    StringBuilder responseBuilder = new StringBuilder();
//    responseBuilder.append("Here are all the cars in our database: \n");
//    for (Voiture car : cars) {
//        responseBuilder.append("- ").append(car.getMarque()).append(" ").append(car.getModele()).append(" (").append(car.getMatricule()).append(")\n");
//    }
//    String response = responseBuilder.toString();
//
//    // Return the response to Dialogflow
//    return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//    
//}
