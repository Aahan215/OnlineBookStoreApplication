package com.aahan.onlineBookStore.service;

import com.aahan.onlineBookStore.dao.OrderItemDao;
import com.aahan.onlineBookStore.pojo.CartPojo;
import com.aahan.onlineBookStore.pojo.OrderItemPojo;
import com.aahan.onlineBookStore.pojo.OutwardOrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemDao dao;
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;

	@Transactional(rollbackOn = ApiException.class)
	public void add(int orderId) throws ApiException {
		List<CartPojo> list = cartService.getAll();
		if(list.size()==0){
			throw new ApiException("Cannot place empty order");
		}
		for (CartPojo p : list) {
			OrderItemPojo o = new OrderItemPojo();
			o.setQuantity(p.getQuantity());
			o.setOrderId(orderId);
			o.setAuthor(p.getAuthor());
			o.setPrice(p.getPrice());
			o.setTitle(p.getTitle());
			o.setGenre(p.getGenre());
			dao.insert(o);
		}
		cartService.deleteAll();
	}

	@Transactional
	public void delete(Integer id) throws ApiException {
		dao.delete(id);
	}

	@Transactional
	public List<OrderItemPojo> getOrderList(Integer id) throws ApiException {
		return dao.selectOrderItemByOrderId(id);
	}

	@Transactional
	public List<OrderItemPojo> getAll() {
		return dao.selectAll();
	}
	@Transactional
	public List<OrderItemPojo> getRelevantAll(List<OutwardOrderPojo> pojo) {
		return dao.selectRelevant(pojo);
	}

	@Transactional(rollbackOn = ApiException.class)
	public OrderItemPojo getCheck(Integer id) throws ApiException {
		OrderItemPojo pojo = dao.select(id);
		if (pojo == null) {
			throw new ApiException("OrderItem with given ID does not exit, id: " + id);
		}
		return pojo;
	}

}
