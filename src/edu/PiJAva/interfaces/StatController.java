/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.interfaces;

import edu.PiJAva.services.PersonneCRUD;
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



ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
      new PieChart.Data( rs.nbadmin()+ "  reclamation(s) administrative(s)", admin),
        
        new PieChart.Data( rs.nbnormal()+ "  reclamation(s) normale(s)",normal)
);
 
statistique.setAnimated(true);
statistique.setData(list);
    }
    }    
    

