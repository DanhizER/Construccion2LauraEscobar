package app.adapters.rest;

import app.adapters.rest.request.RegisterInvoiceRequest;
import app.adapters.rest.utils.InvoiceValidator;
import app.domain.models.Invoice;
import app.domain.models.Order;
import app.domain.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    // Endpoint para registrar una venta (factura)
    @PostMapping("/invoice")
    public ResponseEntity<?> sellProduct(@RequestBody RegisterInvoiceRequest request) throws Exception {
        Invoice invoice = request.toInvoice();
        InvoiceValidator.ownerDocumentValidator(request.getOwnersId());
        if (request.getOrderId() != null) {
            InvoiceValidator.orderIdValidator(request.getOrderId());
        }
        InvoiceValidator.productNameValidator(request.getProductName());
        InvoiceValidator.productQuantityValidator(request.getQuantity());
        InvoiceValidator.totalValidator(request.getValue());
        sellerService.SellProduct(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body("Venta registrada exitosamente");
    }

    // Endpoint para listar todas las ventas (facturas)
    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> listInvoices() {
        List<Invoice> invoices = sellerService.listInvoices();
        return ResponseEntity.ok(invoices);
    }

    // Endpoint para listar ventas por documento de dueño
    @GetMapping("/invoices/owner/{ownerDoc}")
    public ResponseEntity<List<Invoice>> listInvoicesByOwner(@PathVariable Long ownerDoc) {
        List<Invoice> invoices = sellerService.listInvoicesByOwnerDoc(ownerDoc);
        return ResponseEntity.ok(invoices);
    }

    // Endpoint para listar todas las órdenes médicas
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> listOrders() {
        List<Order> orders = sellerService.listOrders();
        return ResponseEntity.ok(orders);
    }
}
