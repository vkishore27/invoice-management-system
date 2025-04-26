package org.vk.invoice.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vk.invoice.backend.entity.Invoice;
import org.vk.invoice.backend.repository.InvoiceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    // Edit API will be added soon
    public Optional<Invoice> updateInvoice(Long id, Invoice updatedInvoice) {
        return invoiceRepository.findById(id).map(existing -> {
            existing.setCustomerName(updatedInvoice.getCustomerName());
            existing.setInvoiceDate(updatedInvoice.getInvoiceDate());
            existing.setTotalAmount(updatedInvoice.getTotalAmount());
            existing.setStatus(updatedInvoice.getStatus());
            return invoiceRepository.save(existing);
        });
    }
}
