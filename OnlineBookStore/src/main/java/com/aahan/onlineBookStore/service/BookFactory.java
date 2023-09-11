package com.aahan.onlineBookStore.service;

import com.aahan.onlineBookStore.pojo.BookPojo;

import java.awt.print.Book;

public interface BookFactory {
    BookPojo createBook(String title, String author, String genre, double price, int availability);
}
