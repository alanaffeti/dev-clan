/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a41.tests;

import edu.connexion3a41.entities.Rec;
import edu.connexion3a41.services.PersonneCRUD;
import edu.connexion3a41.tools.MyConnection;

/**
 *
 * @author karra
 */
public class MainClass {
    public static void main(String[] args) {
     //   MyConnection mc = new MyConnection();
        PersonneCRUD pcd = new PersonneCRUD();
        Rec r = new Rec ("agefffafa","aaa","al","Suiiii","normale");
       // pcd.addEntity2(r);
       // System.out.println(pcd.afficher());
       //pcd.updateEntity(1, r);
      // pcd.deleteEntity(1);
    }
}
