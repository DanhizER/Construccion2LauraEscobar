package app.adapters.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.adapters.invoice.entity.InvoiceEntity;
import app.adapters.invoice.repository.InvoiceRepository;
import app.domain.models.Invoice;
import app.ports.InvoicePort;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceAdapter implements InvoicePort {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public void saveInvoice(Invoice invoice) {
        invoiceRepository.save(new InvoiceEntity(invoice));
    }

    @Override
    public Invoice findById(Long id) {
        return invoiceRepository.findById(id)
                .map(InvoiceEntity::toDomain)
                .orElse(null);
    }

    @Override
    public List<Invoice> findInvoicesByOwnerId(Long ownerDoc){
        return invoiceRepository.findByOwner_Document(ownerDoc)
                .stream()
                .map(InvoiceEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Invoice> findAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(InvoiceEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInvoice(Long invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }
}