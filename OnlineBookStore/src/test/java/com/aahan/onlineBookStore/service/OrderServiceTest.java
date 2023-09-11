package com.aahan.onlineBookStore.service;

import com.aahan.onlineBookStore.model.InvoiceForm;
import com.aahan.onlineBookStore.pojo.BookPojo;
import com.aahan.onlineBookStore.pojo.CartPojo;
import com.aahan.onlineBookStore.pojo.OrderItemPojo;
import com.aahan.onlineBookStore.pojo.OutwardOrderPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static com.aahan.onlineBookStore.helper.helper.createBook;
import static com.aahan.onlineBookStore.helper.helper.createCart;
import static org.junit.Assert.*;

public class OrderServiceTest extends AbstractUnitTest {

//	@Value("${invoice.url}")
//	private String invoiceUrl;

	@Autowired
	private OrderService service;

	@Autowired
	private BookService bookService;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderItemService orderItemService;

	@Test
	public void testAdd() throws ApiException {
		OutwardOrderPojo p = new OutwardOrderPojo();
		service.add(p);

		OutwardOrderPojo q = service.get(p.getId());
		assertEquals(p.getDateTime(), q.getDateTime());
		assertEquals(p.getStatus(), q.getStatus());
	}

	@Test
	public void testGetAll() throws ApiException {
		OutwardOrderPojo p = new OutwardOrderPojo();
		service.add(p);

		OutwardOrderPojo q = new OutwardOrderPojo();
		service.add(q);

		List<OutwardOrderPojo> r = service.getAll();
		assertEquals(2, r.size());
	}

	@Test
	public void testUpdateDelivered() throws ApiException {
		OutwardOrderPojo p = new OutwardOrderPojo();
		service.add(p);

		service.update(p.getId(),"DELIVERED");

		assertEquals("DELIVERED",service.get(p.getId()).getStatus().toString());
	}

	@Test
	public void testUpdateShipped() throws ApiException {
		OutwardOrderPojo p = new OutwardOrderPojo();
		service.add(p);

		service.update(p.getId(),"SHIPPED");

		assertEquals("SHIPPED",service.get(p.getId()).getStatus().toString());
	}

	@Test
	public void testGenerateInvoiceForOrder() throws ApiException {
		OutwardOrderPojo outwardOrderPojo = new OutwardOrderPojo();
		service.add(outwardOrderPojo);

		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","fiction",200.02,50);
		cartService.add(cart);

		OrderItemPojo p1=new OrderItemPojo();
		p1.setOrderId(outwardOrderPojo.getId());
		p1.setAuthor(cart.getAuthor());
		p1.setTitle(cart.getTitle());
		p1.setQuantity(cart.getQuantity());
		p1.setGenre(cart.getGenre());
		p1.setPrice(cart.getPrice());
		orderItemService.add(p1.getOrderId());

		InvoiceForm invoiceForm = service.generateInvoiceForOrder(outwardOrderPojo.getId());

		assertNotNull(invoiceForm);
		assertEquals(outwardOrderPojo.getId(), invoiceForm.getOrderId());
		assertNotNull(invoiceForm.getPlacedDate());
		assertEquals(1, invoiceForm.getOrderItemList().size());

	}

	@Test
	public void testGetInvoicePDF() throws ApiException, Exception {
		OutwardOrderPojo outwardOrderPojo = new OutwardOrderPojo();
		service.add(outwardOrderPojo);

		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","fiction",200.02,50);
		cartService.add(cart);

		OrderItemPojo p1=new OrderItemPojo();
		p1.setOrderId(outwardOrderPojo.getId());
		p1.setAuthor(cart.getAuthor());
		p1.setTitle(cart.getTitle());
		p1.setQuantity(cart.getQuantity());
		p1.setGenre(cart.getGenre());
		p1.setPrice(cart.getPrice());
		orderItemService.add(p1.getOrderId());

		ResponseEntity<byte[]> response= service.getInvoicePDF(outwardOrderPojo.getId());

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
	}

}