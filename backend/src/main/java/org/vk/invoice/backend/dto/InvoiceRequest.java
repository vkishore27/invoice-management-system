package org.vk.invoice.backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class InvoiceRequest {

    // --- Buyer Details ---
    private String customerName;
    private String customerAddress;
    private String mobile;
    private String gstin;

    // --- Invoice Info ---
    private String invoiceNo;
    private LocalDate invoiceDate;
    private String transport;
    private String lrNo;
    private String baleNo;
    private String destination;

    // --- Bank Details ---
    private String bankName;
    private String accountNo;
    private String ifscCode;

    // --- Amounts ---
    private Double taxableAmount;
    private Double cgstAmount;
    private Double sgstAmount;
    private Double igstAmount;
    private Double totalTax;
    private Double roundOff;
    private Double invoiceTotal;
    private String invoiceTotalInWords;

    private String status; // PAID / DUE

    // --- Item List ---
    private List<InvoiceItemRequest> items;
}
