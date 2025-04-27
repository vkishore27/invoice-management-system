package org.vk.invoice.backend.service;

//import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;
import org.vk.invoice.backend.entity.Invoice;
import org.vk.invoice.backend.entity.InvoiceItem;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.itextpdf.text.Font.FontFamily.HELVETICA;
import static com.itextpdf.text.FontFactory.HELVETICA_BOLD;

@Service
public class InvoicePdfService {

    public byte[] generateInvoicePdf(Invoice invoice) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // --- Header Section ---
            Paragraph header = new Paragraph("GULJARI TEXTILES", FontFactory.getFont(HELVETICA_BOLD, 20));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            document.add(new Paragraph("Address: Kanpur, Uttar Pradesh\nGSTIN: 09XXXXXXXXXX\nPAN: XXXXXXXX\nMobile: 9876543210\nEmail: example@email.com"));
            document.add(Chunk.NEWLINE);

            // --- Buyer Details ---
            PdfPTable buyerTable = new PdfPTable(2);
            buyerTable.setWidthPercentage(100);
            buyerTable.addCell(getCell("Buyer: " + invoice.getCustomerName(), PdfPCell.ALIGN_LEFT));
            buyerTable.addCell(getCell("Invoice No: " + invoice.getInvoiceNo(), PdfPCell.ALIGN_RIGHT));
            buyerTable.addCell(getCell("Address: " + invoice.getCustomerAddress(), PdfPCell.ALIGN_LEFT));
            buyerTable.addCell(getCell("Date: " + invoice.getInvoiceDate(), PdfPCell.ALIGN_RIGHT));
            buyerTable.addCell(getCell("Mobile: " + invoice.getMobile(), PdfPCell.ALIGN_LEFT));
            buyerTable.addCell(getCell("Destination: " + invoice.getDestination(), PdfPCell.ALIGN_RIGHT));
            document.add(buyerTable);

            document.add(Chunk.NEWLINE);

            // --- Items Table ---
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.addCell("Particulars");
            table.addCell("HSN Code");
            table.addCell("Challan No");
            table.addCell("Unit");
            table.addCell("Rate");
            table.addCell("Qty");
            table.addCell("Amount");

            List<InvoiceItem> items = invoice.getItems();
            for (InvoiceItem item : items) {
                table.addCell(item.getParticulars());
                table.addCell(item.getHsnCode());
                table.addCell(item.getChallanNo());
                table.addCell(item.getUnit());
                table.addCell(String.valueOf(item.getRate()));
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.valueOf(item.getAmount()));
            }

            document.add(table);

            document.add(Chunk.NEWLINE);

            // --- Tax and Amount Section ---
            PdfPTable totalsTable = new PdfPTable(2);
            totalsTable.setWidthPercentage(50);
            totalsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

            totalsTable.addCell("Taxable Amount:");
            totalsTable.addCell("₹ " + invoice.getTaxableAmount());
            totalsTable.addCell("CGST:");
            totalsTable.addCell("₹ " + invoice.getCgstAmount());
            totalsTable.addCell("SGST:");
            totalsTable.addCell("₹ " + invoice.getSgstAmount());
            totalsTable.addCell("IGST:");
            totalsTable.addCell("₹ " + invoice.getIgstAmount());
            totalsTable.addCell("Total Tax:");
            totalsTable.addCell("₹ " + invoice.getTotalTax());
            totalsTable.addCell("Round Off:");
            totalsTable.addCell("₹ " + invoice.getRoundOff());
            totalsTable.addCell("Invoice Total:");
            totalsTable.addCell("₹ " + invoice.getInvoiceTotal());

            document.add(totalsTable);

            document.add(Chunk.NEWLINE);

            // --- Footer Section ---
            document.add(new Paragraph("Total Amount (in words): " + invoice.getInvoiceTotalInWords()));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Bank: " + invoice.getBankName()));
            document.add(new Paragraph("Account No: " + invoice.getAccountNo()));
            document.add(new Paragraph("IFSC Code: " + invoice.getIfscCode()));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Notes: "));
            document.add(new Paragraph("1. Goods once sold will not be taken back."));
            document.add(new Paragraph("2. All disputes subject to Kanpur jurisdiction."));
            document.add(new Paragraph("3. Payment must be made within due date."));

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Receiver's Signature                             For Guljari Textiles", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to generate PDF");
        }

        return outputStream.toByteArray();
    }

    // Utility method to align cells
    private PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }
}