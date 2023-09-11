package com.aahan.onlineBookStore.service;

import com.aahan.onlineBookStore.dao.BookDao;
import com.aahan.onlineBookStore.dao.CartDao;
import com.aahan.onlineBookStore.model.CartForm;
import com.aahan.onlineBookStore.pojo.BookPojo;
import com.aahan.onlineBookStore.pojo.CartPojo;
import com.aahan.onlineBookStore.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartDao dao;

    @Autowired
    private BookDao bookDao;

    @Transactional
    public void add(CartPojo p) throws ApiException {
        CartPojo ex = dao.checkCombination(p.getTitle(),p.getAuthor());
        BookPojo pojo=bookDao.checkCombination(p.getTitle(),p.getAuthor());
        if(StringUtil.isEmpty(p.getTitle())) {
            throw new ApiException("Title cannot be empty");
        }
        if(StringUtil.isEmpty(p.getAuthor())) {
            throw new ApiException("Author cannot be empty");
        }
        if(StringUtil.isEmpty(p.getGenre())) {
            throw new ApiException("Genre cannot be empty");
        }
        if(pojo.getAvailability()==0){
            throw new ApiException("Required number of books are not present");
        }
        if(ex!=null){
            if(pojo.getAvailability()<= ex.getQuantity()){
                throw new ApiException("Required number of books are not present");
            }
            ex.setQuantity( ex.getQuantity()+1);
        }
        else {
            dao.insert(p);
        }
    }

    public CartPojo get(int id) throws ApiException{
        CartPojo p= dao.selectById(id);
        if(p==null){
            throw new ApiException("Cart with given ID does not exist");
        }
        return dao.selectById(id);
    }

    @Transactional
    public List<CartPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional
    public void delete(int id) throws ApiException{
        CartPojo pojo= get(id);
        if(pojo==null){
            throw new ApiException("Cart with given id already not present");
        }
        if(pojo.getQuantity()>1){
            pojo.setQuantity(pojo.getQuantity()-1);
        }
        else {
            dao.delete(id);
        }
    }

    @Transactional
    public void deleteAll() {
        List<CartPojo>list=dao.selectAll();
        for(CartPojo p:list){
            BookPojo pojo=bookDao.checkCombination(p.getTitle(),p.getAuthor());
            pojo.setAvailability(pojo.getAvailability()- p.getQuantity());
            dao.delete(p.getId());
        }

    }

    @Transactional
    public void update(int id, CartForm form) throws ApiException{
        CartPojo pojo= get(id);
        if(pojo==null){
            throw new ApiException("Cart with given id does not exist");
        }
        if(StringUtil.isEmpty(form.getTitle())) {
            throw new ApiException("Title cannot be empty");
        }
        if(StringUtil.isEmpty(form.getAuthor())) {
            throw new ApiException("Author cannot be empty");
        }
        if(StringUtil.isEmpty(form.getGenre())) {
            throw new ApiException("Genre cannot be empty");
        }
        pojo.setTitle(form.getTitle());
        pojo.setAuthor(form.getAuthor());
        pojo.setGenre(form.getGenre());
        pojo.setPrice(form.getPrice());
        pojo.setQuantity(form.getQuantity());
    }


}
