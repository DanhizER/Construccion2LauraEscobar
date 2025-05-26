package app.adapters.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.adapters.invoice.entity.InvoiceEntity;
import app.adapters.invoice.repository.InvoiceRepository;
import app.adapters.order.entity.OrderEntity;
import app.adapters.order.repository.OrderRepository;
import app.adapters.owner.entity.OwnerEntity;
import app.adapters.owner.repository.OwnerRepository;
import app.adapters.pet.entity.PetEntity;
import app.adapters.pet.repository.PetRepository;
import app.domain.models.Invoice;
import app.ports.InvoicePort;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceAdapter implements InvoicePort {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void saveInvoice(Invoice invoice) {
        PetEntity petEntity = petRepository.findById(invoice.getPetId()).orElse(null);
        OwnerEntity ownerEntity = ownerRepository.findByDocument(invoice.getOwnersId());
        OrderEntity orderEntity = null;
        if (invoice.getOrderId() != null) {
            orderEntity = orderRepository.findById(invoice.getOrderId()).orElse(null);
        }

        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setPet(petEntity);
        invoiceEntity.setOwner(ownerEntity);
        invoiceEntity.setOrder(orderEntity);
        invoiceEntity.setProductName(invoice.getProductName());
        invoiceEntity.setValue(invoice.getValue());
        invoiceEntity.setQuantity(invoice.getQuantity());

        invoiceRepository.save(invoiceEntity);
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