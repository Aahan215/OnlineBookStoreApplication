package com.aahan.onlineBookStore.dao;

import com.aahan.onlineBookStore.pojo.BookPojo;
import com.aahan.onlineBookStore.pojo.CartPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CartDao extends AbstractDao {
    private static String DELETE_ID = "delete from CartPojo p where id=:id";
    private static String SELECT_ID = "select p from CartPojo p where id=:id";
    private static String CHECK_COMBINATION = "select p from CartPojo p where title=:title AND author=:author";
    private static String SELECT_ALL="select p from CartPojo p";

    @Transactional
    public void insert(CartPojo p) {
        em().persist(p);
    }

    public int delete(int id) {
        Query query = em().createQuery(DELETE_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public CartPojo selectById(Integer id) {
        TypedQuery<CartPojo> query = getQuery(SELECT_ID, CartPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public CartPojo checkCombination(String title, String author) {
        TypedQuery<CartPojo> query = getQuery(CHECK_COMBINATION, CartPojo.class);
        query.setParameter("title", title);
        query.setParameter("author", author);
        return getSingle(query);
    }

    public List<CartPojo> selectAll() {
        TypedQuery<CartPojo> query = getQuery(SELECT_ALL, CartPojo.class);
        return query.getResultList();
    }
}
