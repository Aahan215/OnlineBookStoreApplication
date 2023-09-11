package com.aahan.onlineBookStore.service;

import com.aahan.onlineBookStore.pojo.BookPojo;
import org.springframework.stereotype.Service;

@Service
public class DefaultBookFactory implements BookFactory {

    @Override
    public BookPojo createBook(String title, String author, String genre, double price, int availability) {
        BookPojo book = new BookPojo();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPrice(price);
        book.setAvailability(availability);
        return book;
    }
}

