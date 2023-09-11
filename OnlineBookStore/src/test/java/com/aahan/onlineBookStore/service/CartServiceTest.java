
package com.aahan.onlineBookStore.service;
import com.aahan.onlineBookStore.model.CartForm;
import com.aahan.onlineBookStore.pojo.BookPojo;
import com.aahan.onlineBookStore.pojo.CartPojo;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.aahan.onlineBookStore.helper.helper.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CartServiceTest extends AbstractUnitTest {

	@Autowired
	private CartService service;

	@Autowired
	private BookService bookService;

	@Test
	public void testAdd() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","fiction",200.02,50);
		service.add(cart);
		String expectedTitle="1984";
		String expectedAuthor="sudha murthy";
		String expectedGenre="fiction";
		double expectedPrice=200.02;
		int expectedQuantity=100;
		CartPojo q= service.get(p.getId());
		assertEquals(expectedAuthor,q.getAuthor());
		assertEquals(expectedTitle,q.getTitle());
		assertEquals(expectedGenre,q.getGenre());
		assertEquals(expectedPrice,q.getPrice(),0.001);
		assertEquals(expectedQuantity,q.getQuantity());
	}

	@Test
	public void testEmptyTitle() throws ApiException {
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,110);
		bookService.add(p);
		CartPojo cart=createCart("","sudha murthy","fiction",200.02,50);
		try {
			service.add(cart);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Title cannot be empty", e.getMessage());
		}
	}

	@Test
	public void testEmptyAuthor() throws ApiException {
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,1000);
		bookService.add(p);
		CartPojo cart=createCart("1984","","fiction",200.02,50);
		try {
			service.add(cart);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Author cannot be empty", e.getMessage());
		}
	}

	@Test
	public void testEmptyGenre() throws ApiException {
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,1000);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","",200.02,50);
		try {
			service.add(cart);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Genre cannot be empty", e.getMessage());
		}
	}

	@Test
	public void testEmptyAvailability() throws ApiException {
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,0);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","fiction",200.02,50);
		try {
			service.add(cart);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Required number of books are not present", e.getMessage());
		}
	}

	@Test
	public void testInsufficientAvailability() throws ApiException {
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,10);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","fiction",200.02,50);
		try {
			service.add(cart);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Required number of books are not present", e.getMessage());
		}
	}

	@Test
	public void testGetNotExist() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","fiction",200.02,150);
		int id=176;
		try {
			service.get(id);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Cart with given ID does not exist", e.getMessage());
		}
	}

	@Test
	public void testGetAll() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		BookPojo q=createBook("1984","sudha murthy","science",100.82,1090);
		bookService.add(p);
		bookService.add(q);
		CartPojo cart1=createCart("1984","sudha murthy","fiction",200.02,10);
		CartPojo cart2=createCart("1984","sudha murthy","science",100.82,100);
		service.add(cart1);
		service.add(cart2);
		List<CartPojo>list=service.getAll();
		assertEquals(2,list.size());
	}

	@Test
	public void testDelete() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","fiction",200.02,50);
		service.add(cart);
		List<CartPojo>list=service.getAll();
		service.delete(p.getId());
		assertEquals(0,list.size());
	}

	@Test
	public void testDeleteAll() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","fiction",200.02,50);
		service.add(cart);
		List<CartPojo>list=service.getAll();
		service.deleteAll();
		assertEquals(0,list.size());
	}

	@Test
	public void testDeleteNotPresent() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","fiction",200.02,50);
		service.add(cart);
		try {
			service.delete(176);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Cart with given id already not present", e.getMessage());
		}
	}

	@Test
	public void testUpdate() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","fiction",200.02,50);
		service.add(cart);
		CartForm cart2=createCartForm("1984","sudha murthy","fiction",200.02,80);

		CartPojo r= service.get(cart.getId());

		service.update(r.getId(),cart2);
		String expectedTitle="knight";
		String expectedAuthor="carlos";
		String expectedGenre="fiction";
		double expectedPrice=100.02;
		int expectedQuantity=80;
		assertEquals(expectedAuthor,r.getAuthor());
		assertEquals(expectedTitle,r.getTitle());
		assertEquals(expectedGenre,r.getGenre());
		assertEquals(expectedPrice,r.getPrice(),0.001);
		assertEquals(expectedQuantity,r.getQuantity());
	}

	@Test
	public void testEmptyTitleUpdate() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","fiction",200.02,50);
		service.add(cart);
		CartForm cart2=createCartForm("","sudha murthy","fiction",200.02,80);


		try {
			service.update(cart.getId(),cart2);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Title cannot be empty", e.getMessage());
		}
	}

	@Test
	public void testEmptyAuthorUpdate() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","fiction",200.02,50);
		service.add(cart);
		CartForm cart2=createCartForm("1984","","fiction",200.02,80);


		try {
			service.update(cart.getId(),cart2);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Author cannot be empty", e.getMessage());
		}
	}

	@Test
	public void testEmptyGenreUpdate() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		bookService.add(p);
		CartPojo cart=createCart("1984","sudha murthy","fiction",200.02,50);
		service.add(cart);
		CartForm cart2=createCartForm("1984","sudha murthy","",200.02,80);


		try {
			service.update(cart.getId(),cart2);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Genre cannot be empty", e.getMessage());
		}
	}

}
