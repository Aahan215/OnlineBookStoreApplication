package com.aahan.onlineBookStore.dto;

import com.aahan.onlineBookStore.model.CartData;
import com.aahan.onlineBookStore.model.CartForm;
import com.aahan.onlineBookStore.pojo.CartPojo;
import com.aahan.onlineBookStore.service.ApiException;
import com.aahan.onlineBookStore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.aahan.onlineBookStore.util.NormalizeUtil.normalize;


@Component
public class cartDto {

    @Autowired
    private CartService service;

    public void add(CartForm form) throws ApiException {
        normalize(form);
        CartPojo cart = new CartPojo();
        cart.setTitle(form.getTitle());
        cart.setAuthor(form.getAuthor());
        cart.setGenre(form.getGenre());
        cart.setPrice(form.getPrice());
        cart.setQuantity(form.getQuantity());
        service.add(cart);
    }

    public CartData get(int id) throws ApiException{
        CartPojo cart= service.get(id);
        return convert(cart);
    }

    public List<CartData> getAll(){
        List<CartPojo>pojoList=service.getAll();
        List<CartData>list=new ArrayList<>();
        for (CartPojo p : pojoList) {
            list.add(convert(p));
        }
        return list;
    }

    public void update(int id, CartForm form) throws ApiException {
        normalize(form);
        service.update(id,form);
    }

    public void delete(int id) throws ApiException {
        service.delete(id);
    }

    public void deleteAll(){
        service.deleteAll();
    }


    public CartData convert(CartPojo cart){
        CartData d=new CartData();
        d.setId(cart.getId());
        d.setTitle(cart.getTitle());
        d.setAuthor(cart.getAuthor());
        d.setGenre(cart.getGenre());
        d.setPrice(cart.getPrice());
        d.setQuantity(cart.getQuantity());
        return d;
    }

}

