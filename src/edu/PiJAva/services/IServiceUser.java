/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.services;

import edu.PiJAva.entities.User;
import java.util.List;

/**
 *
 * @author Skymil
 */
public interface IServiceUser<T> {
    public void ajouter(T t);
    public void modifier(int id,T t);
    public void supprimer(int id);
    public List<T> afficher();
    public User getById(int id);
}
