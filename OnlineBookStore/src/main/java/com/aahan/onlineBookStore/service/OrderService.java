package com.aahan.onlineBookStore.service;

import com.aahan.onlineBookStore.dao.OrderDao;
import com.aahan.onlineBookStore.model.EnumData;
import com.aahan.onlineBookStore.model.InvoiceForm;
import com.aahan.onlineBookStore.model.OrderItem;
import com.aahan.onlineBookStore.pojo.OrderItemPojo;
import com.aahan.onlineBookStore.pojo.OutwardOrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class OrderService {

	@Autowired
	private OrderDao dao;
	@Autowired
	private OrderItemService orderItemService;

	@Value("${invoice.url}")
	private String invoiceUrl;

	@Transactional(rollbackOn = ApiException.class)
	public void add(OutwardOrderPojo pojo) throws ApiException {
		dao.insert(pojo);
	}

	@Transactional(rollbackOn = ApiException.class)
	public OutwardOrderPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public List<OutwardOrderPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, String status) throws ApiException {
		OutwardOrderPojo existingPojo = getCheck(id);
		if(status.equals("PROCESSING")){
			existingPojo.setStatus(EnumData.Status.PROCESSING);
		}if(status.equals("SHIPPED")){
			existingPojo.setStatus(EnumData.Status.SHIPPED);
		}if(status.equals("DELIVERED")){
			existingPojo.setStatus(EnumData.Status.DELIVERED);
		}
	}

	@Transactional
	public OutwardOrderPojo getCheck(int id) throws ApiException {
		OutwardOrderPojo pojo = dao.select(id);
		if (pojo == null) {
			throw new ApiException("Order with given ID does not exit, id: " + id);
		}
		return pojo;
	}

	public List<OrderItemPojo> selectByOrderId(Integer orderId) throws ApiException{
		return orderItemService.getOrderList(orderId);
	}

	public ResponseEntity<byte[]> getInvoicePDF(Integer id) throws Exception {
		InvoiceForm invoiceForm = generateInvoiceForOrder(id);
		RestTemplate restTemplate = new RestTemplate();
		byte[] contents = Base64.getDecoder().decode(restTemplate.postForEntity(invoiceUrl, invoiceForm, byte[].class).getBody());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		String filename = "invoice.pdf";
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
		return response;
	}
	public InvoiceForm generateInvoiceForOrder(Integer orderId) throws ApiException
	{
		InvoiceForm invoiceForm = new InvoiceForm();
		OutwardOrderPojo outwardOrderPojo = getCheck(orderId);
		invoiceForm.setOrderId(outwardOrderPojo.getId());
		invoiceForm.setPlacedDate(outwardOrderPojo.getDateTime().toString());
		List<OrderItemPojo> orderItemPojoList = selectByOrderId(outwardOrderPojo.getId());
		List<OrderItem> orderItemList = new ArrayList<>();
		for(OrderItemPojo pojo: orderItemPojoList) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderItemId(pojo.getId());
			orderItem.setAuthor(pojo.getAuthor());
			orderItem.setTitle(pojo.getTitle());
			orderItem.setGenre(pojo.getGenre());
			orderItem.setPrice(pojo.getPrice());
			orderItem.setQuantity(pojo.getQuantity());
			orderItemList.add(orderItem);
		}
		invoiceForm.setOrderItemList(orderItemList);
		return invoiceForm;
	}

}
