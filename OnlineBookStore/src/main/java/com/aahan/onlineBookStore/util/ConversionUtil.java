package com.aahan.onlineBookStore.util;

import com.aahan.onlineBookStore.model.*;
import com.aahan.onlineBookStore.pojo.OrderItemPojo;
import com.aahan.onlineBookStore.pojo.OutwardOrderPojo;
import com.aahan.onlineBookStore.pojo.UserPojo;

public class ConversionUtil {

    public static UserData convert(UserPojo pojo) {
        UserData data = new UserData();
        data.setEmail(pojo.getEmail());
        data.setRole(pojo.getRole().name());
        data.setId(pojo.getId());
        return data;
    }

    public static UserPojo convert(UserForm form) {
        UserPojo pojo = new UserPojo();
        pojo.setEmail(form.getEmail());
        pojo.setRole(EnumData.Role.valueOf(form.getRole()));
        pojo.setPassword(form.getPassword());
        return pojo;
    }

    public static OrderData convert(OutwardOrderPojo pojo) {
        OrderData data = new OrderData();
        data.setId(pojo.getId());
        data.setStatus(String.valueOf(pojo.getStatus()));
        data.setDateTime(pojo.getDateTime());
        return data;
    }

    public static OrderItemData convert(OrderItemPojo pojo) {
        OrderItemData data = new OrderItemData();
        data.setAuthor(pojo.getAuthor());
        data.setTitle(pojo.getTitle());
        data.setGenre(pojo.getGenre());
        data.setPrice(pojo.getPrice());
        data.setQuantity(pojo.getQuantity());
        data.setOrderId(pojo.getOrderId());
        return data;
    }
}
