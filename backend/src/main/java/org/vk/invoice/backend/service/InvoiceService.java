package org.vk.invoice.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vk.invoice.backend.dto.InvoiceItemRequest;
import org.vk.invoice.backend.dto.InvoiceRequest;
import org.vk.invoice.backend.entity.Invoice;
import org.vk.invoice.backend.entity.InvoiceItem;
import org.vk.invoice.backend.repository.InvoiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

//    public Invoice createInvoice(Invoice invoice) {
//        return invoiceRepository.save(invoice);
//    }

    // Create Invoice
    public Invoice createInvoice(InvoiceRequest request) {
        // Build Invoice entity
        Invoice invoice = Invoice.builder()
                .customerName(request.getCustomerName())
                .customerAddress(request.getCustomerAddress())
                .mobile(request.getMobile())
                .gstin(request.getGstin())
                .invoiceNo(request.getInvoiceNo())
                .invoiceDate(request.getInvoiceDate())
                .transport(request.getTransport())
                .lrNo(request.getLrNo())
                .baleNo(request.getBaleNo())
                .destination(request.getDestination())
                .bankName(request.getBankName())
                .accountNo(request.getAccountNo())
                .ifscCode(request.getIfscCode())
                .taxableAmount(request.getTaxableAmount())
                .cgstAmount(request.getCgstAmount())
                .sgstAmount(request.getSgstAmount())
                .igstAmount(request.getIgstAmount())
                .totalTax(request.getTotalTax())
                .roundOff(request.getRoundOff())
                .invoiceTotal(request.getInvoiceTotal())
                .invoiceTotalInWords(request.getInvoiceTotalInWords())
                .status(request.getStatus())
                .build();

        // Build Invoice Items
        List<InvoiceItem> itemList = new ArrayList<>();
        if (request.getItems() != null) {
            for (InvoiceItemRequest itemRequest : request.getItems()) {
                InvoiceItem item = InvoiceItem.builder()
                        .particulars(itemRequest.getParticulars())
                        .hsnCode(itemRequest.getHsnCode())
                        .challanNo(itemRequest.getChallanNo())
                        .unit(itemRequest.getUnit())
                        .rate(itemRequest.getRate())
                        .quantity(itemRequest.getQuantity())
                        .amount(itemRequest.getAmount())
                        .invoice(invoice) // Link item to invoice
                        .build();
                itemList.add(item);
            }
        }

        invoice.setItems(itemList);

        return invoiceRepository.save(invoice); // Save Invoice + Items
    }

//    public Invoice getInvoiceById(Long id) {
//        return invoiceRepository.findById(id).orElse(null);
//    }

    // Get Invoice By ID
    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

//    // Edit API will be added soon
//    public Optional<Invoice> updateInvoice(Long id, Invoice updatedInvoice) {
//        return invoiceRepository.findById(id).map(existing -> {
//            existing.setCustomerName(updatedInvoice.getCustomerName());
//            existing.setInvoiceDate(updatedInvoice.getInvoiceDate());
//            existing.setTotalAmount(updatedInvoice.getTotalAmount());
//            existing.setStatus(updatedInvoice.getStatus());
//            return invoiceRepository.save(existing);
//        });
//    }

    // Update Invoice
    public Optional<Invoice> updateInvoice(Long id, InvoiceRequest request) {
        return invoiceRepository.findById(id).map(existing -> {

            // Update main fields
            existing.setCustomerName(request.getCustomerName());
            existing.setCustomerAddress(request.getCustomerAddress());
            existing.setMobile(request.getMobile());
            existing.setGstin(request.getGstin());

            existing.setInvoiceNo(request.getInvoiceNo());
            existing.setInvoiceDate(request.getInvoiceDate());
            existing.setTransport(request.getTransport());
            existing.setLrNo(request.getLrNo());
            existing.setBaleNo(request.getBaleNo());
            existing.setDestination(request.getDestination());

            existing.setBankName(request.getBankName());
            existing.setAccountNo(request.getAccountNo());
            existing.setIfscCode(request.getIfscCode());

            existing.setTaxableAmount(request.getTaxableAmount());
            existing.setCgstAmount(request.getCgstAmount());
            existing.setSgstAmount(request.getSgstAmount());
            existing.setIgstAmount(request.getIgstAmount());
            existing.setTotalTax(request.getTotalTax());
            existing.setRoundOff(request.getRoundOff());
            existing.setInvoiceTotal(request.getInvoiceTotal());
            existing.setInvoiceTotalInWords(request.getInvoiceTotalInWords());

            existing.setStatus(request.getStatus());

            // --- Handle Items ---
            // Clear old items first
            existing.getItems().clear();

            if (request.getItems() != null) {
                List<InvoiceItem> updatedItems = new ArrayList<>();
                for (InvoiceItemRequest itemRequest : request.getItems()) {
                    InvoiceItem item = InvoiceItem.builder()
                            .particulars(itemRequest.getParticulars())
                            .hsnCode(itemRequest.getHsnCode())
                            .challanNo(itemRequest.getChallanNo())
                            .unit(itemRequest.getUnit())
                            .rate(itemRequest.getRate())
                            .quantity(itemRequest.getQuantity())
                            .amount(itemRequest.getAmount())
                            .invoice(existing) // very important!
                            .build();
                    updatedItems.add(item);
                }
                existing.getItems().addAll(updatedItems);
            }

            return invoiceRepository.save(existing);
        });
    }

}
