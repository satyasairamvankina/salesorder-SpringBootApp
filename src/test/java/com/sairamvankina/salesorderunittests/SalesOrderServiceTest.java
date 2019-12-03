package com.sairamvankina.salesorderunittests;

import com.sairamvankina.salesorder.entity.Address;
import com.sairamvankina.salesorder.entity.Item;
import com.sairamvankina.salesorder.entity.SalesOrder;
import com.sairamvankina.salesorder.exceptions.ResourceBadRequestException;
import com.sairamvankina.salesorder.exceptions.ResourceNotFoundException;
import com.sairamvankina.salesorder.repository.AddressRepository;
import com.sairamvankina.salesorder.repository.ItemsRepository;
import com.sairamvankina.salesorder.repository.SalesOrderRepository;
import com.sairamvankina.salesorder.service.SalesOrderServiceInterfaceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class SalesOrderServiceTest {

    @TestConfiguration
    static class SalesOrderServiceTestConfiguration{
        @Bean
        public SalesOrderServiceInterfaceImpl salesOrderService(){
            return new SalesOrderServiceInterfaceImpl();
        }
    }

    @Autowired
    private SalesOrderServiceInterfaceImpl salesOrderServiceInterfaceImpl;

    @MockBean
    private SalesOrderRepository salesOrderRepository;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private ItemsRepository itemsRepository;

    List<SalesOrder> salesOrderList;

    @Before
    public void startup(){
        Address billingAddress = new Address();
        billingAddress.setId(567);
        billingAddress.setStreet("123 N Bothel");
        billingAddress.setCity("Seattle");
        billingAddress.setState("Washington");
        billingAddress.setCountry("USA");
        billingAddress.setZipCode(64467);

        Address shippingAddress = new Address();
        shippingAddress.setId(568);
        shippingAddress.setZipCode(64465);
        shippingAddress.setCountry("USA");
        shippingAddress.setState("MO");
        shippingAddress.setCity("Kansas");
        shippingAddress.setStreet(" Overland part Apt-2");

        Item item = new Item();
        item.setItemId(564);
        item.setColor(Item.Color.GREEN);
        item.setCost(23.0);
        item.setDescription("Awesome Yellow T shirt");
        item.setQuantity(3);
        item.setSize(Item.Size.XL);
        List<Item> items = new ArrayList<>();
        items.add(item);

        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setSalesId(576);
        salesOrder.setTotalPrice(34.0);
        salesOrder.setSalesTax(5.04);
        salesOrder.setOrderPrice(45);
        salesOrder.setOrderDate(new Date());
        salesOrder.setItems(items);
        salesOrder.setBillingAddress(billingAddress);
        salesOrder.setShippingAddress(shippingAddress);

        salesOrderList = Collections.singletonList(salesOrder);

        Mockito.when(salesOrderRepository.findAll()).thenReturn(salesOrderList);
        Mockito.when(salesOrderRepository.findById(salesOrder.getSalesId())).thenReturn(Optional.of(salesOrder));
        Mockito.when(salesOrderRepository.save(salesOrder)).thenReturn(salesOrder);


    }
    @Test
    public void getAllOrder(){
        List<SalesOrder> salesOrderList1 = salesOrderServiceInterfaceImpl.getAllOrder();
        Assert.assertEquals("Lists are equal",salesOrderList,salesOrderList1);
    }
    @Test
    public void getOrderById(){
        SalesOrder salesOrder1 = salesOrderServiceInterfaceImpl.getOrderById(salesOrderList.get(0).getSalesId());
        Assert.assertEquals("sales objects get by Id are equal",salesOrderList.get(0),salesOrder1);
        Assert.assertEquals("sales objects Id's  are equal",salesOrderList.get(0).getSalesId(),salesOrder1.getSalesId());
        Assert.assertEquals("sales objects items are equal",salesOrderList.get(0).getItems(),salesOrder1.getItems());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getOrderByIdFailure(){
        SalesOrder salesOrder1 = salesOrderServiceInterfaceImpl.getOrderById(6798);
    }

    @Test
    public void createOrder(){
        SalesOrder salesOrder1 = salesOrderServiceInterfaceImpl.createOrder(salesOrderList.get(0));

        Assert.assertEquals("Sales order created are equals",salesOrderList.get(0),salesOrder1);
    }

    @Test(expected = ResourceBadRequestException.class)
    public void createOrderFailure(){
//        SalesOrder salesOrder1 = salesOrderServiceInterfaceImpl.createOrder(salesOrderList.get(0));
//        salesOrder1.setSalesId(null);
//        salesOrder1.setSalesTax(345);
            SalesOrder salesOrder2 = salesOrderServiceInterfaceImpl.createOrder(null);

    }
    @Test
    public void updateOrder(){
        SalesOrder salesOrder1 = salesOrderServiceInterfaceImpl.updateOrder(salesOrderList.get(0),salesOrderList.get(0).getSalesId());
        Assert.assertEquals("Updated Sales are equal",salesOrderList.get(0),salesOrder1);
    }

    @Test(expected = ResourceBadRequestException.class)
    public void updateOrderFailure(){
//        SalesOrder salesOrder1 = salesOrderServiceInterfaceImpl.createOrder(salesOrderList.get(0));
//        salesOrder1.setSalesId(23);
//        salesOrder1.setSalesTax(345.32);
        SalesOrder salesOrder2 = salesOrderServiceInterfaceImpl.updateOrder(null,87978);
    }
    @Test(expected = ResourceNotFoundException.class)
    public void deleteOrder(){
        salesOrderServiceInterfaceImpl.deleteOrder(salesOrderList.get(0).getSalesId());
        salesOrderList = new ArrayList<>();
        SalesOrder salesOrder1 = salesOrderServiceInterfaceImpl.getOrderById(3456);
    }


}
