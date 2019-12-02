package com.sairamvankina.salesorder.service;

import com.sairamvankina.salesorder.entity.SalesOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalesOrderService {

    public abstract List<SalesOrder> getAllOrder();
    public abstract SalesOrder getOrderById(int orderId);
    public abstract SalesOrder createOrder(SalesOrder salesOrder);
    public abstract SalesOrder updateOrder(SalesOrder salesOrder, Integer orderId);
    public abstract void deleteOrder(int orderId);
}
