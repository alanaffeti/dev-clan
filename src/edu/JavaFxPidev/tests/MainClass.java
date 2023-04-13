/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.JavaFxPidev.tests;

import edu.JavaFxPidev.entities.Contrat;
import edu.JavaFxPidev.entities.Voiture;
import edu.JavaFxPidev.services.ContratCRUD;
import edu.JavaFxPidev.services.VoitureCRUD;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mouad
 */
public class MainClass {
    public static void main(String[] args) {
         try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDebut = dateFormat.parse("1999-01-01");
        Date dateFin = dateFormat.parse("1999-01-01");
        Date datefabrication = dateFormat.parse("1999-01-01");

        Voiture v = new Voiture("marque","typevoiture", "matricule", "modele", datefabrication, "typecarburant", 120, "etat");

        Contrat c = new Contrat(81,dateDebut, dateFin, (float) 12.5, "aa", "aaa", "aaaa", 1111, "aaaa");
        ContratCRUD pcd = new ContratCRUD();
        VoitureCRUD voiture = new VoitureCRUD();

        pcd.addEntity(c);
        voiture.addEntity(v);
        System.out.println(pcd.display());
    } catch (ParseException ex) {
        System.out.println(ex.getMessage());
    }

    }
}
