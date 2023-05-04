/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;

/**
 *
 * @author famou
 * @param <T>
 */

public interface Mecanicienservice<T> {
    public void ajouter(T t);
    public void modifier(int id,T t);
    public void supprimer(int id);
    public List<T> afficher();
     public List<T> afficher2(String nom);
    
}