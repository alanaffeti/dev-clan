/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.PiJAva.services;
import com.itextpdf.text.Element;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import edu.PiJAva.entities.Contrat;
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

    // Define some styles
    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.BLACK);
    Font subheadingFont = FontFactory.getFont(FontFactory.HELVETICA, 18, BaseColor.GRAY);
    Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLUE);
    Font listItemFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

    // Add a header with your company name
    Paragraph header = new Paragraph("Assuransea", titleFont);
    header.setAlignment(Element.ALIGN_CENTER);
    document.add(header);

    // Add a subheading with the contract information
    Paragraph subheading = new Paragraph("Contrat #" + c.getId(), subheadingFont);
    subheading.setAlignment(Element.ALIGN_CENTER);
    document.add(subheading);

    // Add a table of contents
    Paragraph toc = new Paragraph("Table of Contents", sectionFont);
    toc.setAlignment(Element.ALIGN_CENTER);
    document.add(toc);

    // Add a bullet list of the contract details
    List list = new List(List.UNORDERED);
    list.setListSymbol("\u2022");
    list.setSymbolIndent(12);
    list.setIndentationLeft(36);
    list.add(new ListItem("Nom: " + c.getNomconducteur(), listItemFont));
    list.add(new ListItem("Prénom: " + c.getPrenomconducteur(), listItemFont));
    list.add(new ListItem("CIN: " + c.getCin(), listItemFont));
    list.add(new ListItem("Région: " + c.getType(), listItemFont));
    list.add(new ListItem("ID de la voiture concernée: " + c.getVoiture_id(), listItemFont));
    list.add(new ListItem("Date d'inscription: " + c.getDatedebut(), listItemFont));
    list.add(new ListItem("Date de fin: " + c.getDatefin(), listItemFont));
    document.add(list);

    // Add an image
//    Image logo = Image.getInstance("IMAGES/.png");
//    logo.setAlignment(Element.ALIGN_CENTER);
//    logo.scaleToFit(150, 150);
//    document.add(logo);

    // Add a footer with the date and page number
    Paragraph footer = new Paragraph("Generated on " + new Date(), listItemFont);
    footer.setAlignment(Element.ALIGN_CENTER);
    document.add(footer);
    document.add(new Paragraph("Page " + document.getPageNumber(), listItemFont));

    document.close();

    // Open the PDF file
    Process pro = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filename + ".pdf");
}

}
