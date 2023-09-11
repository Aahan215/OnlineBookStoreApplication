package com.aahan.onlineBookStore.service;

import com.aahan.onlineBookStore.pojo.BookPojo;
import com.aahan.onlineBookStore.pojo.CartPojo;
import com.aahan.onlineBookStore.pojo.OrderItemPojo;
import com.aahan.onlineBookStore.pojo.OutwardOrderPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static com.aahan.onlineBookStore.helper.helper.createBook;
import static com.aahan.onlineBookStore.helper.helper.createCart;
import static org.junit.Assert.assertEquals;

public class OrderItemServiceTest extends AbstractUnitTest {

	@Autowired
	private OrderService service;

	@Autowired
	private BookService bookService;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderItemService orderItemService;

	@Test
	public void testAdd() throws ApiException{
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
	}




	@Test
	public void testGetAll() throws ApiException{
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

		List<OrderItemPojo> q= orderItemService.getAll();
		assertEquals(1,q.size());
	}

}
