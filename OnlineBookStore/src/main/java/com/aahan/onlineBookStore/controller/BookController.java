package com.aahan.onlineBookStore.controller;

import com.aahan.onlineBookStore.model.BookData;
import com.aahan.onlineBookStore.model.BookForm;
import com.aahan.onlineBookStore.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.aahan.onlineBookStore.dto.bookDto;

import java.awt.print.Book;
import java.util.List;

@Api
@RestController
public class BookController {

    @Autowired
    private bookDto dto;

    @ApiOperation(value = "Add a Book")
    @RequestMapping(path = "/api/book", method = RequestMethod.POST)
    public void add(@RequestBody BookForm form) throws ApiException {
        dto.add(form);
    }

    @ApiOperation(value = "Get a book by ID")
    @RequestMapping(path = "/api/book/{id}", method = RequestMethod.GET)
    public BookData get(@PathVariable Integer id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Get list of books by Author")
    @RequestMapping(path = "/api/book/author/{author}", method = RequestMethod.GET)
    public List<BookData> getBookByAuthor(@PathVariable String author) throws ApiException {
        return dto.getBookByAuthor(author);
    }

    @ApiOperation(value = "Get list of books by Genre")
    @RequestMapping(path = "/api/book/genre/{genre}", method = RequestMethod.GET)
    public List<BookData> getBookByGenre(@PathVariable String genre) throws ApiException {
        return dto.getBookByGenre(genre);
    }

    @ApiOperation(value = "Get list of books by Price Range")
    @RequestMapping(path = "/api/book/price/{minPrice}/{maxPrice}", method = RequestMethod.GET)
    public List<BookData> getBookByPrice(@PathVariable double minPrice,@PathVariable double maxPrice) throws ApiException {
        return dto.getBookByPrice(minPrice, maxPrice);
    }

    @ApiOperation(value = "Get list of all books")
    @RequestMapping(path = "/api/book", method = RequestMethod.GET)
    public List<BookData> getAll(){
        return dto.getAll();
    }

    @ApiOperation(value = "Update a book")
    @RequestMapping(path = "/api/book/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody BookForm form) throws ApiException {
        dto.update(id,form);
    }

    @ApiOperation(value = "Delete a Book")
    @RequestMapping(path = "/api/book/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException{
        dto.delete(id);
    }

}
