package app.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.models.Invoice;
import app.domain.models.Order;
import app.ports.InvoicePort;
import app.ports.OrderPort;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SellerService {
	@Autowired
	private OrderPort orderPort;
	@Autowired
	private InvoicePort invoicePort;

	//Validamos si la orden existe
	public boolean isOrderValid(long orderId) {
		Order order = orderPort.findByOrderId(orderId);
		if(order==null) {
			log.error("Orden medica con ID {} no encontrada", orderId);
			return false;
		}
		if (order.isOrderCancelled()) {
			log.error("Orden medica con ID  {} fue cancelada", orderId);
			return true;
		}
		log.info("Orden medica con ID {} es valida", orderId);
		return true;
	}

	//Registramos la venta
	public void SellProduct(Invoice invoice) {
		Long orderId = invoice.getOrderId();
		if(orderId != null) {
			if(!isOrderValid(orderId)) {
				log.error("No se puede registrar la venta, la orden no existe");
				throw new IllegalArgumentException("No existe una orden medica con este ID");
			}
		}
		invoicePort.saveInvoice(invoice);
		log.info("Venta registrada exitosamente");
	}

	//Consultamos las ventas generadas
	public List<Invoice> listInvoices(){
		List<Invoice> invoices = invoicePort.findAllInvoices();
		if(invoices.isEmpty()) {
			log.error("No se encontraron ventas registradas");
			throw new IllegalArgumentException("No se encontraron ventas registradas");
		}
		log.info("Se encontraron {} ventas registradas", invoices.size());
		return invoices;
	}

	//Consultamos las ventas generadas por un cliente registrado(OwnerPet)
	public List<Invoice> listInvoicesByOwnerDoc(Long OwnerDoc){
		List<Invoice> invoices = invoicePort.findInvoicesByOwnerId(OwnerDoc);
		if(invoices.isEmpty()) {
			log.error("No se encontraron ventas registradas para el cliente con documento {}", OwnerDoc);
			throw new IllegalArgumentException("No se encontraron ventas registradas para el cliente con documento "+OwnerDoc);
		}
		log.info("Se encontraron {} facturas registradas para el cliente con documento {}", invoices.size(), OwnerDoc);
		return invoices;
	}

	//Consultamos todas las ordenes medicas
	public List<Order> listOrders(){
		List<Order> orders = orderPort.findAllOrders();
		if(orders.isEmpty()) {
			log.error("No se encontraron ordenes medicas registradas");
			throw new IllegalArgumentException("No se encontraron ordenes medicas registradas");
		}
		log.info("Se encontraron {} ordenes medicas registradas", orders.size());
		return orders;
	}
}
