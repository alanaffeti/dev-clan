/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.services;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import edu.PiJAva.entities.Factures;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

/**
 *
 * @author skander
 */
public class ServicePdfA {

    public void liste_PromotionPDF() throws FileNotFoundException, DocumentException, IOException {

        
        
        
        Facturecrud sd = new Facturecrud();
         
         
        String message = "Voici Votre facture  \n\n";

        
        
        String file_name = "src/PDF/pdf perso1.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file_name));
        document.open();
        Paragraph para = new Paragraph(message);
        document.add(para);
        List<Factures> art = sd.afficher();
        
         

        PdfPTable table2 = new PdfPTable(2);

//        PdfPCell c10= new PdfPCell(new Phrase("total finale"));
//            table2.addCell(c10);
//        
        PdfPCell c8 = new PdfPCell(new Phrase("ID"));

        table2.addCell(c8);

        PdfPCell c9 = new PdfPCell(new Phrase("article"));
        table2.addCell(c9);
        
//         PdfPCell c10= new PdfPCell(new Phrase("total finale"));
//           table2.addCell(c10);
        

        List<Factures> ids = new ArrayList(art);
        double totalFinal = 0;

        for (int i = 0; i < ids.size(); i++) {

            table2.addCell("" + ids.get(i).getId_fact());

            List<Factures> l = new ArrayList();
            PdfPTable table = new PdfPTable(6);

            PdfPCell cl = new PdfPCell(new Phrase("article"));
            table.addCell(cl);
            PdfPCell c2 = new PdfPCell(new Phrase("quantite"));
            table.addCell(c2);
            PdfPCell c3 = new PdfPCell(new Phrase("description"));
            table.addCell(c3);
            PdfPCell c4 = new PdfPCell(new Phrase("prixsanstva"));
            table.addCell(c4);
            ;
            PdfPCell c6 = new PdfPCell(new Phrase("prixavectva"));
            table.addCell(c6);
            PdfPCell c7 = new PdfPCell(new Phrase("total"));
            table.addCell(c7);

            
            
            table.setHeaderRows(1);
           
            
            
            for (int j = 0; j < art.size(); j++) {
                if (art.get(j).getId_fact() == ids.get(i).getId_fact()) {

                    table.addCell("" + art.get(j).getArticle());
                    table.addCell("" + art.get(j).getQuantite());
                    table.addCell("" + art.get(j).getDescription());
                    table.addCell("" + art.get(j).getPrixsanstva());
                    //table.addCell("" + art.get(j).getImg());
                    table.addCell("" + art.get(j).getPrixavectva());
                    table.addCell("" + art.get(j).getTotal());
                    
                    
                      
                    

                    l.add(art.get(j));
                    
                    totalFinal += art.get(j).getTotal();
                }
            }
            table2.addCell(table);

            art.removeAll(l);
        }

        document.add(table2);
        
        String message1 = "Voici Votre total avec tva  \n\n"+totalFinal;
       Paragraph para1 = new Paragraph(message1);
        document.add(para1);
       
       
        
       
        
        
        document.close();

    }
    
    
   

}
