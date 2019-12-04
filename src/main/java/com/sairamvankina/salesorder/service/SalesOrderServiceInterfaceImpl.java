package com.sairamvankina.salesorder.service;

import com.sairamvankina.salesorder.entity.SalesOrder;
import com.sairamvankina.salesorder.exceptions.ResourceBadRequestException;
import com.sairamvankina.salesorder.exceptions.ResourceNotFoundException;
import com.sairamvankina.salesorder.repository.AddressRepository;
import com.sairamvankina.salesorder.repository.ItemsRepository;
import com.sairamvankina.salesorder.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SalesOrderServiceInterfaceImpl implements SalesOrderService {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ItemsRepository itemsRepository;

    @Override
    public List<SalesOrder> getAllOrder() {
        return salesOrderRepository.findAll();
    }

    @Override
    public SalesOrder getOrderById(int orderId) {
        Optional<SalesOrder> a =  salesOrderRepository.findById(orderId);
        a.orElseThrow(() ->
                new ResourceNotFoundException("The order with Id " +orderId+" doesn't exists")
        );
        return a.get();
    }

    @Override
    public SalesOrder createOrder(SalesOrder salesOrder) {
        if(salesOrder == null)throw new ResourceBadRequestException("Bad request for creating sales order cannot be null"+salesOrder);
        addressRepository.save(salesOrder.getBillingAddress());
        addressRepository.save(salesOrder.getShippingAddress());
        itemsRepository.saveAll(salesOrder.getItems());
        SalesOrder salesOrder1 =  salesOrderRepository.save(salesOrder);
        if(salesOrder1 == null ) throw new ResourceBadRequestException("Bad request for creating sales order "+salesOrder);
        return salesOrder1;
    }

    @Override
    public SalesOrder updateOrder(SalesOrder salesOrder, Integer orderId) {
        if(salesOrder == null) throw  new ResourceBadRequestException("Bad request for update of Sales order");
        return salesOrderRepository.findById(orderId).map(order -> {
            order.setBillingAddress(salesOrder.getBillingAddress());
            order.setDeliveryCost(salesOrder.getDeliveryCost());
            order.setItems(salesOrder.getItems());
            order.setOrderDate(salesOrder.getOrderDate());
            order.setOrderStatus(salesOrder.getOrderStatus());
            order.setSalesTax(salesOrder.getSalesTax());
            order.setOrderPrice(salesOrder.getOrderPrice());
            order.setTotalPrice(salesOrder.getTotalPrice());
            addressRepository.save(salesOrder.getBillingAddress());
            addressRepository.save(salesOrder.getShippingAddress());
            itemsRepository.saveAll(salesOrder.getItems());
            return salesOrderRepository.save(order);
        }).orElseGet(() -> {
            salesOrder.setSalesId(orderId);
            addressRepository.save(salesOrder.getBillingAddress());
            addressRepository.save(salesOrder.getShippingAddress());
            itemsRepository.saveAll(salesOrder.getItems());
            return salesOrderRepository.save(salesOrder);
        });
    }

    @Override
    public void deleteOrder(int orderId) {
        Optional<SalesOrder> salesOrder = salesOrderRepository.findById(orderId);
        salesOrder.orElseThrow(() -> new  ResourceNotFoundException("The order with Id " +orderId+" doesn't exists"));
        itemsRepository.deleteAll(salesOrder.get().getItems());
        addressRepository.delete(salesOrder.get().getBillingAddress());
        addressRepository.delete(salesOrder.get().getShippingAddress());
        salesOrderRepository.delete(salesOrder.get());
    }
}
