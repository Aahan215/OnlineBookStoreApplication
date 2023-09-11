package com.aahan.onlineBookStore.helper;

import com.aahan.onlineBookStore.model.BookForm;
import com.aahan.onlineBookStore.model.CartForm;
import com.aahan.onlineBookStore.pojo.BookPojo;
import com.aahan.onlineBookStore.pojo.CartPojo;

public class helper {

    public static BookPojo createBook(String title, String author, String genre, double price, int availability) {
        BookPojo book = new BookPojo();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPrice(price);
        book.setAvailability(availability);
        return book;
    }

    public static BookForm createBookForm(String title, String author, String genre, double price, int availability) {
        BookForm book = new BookForm();
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPrice(price);
        book.setAvailability(availability);
        return book;
    }

    public static CartPojo createCart(String title, String author, String genre, double price, int quantity) {
        CartPojo cart = new CartPojo();
        cart.setTitle(title);
        cart.setAuthor(author);
        cart.setGenre(genre);
        cart.setPrice(price);
        cart.setQuantity(quantity);
        return cart;
    }

    public static CartForm createCartForm(String title, String author, String genre, double price, int quantity) {
        CartForm cart = new CartForm();
        cart.setTitle(title);
        cart.setAuthor(author);
        cart.setGenre(genre);
        cart.setPrice(price);
        cart.setQuantity(quantity);
        return cart;
    }

}
