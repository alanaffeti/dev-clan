/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.JavaFxPidev.services;
import com.itextpdf.text.Element;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import edu.JavaFxPidev.entities.Contrat;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import java.util.Date;


/**
 *
 * @author Mouad
 */
public class PDFGenerator {

    Contrat c = new Contrat();

    public void generatePdf(String filename, Contrat c) throws FileNotFoundException, DocumentException,
            BadElementException, IOException, InterruptedException, SQLException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));
        document.open();

        // Add a header with your company name
        Paragraph header = new Paragraph("Assuransea");
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        // Add a subheading with the contract information
        Paragraph subheading = new Paragraph("Contrat #" + c.getId());
        subheading.setAlignment(Element.ALIGN_CENTER);
        document.add(subheading);

        // Add a table of contents
        Paragraph toc = new Paragraph("Table of Contents");
        toc.setAlignment(Element.ALIGN_CENTER);
        document.add(toc);

        // Add a bullet list of the contract details
        List list = new List(List.UNORDERED);
        list.add(new ListItem("Nom: " + c.getNomconducteur()));
        list.add(new ListItem("Prénom: " + c.getPrenomconducteur()));
        list.add(new ListItem("CIN: " + c.getCin()));
        list.add(new ListItem("Région: " + c.getType()));
        list.add(new ListItem("ID de la voiture concernée: " + c.getVoiture_id()));
        list.add(new ListItem("Date d'inscription: " + c.getDatedebut()));
        list.add(new ListItem("Date de fin: " + c.getDatefin()));
        document.add(list);

        // Add an image
        Image logo = Image.getInstance("‪images/logonoir.png");
        logo.setAlignment(Element.ALIGN_CENTER);
        logo.scaleToFit(150, 150);
        document.add(logo);

        // Add a footer with the date and page number
        Paragraph footer = new Paragraph("Generated on " + new Date());
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
        document.add(new Paragraph("Page " + document.getPageNumber()));

        document.close();

        // Open the PDF file
        Process pro = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filename + ".pdf");
    }
}
