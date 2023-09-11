package com.aahan.onlineBookStore.controller;

import com.aahan.onlineBookStore.dto.orderDto;
import com.aahan.onlineBookStore.model.OrderData;
import com.aahan.onlineBookStore.model.OrderItemData;
import com.aahan.onlineBookStore.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class OrderController {

	@Autowired
	private orderDto dto;

	@ApiOperation(value = "Add an order")
	@RequestMapping(path = "/api/order", method = RequestMethod.POST)
	public void createOrder() throws ApiException {
		dto.add();
	}

	@ApiOperation(value = "Get list of all orders")
	@RequestMapping(path = "/api/order", method = RequestMethod.GET)
	public List<OrderData> getAll() {
		return dto.getAll();
	}

	@ApiOperation(value = "Update an order")
	@RequestMapping(path = "/api/order/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable Integer id, @RequestBody String status) throws ApiException {
		dto.update(id,status);
	}

	@ApiOperation(value = "Get the list of orderItem by orderID")
	@RequestMapping(path = "/api/order/view/{id}", method = RequestMethod.GET)
	public List<OrderItemData> getOrderList(@PathVariable int id) throws ApiException {
		return dto.getOrderList(id);
	}

	@ApiOperation(value = "Download Invoice")
	@GetMapping(path = "/api/invoice/{id}")
	public ResponseEntity<byte[]> getInvoicePDF(@PathVariable Integer id) throws Exception{
		return dto.getInvoicePDF(id);
	}

}
