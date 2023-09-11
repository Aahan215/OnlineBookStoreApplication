package com.aahan.onlineBookStore.dao;


import com.aahan.onlineBookStore.pojo.BookPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BookDao extends AbstractDao {

    private static String DELETE_ID = "delete from BookPojo p where id=:id";
    private static String SELECT_ID = "select p from BookPojo p where id=:id";
    private static String SELECT_AUTHOR = "select p from BookPojo p where author=:author";
    private static String SELECT_GENRE = "select p from BookPojo p where genre=:genre";
    private static String SELECT_PRICE = "select p from BookPojo p where price>=:minPrice AND price<=:maxPrice";
    private static String CHECK_COMBINATION = "select p from BookPojo p where title=:title AND author=:author";
    private static String SELECT_ALL="select p from BookPojo p";

    @Transactional
    public void insert(BookPojo p) {
        em().persist(p);
    }

    public int delete(int id) {
        Query query = em().createQuery(DELETE_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public BookPojo selectById(Integer id) {
        TypedQuery<BookPojo> query = getQuery(SELECT_ID, BookPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<BookPojo> select_author(String author) {
        TypedQuery<BookPojo> query = getQuery(SELECT_AUTHOR, BookPojo.class);
        query.setParameter("author", author);
        return query.getResultList();
    }

    public List<BookPojo> select_genre(String genre) {
        TypedQuery<BookPojo> query = getQuery(SELECT_GENRE, BookPojo.class);
        query.setParameter("genre", genre);
        return query.getResultList();
    }

    public List<BookPojo> select_greaterPrice(double minPrice,double maxPrice) {
        TypedQuery<BookPojo> query = getQuery(SELECT_PRICE, BookPojo.class);
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);
        return query.getResultList();
    }

    public BookPojo checkCombination(String title, String author) {
        TypedQuery<BookPojo> query = getQuery(CHECK_COMBINATION, BookPojo.class);
        query.setParameter("title", title);
        query.setParameter("author", author);
        return getSingle(query);
    }

    public List<BookPojo> selectAll() {
        TypedQuery<BookPojo> query = getQuery(SELECT_ALL, BookPojo.class);
        return query.getResultList();
    }

}

