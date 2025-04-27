package org.vk.invoice.backend.dto;

import lombok.Data;

@Data
public class InvoiceItemRequest {
    private String particulars;
    private String hsnCode;
    private String challanNo;
    private String unit;
    private Double rate;
    private Integer quantity;
    private Double amount;
}
