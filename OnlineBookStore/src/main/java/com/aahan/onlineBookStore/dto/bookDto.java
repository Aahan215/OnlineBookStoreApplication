package com.aahan.onlineBookStore.dto;

import com.aahan.onlineBookStore.model.BookData;
import com.aahan.onlineBookStore.model.BookForm;
import com.aahan.onlineBookStore.pojo.BookPojo;
import com.aahan.onlineBookStore.service.ApiException;
import com.aahan.onlineBookStore.service.BookFactory;
import com.aahan.onlineBookStore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.aahan.onlineBookStore.util.NormalizeUtil.normalize;


@Component
public class bookDto {

    @Autowired
    private BookService service;

    @Autowired
    private BookFactory bookFactory;

    public void add(BookForm form) throws ApiException {
        normalize(form);
        BookPojo newBook = bookFactory.createBook(form.getTitle(), form.getAuthor(), form.getGenre(), form.getPrice(), form.getAvailability());
        service.add(newBook);
    }

    public BookData get(int id) throws ApiException{
        BookPojo book= service.get(id);
        return convert(book);
    }

    public List<BookData> getBookByAuthor(String author) throws ApiException{
        List<BookPojo>pojoList=service.getAuthor(author);
        List<BookData>list=new ArrayList<>();
        for (BookPojo p : pojoList) {
            list.add(convert(p));
        }
        return list;
    }

    public List<BookData> getBookByGenre(String genre) throws ApiException{
        List<BookPojo>pojoList=service.getGenre(genre);
        List<BookData>list=new ArrayList<>();
        for (BookPojo p : pojoList) {
            list.add(convert(p));
        }
        return list;
    }

    public List<BookData> getBookByPrice(double minPrice,double maxPrice) throws ApiException{
        List<BookPojo>pojoList=service.getPrice(minPrice,maxPrice);
        List<BookData>list=new ArrayList<>();
        for (BookPojo p : pojoList) {
            list.add(convert(p));
        }
        return list;
    }

    public List<BookData> getAll(){
        List<BookPojo>pojoList=service.getAll();
        List<BookData>list=new ArrayList<>();
        for (BookPojo p : pojoList) {
            list.add(convert(p));
        }
        return list;
    }

    public void update(int id, BookForm form) throws ApiException {
        normalize(form);
        service.update(id,form);
    }

    public void delete(int id) throws ApiException {
        service.delete(id);
    }

    public BookData convert(BookPojo book){
        BookData d=new BookData();
        d.setId(book.getId());
        d.setTitle(book.getTitle());
        d.setAuthor(book.getAuthor());
        d.setGenre(book.getGenre());
        d.setPrice(book.getPrice());
        d.setAvailability(book.getAvailability());
        return d;
    }

}

