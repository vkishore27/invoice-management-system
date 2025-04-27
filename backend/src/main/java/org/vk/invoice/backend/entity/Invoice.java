package org.vk.invoice.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Buyer Details ---
    private String customerName;
    private String customerAddress;
    private String mobile;
    private String gstin;

    // --- Invoice Basic Info ---
    private String invoiceNo;
    private LocalDate invoiceDate;
    private String transport;
    private String lrNo;        // Lorry Receipt No.
    private String baleNo;
    private String destination;

    // --- Bank Details ---
    private String bankName;
    private String accountNo;
    private String ifscCode;

    // --- Amount Details ---
    private Double taxableAmount;
    private Double cgstAmount;
    private Double sgstAmount;
    private Double igstAmount;
    private Double totalTax;
    private Double roundOff;
    private Double invoiceTotal;
    private String invoiceTotalInWords;

    private String status; // PAID / DUE

    // --- Items (Products/Services) ---

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> items;   // Each Invoice has multiple items
}


//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Invoice {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String customerName;
//    private LocalDate invoiceDate;
//    private Double totalAmount;
//    private String status;
//
//
//}
