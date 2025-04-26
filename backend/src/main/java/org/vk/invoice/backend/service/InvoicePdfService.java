package org.vk.invoice.backend.service;

//import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.text.Document;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;
import org.vk.invoice.backend.entity.Invoice;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.itextpdf.text.Font.FontFamily.HELVETICA;

@Service
public class InvoicePdfService {

    public byte[] generateInvoicePdf(Invoice invoice) throws Exception {
        // Create a Document instance (this represents the PDF)
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            // Create a PdfWriter instance and link it to the output stream
            PdfWriter.getInstance(document, byteArrayOutputStream);

            // Open the document to start writing
            document.open();

            // Add the invoice title
            document.add(new Paragraph("Invoice", new com.itextpdf.text.Font(HELVETICA, 20)));

            // Add Invoice details to the document
            document.add(new Paragraph("Invoice ID: " + invoice.getId()));
            document.add(new Paragraph("Customer Name: " + invoice.getCustomerName()));
            document.add(new Paragraph("Invoice Date: " + invoice.getInvoiceDate()));
            document.add(new Paragraph("Total Amount: ₹" + invoice.getTotalAmount()));
            document.add(new Paragraph("Status: " + invoice.getStatus()));

            // Add line break
            document.add(Chunk.NEWLINE);

            // Closing the document (this will write to the byte array)
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error generating PDF");
        }

        return byteArrayOutputStream.toByteArray();  // Return PDF as byte array
    }
}
