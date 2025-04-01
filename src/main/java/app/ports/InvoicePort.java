package app.ports;

import java.util.List;

import app.domain.models.Invoice;

public interface InvoicePort {
    void saveInvoice(Invoice invoice);
    List<Invoice> findAllInvoices();
    List<Invoice> findInvoicesByOwnerId(Long OwnerDoc);
    Invoice findById(Long invoiceId);
    void deleteInvoice(Long invoiceId);

}
