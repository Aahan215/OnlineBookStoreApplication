
package com.aahan.onlineBookStore.service;

import com.aahan.onlineBookStore.model.BookForm;
import com.aahan.onlineBookStore.pojo.BookPojo;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static com.aahan.onlineBookStore.helper.helper.createBook;
import static com.aahan.onlineBookStore.helper.helper.createBookForm;
import static org.junit.Assert.*;

public class BookServiceTest extends AbstractUnitTest {

	@Autowired
	private BookService service;

	@Test
	public void testAdd() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);
		String expectedTitle="1984";
		String expectedAuthor="sudha murthy";
		String expectedGenre="fiction";
		double expectedPrice=200.02;
		int expectedAvailability=100;
		BookPojo q= service.get(p.getId());
		assertEquals(expectedAuthor,q.getAuthor());
		assertEquals(expectedTitle,q.getTitle());
		assertEquals(expectedGenre,q.getGenre());
		assertEquals(expectedPrice,q.getPrice(),0.001);
		assertEquals(expectedAvailability,q.getAvailability());

	}

	@Test
	public void testAddNullTitle() {
		BookPojo p=createBook("","sudha murthy","fiction",200.02,100);
		try {
			service.add(p);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Title cannot be empty", e.getMessage());
		}
	}

	@Test
	public void testAddNullAuthor() {
		BookPojo p=createBook("1984","","fiction",200.02,100);
		try {
			service.add(p);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Author cannot be empty", e.getMessage());
		}
	}

	@Test
	public void testAddNullGenre() {
		BookPojo p=createBook("1984","sudha murthy","",200.02,100);
		try {
			service.add(p);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Genre cannot be empty", e.getMessage());
		}
	}

	@Test
	public void testAddCombination() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);
		BookPojo q=createBook("1984","sudha murthy","science",100.82,1090);
		try {
			service.add(q);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Title and Author Combination already exists", e.getMessage());
		}
	}

	@Test
	public void testGetNotExist() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);
		int id=176;
		try {
			service.get(id);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Book with given ID does not exist", e.getMessage());
		}
	}

	@Test
	public void testGetAll() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		BookPojo q=createBook("1984","sudha murthy","science",100.82,1090);
		service.add(p);
		service.add(q);
		List<BookPojo>list=service.getAll();
		assertEquals(2,list.size());
	}

	@Test
	public void testDelete() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);
		List<BookPojo>list=service.getAll();
		service.delete(p.getId());
		assertEquals(0,list.size());
	}

	@Test
	public void testDeleteNotPresent() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);
		try {
			service.delete(176);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Book with given id already not present", e.getMessage());
		}
	}

	@Test
	public void testUpdate() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);

		BookForm q=createBookForm("knight","carlos","science",100.82,1090);

		BookPojo r= service.get(p.getId());

		service.update(r.getId(),q);
		String expectedTitle="knight";
		String expectedAuthor="carlos";
		String expectedGenre="fiction";
		double expectedPrice=100.02;
		int expectedAvailability=1090;
		assertEquals(expectedAuthor,r.getAuthor());
		assertEquals(expectedTitle,r.getTitle());
		assertEquals(expectedGenre,r.getGenre());
		assertEquals(expectedPrice,r.getPrice(),0.001);
		assertEquals(expectedAvailability,r.getAvailability());
	}

	@Test
	public void testEmptyTitleUpdate() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);

		BookForm q=createBookForm("","carlos","science",100.82,1090);

		try {
			service.update(p.getId(),q);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Title cannot be empty", e.getMessage());
		}
	}

	@Test
	public void testEmptyGenreUpdate() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);

		BookForm q=createBookForm("knight","carlos","",100.82,1090);

		try {
			service.update(p.getId(),q);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Genre cannot be empty", e.getMessage());
		}
	}

	@Test
	public void testEmptyAuthorUpdate() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);

		BookForm q=createBookForm("knight","","science",100.82,1090);

		try {
			service.update(p.getId(),q);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Author cannot be empty", e.getMessage());
		}
	}

	@Test
	public void testCombinationUpdate() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);

		BookPojo q=createBook("knight","carlos","science",100.82,1090);

		BookForm r=createBookForm("knight","carlos","sports",1340.82,190);

		try {
			service.update(p.getId(),r);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Title and Author Combination already exists", e.getMessage());
		}
	}

	@Test
	public void testGetIncorrectAuthor() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);
		List<BookPojo> q= service.getAuthor("carlos");
		assertEquals(1,q.size());
	}

	@Test
	public void testGetAuthor() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);
		try {
			service.getAuthor("carlos");
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Book with given author does not exist", e.getMessage());
		}
	}

	@Test
	public void testGetGenre() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);
		List<BookPojo> q= service.getGenre(p.getGenre());
		assertEquals(1,q.size());
	}

	@Test
	public void testGetIncorrectGenre() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);
		try {
			service.getGenre("science");
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Book with given genre does not exist", e.getMessage());
		}
	}

	@Test
	public void testGetPrice() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);
		List<BookPojo> q= service.getPrice(10,1000);
		assertEquals(1,q.size());
	}

	@Test
	public void testGetIncorrectPrice() throws ApiException{
		BookPojo p=createBook("1984","sudha murthy","fiction",200.02,100);
		service.add(p);
		try {
			service.getPrice(10,100);
			fail("Expected ApiException was not thrown");
		} catch (ApiException e) {
			TestCase.assertEquals( "Book with given price range does not exist", e.getMessage());
		}
	}



}
