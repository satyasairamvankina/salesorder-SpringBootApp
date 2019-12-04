package com.sairamvankina.salesorder.controller;


import com.sairamvankina.salesorder.entity.SalesOrder;
import com.sairamvankina.salesorder.exceptions.SalesResponse;
import com.sairamvankina.salesorder.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/salesorder")
public class SalesOrderController {

    @Autowired
    SalesOrderService salesOrderService;

    @GetMapping
    public List<SalesOrder> getAllOrder(){
        return salesOrderService.getAllOrder();
    }

    @GetMapping("{orderId}")
    public ResponseEntity<SalesResponse> getOrderById(@PathVariable("orderId") int  orderId){
        SalesOrder salesOrder =  salesOrderService.getOrderById(orderId);
//        return new ResponseEntity<SalesOrder>(salesOrder, HttpStatus.OK);
        return new ResponseEntity<SalesResponse>(new SalesResponse(HttpStatus.OK.value(), "sales order created succesfully",  salesOrder), HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<SalesResponse> createOrder(@Valid @RequestBody SalesOrder salesOrder){
        SalesOrder salesOrder1 =  salesOrderService.createOrder(salesOrder);
//        return new ResponseEntity<SalesOrder>(salesOrder1, HttpStatus.CREATED);
        return new ResponseEntity<>(new SalesResponse(HttpStatus.CREATED.value(), "sales order created succesfully",  salesOrder1), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<SalesResponse> updateOrder(@Valid @RequestBody  SalesOrder salesOrder,@PathVariable("id") Integer orderId){
        SalesOrder salesOrder1 =salesOrderService.updateOrder(salesOrder,orderId);
        return new ResponseEntity<SalesResponse>(new SalesResponse(HttpStatus.OK.value(), "sales order updated succesfully",  salesOrder1), HttpStatus.OK);
    }

    @DeleteMapping("{orderId}")
    public ResponseEntity<SalesResponse> deleteOrder(@PathVariable("orderId") int  orderId){
        salesOrderService.deleteOrder(orderId);
        return new ResponseEntity<SalesResponse>(new SalesResponse(HttpStatus.NO_CONTENT.value(), "sales order with id "+orderId+ " deleted succesfully",  null), HttpStatus.NO_CONTENT);
    }

}
