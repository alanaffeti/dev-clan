/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.JavaFxPidev.interfaces;

import edu.JavaFxPidev.entities.Contrat;
import java.util.List;

/**
 *
 * @author Mouad
 */
public interface EntityCRUD<T> {
     public void addEntity(T t);
     public List<T> display();
     public void delete(int id);
     public void update(int id,T t);
     public List<T> getContratsByVoitureId(int id);

}
