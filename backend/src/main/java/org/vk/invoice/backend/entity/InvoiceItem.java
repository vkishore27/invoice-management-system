package org.vk.invoice.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String particulars;  // Item name or description
    private String hsnCode;
    private String challanNo;
    private String unit;
    private Double rate;
    private Integer quantity;
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
