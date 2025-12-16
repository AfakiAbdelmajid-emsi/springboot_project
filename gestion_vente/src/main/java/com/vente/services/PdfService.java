package com.vente.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.vente.entities.Commande;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generateInvoice(Commande cmd) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document doc = new Document();
        PdfWriter.getInstance(doc, out);

        doc.open();
        doc.add(new Paragraph("FACTURE DE COMMANDE"));
        doc.add(new Paragraph("Code : " + cmd.getCodeCmd()));
        doc.add(new Paragraph("Client : " + cmd.getClient()));
        doc.add(new Paragraph("Produit : " + cmd.getCodePdt()));
        doc.add(new Paragraph("Quantité : " + cmd.getQteCmd()));
        doc.add(new Paragraph("Date : " + cmd.getDateCmd()));
        doc.close();

        return out.toByteArray();
    }
}

