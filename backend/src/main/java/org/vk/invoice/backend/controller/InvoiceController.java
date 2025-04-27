package org.vk.invoice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vk.invoice.backend.dto.InvoiceRequest;
import org.vk.invoice.backend.entity.Invoice;
import org.vk.invoice.backend.service.InvoicePdfService;
import org.vk.invoice.backend.service.InvoiceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoicePdfService invoicePdfService;

    // Create new Invoice
    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceRequest request) {
        Invoice createdInvoice = invoiceService.createInvoice(request);
        return ResponseEntity.ok(createdInvoice);
    }

    // Get Invoice by ID
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        Optional<Invoice> invoiceOptional = invoiceService.getInvoiceById(id);
        return invoiceOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
//        return invoiceService.updateInvoice(id, invoice)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody InvoiceRequest request) {
        return invoiceService.updateInvoice(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    // --- New PDF Download API ---
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable Long id) {
        try {
            Optional<Invoice> optionalInvoice = invoiceService.getInvoiceById(id);
            if (optionalInvoice.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Invoice invoice = optionalInvoice.get();
            byte[] pdfBytes = invoicePdfService.generateInvoicePdf(invoice);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-" + invoice.getInvoiceNo() + ".pdf")
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice updatedInvoice) {
//        return invoiceRepository.findById(id)
//                .map(invoice -> {
//                    invoice.setCustomerName(updatedInvoice.getCustomerName());
//                    invoice.setInvoiceDate(updatedInvoice.getInvoiceDate());
//                    invoice.setTotalAmount(updatedInvoice.getTotalAmount());
//                    invoice.setStatus(updatedInvoice.getStatus());
//                    Invoice saved = invoiceRepository.save(invoice);
//                    return ResponseEntity.ok(saved);
//                })
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice updatedInvoice) {
//        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
//
//        if (optionalInvoice.isPresent()) {
//            Invoice existingInvoice = optionalInvoice.get();
//
//            existingInvoice.setCustomerName(updatedInvoice.getCustomerName());
//            existingInvoice.setInvoiceDate(updatedInvoice.getInvoiceDate());
//            existingInvoice.setTotalAmount(updatedInvoice.getTotalAmount());
//            existingInvoice.setStatus(updatedInvoice.getStatus());
//
//            Invoice saved = invoiceRepository.save(existingInvoice);
//            return ResponseEntity.ok(saved);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

}