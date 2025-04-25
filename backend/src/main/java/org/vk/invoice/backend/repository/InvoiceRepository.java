package org.vk.invoice.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vk.invoice.backend.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
