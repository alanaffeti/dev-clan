/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.gui;

import edu.connexion3a41.services.PersonneCRUD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class StatController implements Initializable {

    @FXML
    private PieChart statistique;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // TODO
        int total = 0;
        PersonneCRUD rs = new PersonneCRUD();



int admin = rs.nbadmin();
int normal = rs.nbnormal();
total = admin + normal;

double prcntFeed = (admin  * 100) / total;
double prcntRec = (normal * 100) / total;

ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
      new PieChart.Data( rs.nbadmin()+ "  reclamation(s) administrative", admin),
        new PieChart.Data( rs.nbnormal()+ "  reclamation(s) normale",normal)
);


statistique.setAnimated(true);
statistique.setData(list);
    }
    }    
    

