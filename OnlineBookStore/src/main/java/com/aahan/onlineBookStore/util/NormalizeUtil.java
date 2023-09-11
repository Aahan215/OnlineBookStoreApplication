package com.aahan.onlineBookStore.util;

import com.aahan.onlineBookStore.model.BookForm;
import com.aahan.onlineBookStore.model.CartForm;

public class NormalizeUtil {

    public static void normalize(BookForm p) {
        p.setTitle(p.getTitle().toLowerCase().trim());
        p.setAuthor(p.getAuthor().toLowerCase().trim());
        p.setGenre(p.getGenre().toLowerCase().trim());
    }

    public static void normalize(CartForm p) {
        p.setTitle(p.getTitle().toLowerCase().trim());
        p.setAuthor(p.getAuthor().toLowerCase().trim());
        p.setGenre(p.getGenre().toLowerCase().trim());
    }
}
