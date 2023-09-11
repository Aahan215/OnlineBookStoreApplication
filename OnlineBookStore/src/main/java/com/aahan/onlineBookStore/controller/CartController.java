package com.aahan.onlineBookStore.controller;

import com.aahan.onlineBookStore.dto.cartDto;
import com.aahan.onlineBookStore.model.CartData;
import com.aahan.onlineBookStore.model.CartForm;
import com.aahan.onlineBookStore.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class CartController {

    @Autowired
    private cartDto dto;

    @ApiOperation(value = "Add an item to the Cart")
    @RequestMapping(path = "/api/cart", method = RequestMethod.POST)
    public void add(@RequestBody CartForm form) throws ApiException {
        dto.add(form);
    }

    @ApiOperation(value = "Get a cart Item by ID")
    @RequestMapping(path = "/api/cart/{id}", method = RequestMethod.GET)
    public CartData get(@PathVariable Integer id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Get list of all cart Items")
    @RequestMapping(path = "/api/cart", method = RequestMethod.GET)
    public List<CartData> getAll(){
        return dto.getAll();
    }

    @ApiOperation(value = "Update a cart Item")
    @RequestMapping(path = "/api/cart/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody CartForm form) throws ApiException {
        dto.update(id,form);
    }

    @ApiOperation(value = "Delete a Cart Item")
    @RequestMapping(path = "/api/cart/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException{
        dto.delete(id);
    }

    @ApiOperation(value = "Delete Cart")
    @RequestMapping(path = "/api/cart/deleteAll", method = RequestMethod.POST)
    public void deleteAll(){
         dto.deleteAll();
    }

}
