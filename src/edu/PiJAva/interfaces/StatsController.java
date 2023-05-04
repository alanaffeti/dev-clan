/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import edu.PiJAva.services.ContratCRUD;
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
 * @author Mouad
 */
public class StatsController implements Initializable {

    @FXML
    private PieChart statistique;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        int total = 0;
        ContratCRUD rs = new ContratCRUD();



int encours = rs.nbtousrisques();
int traite = rs.nbvol();
total = encours + traite;

double prcntFeed = (encours * 100) / total;
double prcntRec = (traite * 100) / total;

ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
      new PieChart.Data("Vol : " +rs.nbtousrisques() + "", encours),
        new PieChart.Data("Touristique : " + rs.nbvol() + "",traite)
);


statistique.setTitle("Types des contrats");
statistique.setAnimated(true);
statistique.setData(list);
    }    
    }    
    

