package com.sairamvankina.salesorder.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@Entity
public class SalesOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer salesId;

    @OneToMany
    private List<Item> items;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @OneToOne
    private Address billingAddress;

    @OneToOne
    private Address shippingAddress;

    private OrderStatus orderStatus;
    private double orderPrice;
    private double deliveryCost;
    // In percentage
    @Min(value = 0,message = "Sales tax % has to be between in percentage 0 to 100")
    @Max(value = 100,message = "Sales tax % has to be between in percentage 0 to 100")
    private double salesTax;
    // calculate total price for now can be optional
    private double totalPrice;
    public enum OrderStatus {
        Released,Scheduled,Shipped,Invoice
    }

    public SalesOrder() {
    }



    public SalesOrder(List<Item> items, Date orderDate, Address billingAddress, Address shippingAddress, OrderStatus orderStatus, double orderPrice, double deliveryCost, @Min(value = 0) @Max(value = 100, message = "Sales tax % has to be between in percentage 0 to 100") double salesTax, double totalPrice) {
        this.items = items;
        this.orderDate = orderDate;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.orderStatus = orderStatus;
        this.orderPrice = orderPrice;
        this.deliveryCost = deliveryCost;
        this.salesTax = salesTax;
        this.totalPrice = totalPrice;
    }

    public Integer getSalesId() {
        return salesId;
    }

    public void setSalesId(Integer salesId) {
        this.salesId = salesId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
