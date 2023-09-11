package com.aahan.onlineBookStore.dto;

import com.aahan.onlineBookStore.model.CreateOrderForm;
import com.aahan.onlineBookStore.model.OrderData;
import com.aahan.onlineBookStore.model.OrderItemData;
import com.aahan.onlineBookStore.pojo.OrderItemPojo;
import com.aahan.onlineBookStore.pojo.OutwardOrderPojo;
import com.aahan.onlineBookStore.pojo.OutwardOrderPojo;
import com.aahan.onlineBookStore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.aahan.onlineBookStore.util.ConversionUtil.convert;

@Component
public class orderDto {
    @Autowired
    private OrderService service;
    @Autowired
    private OrderItemService orderItemService;

    @Transactional(rollbackOn = ApiException.class)
    public Integer add() throws ApiException {
        OutwardOrderPojo orderPojo = new OutwardOrderPojo();
        service.add(orderPojo);
        Integer orderId= orderPojo.getId();
        orderItemService.add(orderId);
        return orderId;
    }

    public OrderData get(Integer id) throws ApiException {
        OutwardOrderPojo orderPojo = service.getCheck(id);
        return convert(orderPojo);
    }
    public List<OrderData> getAll() {
        List<OutwardOrderPojo> pojoList = service.getAll();
        List<OrderData> list2 = new ArrayList<OrderData>();
        for (OutwardOrderPojo orderPojo : pojoList) {
            list2.add(convert(orderPojo));
        }
        return list2;
    }

    @Transactional
    public List<OrderItemData> getOrderList(int id) throws ApiException {
        List<OrderItemPojo> pojoList = orderItemService.getOrderList(id);
        List<OrderItemData> dataList = new ArrayList<>();
        for (OrderItemPojo pojo : pojoList) {
            dataList.add(convert(pojo));
        }
        return dataList;
    }

    public void update(Integer id,String status) throws ApiException {
        service.update(id,status);
    }
    public ResponseEntity<byte[]> getInvoicePDF( Integer id) throws Exception{
        return service.getInvoicePDF(id);
    }
}
