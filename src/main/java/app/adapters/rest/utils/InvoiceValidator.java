package app.adapters.rest.utils;

import app.domain.models.Invoice;

public class InvoiceValidator {

    public static void validate(Invoice invoice) throws Exception {
        if (invoice == null) throw new Exception("La factura no puede ser nula");
        invoiceIdValidator(invoice.getInvoiceId());
        orderIdValidator(invoice.getOrderId());
        ownerDocumentValidator(invoice.getOwnersId());
        totalValidator(invoice.getValue());
        productNameValidator(invoice.getProductName());
        productQuantityValidator(invoice.getQuantity());
    }

    public static Long invoiceIdValidator(Long value) throws Exception {
        if (value == null || value <= 0) throw new Exception("ID de factura inválido");
        return value;
    }

    public static Long orderIdValidator(Long value) throws Exception {
        if (value == null || value <= 0) throw new Exception("ID de orden inválido");
        return value;
    }

    public static Long ownerDocumentValidator(Long value) throws Exception {
        if (value == null || value <= 0) throw new Exception("Documento del dueño inválido");
        return value;
    }

    public static Double totalValidator(Double value) throws Exception {
        if (value == null || value < 0) throw new Exception("Total de factura inválido");
        return value;
    }

    public static String productNameValidator(String value) throws Exception {
        if (value == null || value.isBlank()) throw new Exception("Nombre del producto inválido");
        return value;
    }

    public static Integer productQuantityValidator(Integer value) throws Exception {
        if (value == null || value < 0) throw new Exception("Cantidad de producto inválida");
        return value;
    }

}
