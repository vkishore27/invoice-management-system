package org.vk.invoice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return invoiceService.createInvoice(invoice);
    }

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        return invoiceService.updateInvoice(id, invoice)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/api/invoices/{id}/pdf")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long id) throws Exception {
        // Get invoice by ID
        Invoice invoice = invoiceService.getInvoiceById(id);
        if (invoice == null) {
            return ResponseEntity.notFound().build();
        }

        // Generate PDF for the invoice
        byte[] pdfContent = invoicePdfService.generateInvoicePdf(invoice);

        // Return PDF file as response
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=invoice-" + invoice.getId() + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfContent);
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