/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.services;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import jdk.internal.util.xml.impl.Input;
import jdk.nashorn.internal.ir.BreakNode;

import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.control.textfield.AutoCompletionBinding;

/**
 *
 * @author Mouad
 */
public class AutocompletionlTextField {
    private File file;
    
    
    Set<String> PossibleWords = new HashSet<>();
    private AutoCompletionBinding<String> AutocompletionBinding;
    
     public AutocompletionlTextField() {
        file = new File("possible_words.txt");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                PossibleWords.add(line.trim());
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    public void setupAutoCompletion(TextField textField ){
    AutocompletionBinding = TextFields.bindAutoCompletion(textField, PossibleWords);


    
 }
    
    
    public void LearnWord(TextField textField,String text)
    {
            System.out.println("LearnWord called with " + textField.getText() + " and " + text);

        PossibleWords.add(text);
         try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(text);
            bufferedWriter.newLine();

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (AutocompletionBinding == null)
        {
            AutocompletionBinding.dispose();
        }
        AutocompletionBinding = TextFields.bindAutoCompletion(textField, PossibleWords);
            
            
    }
    
}
