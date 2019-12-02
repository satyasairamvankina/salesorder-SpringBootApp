package com.sairamvankina.salesorder.intergrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonString;
import com.sairamvankina.salesorder.entity.Address;
import com.sairamvankina.salesorder.entity.Item;
import com.sairamvankina.salesorder.entity.SalesOrder;
import com.sairamvankina.salesorder.repository.AddressRepository;
import com.sairamvankina.salesorder.repository.ItemsRepository;
import com.sairamvankina.salesorder.repository.SalesOrderRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("integrationtest")
public class SalesOrderControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ItemsRepository itemsRepository;

    List<SalesOrder> salesOrderList;

    @Before
    public void setup(){
        Address billingAddress = new Address();
//        billingAddress.setId(567);
        billingAddress.setStreet("123 N Bothel");
        billingAddress.setCity("Seattle");
        billingAddress.setState("Washington");
        billingAddress.setCountry("USA");
        billingAddress.setZipCode(64467);

        Address shippingAddress = new Address();
//        shippingAddress.setId(568);
        shippingAddress.setZipCode(64465);
        shippingAddress.setCountry("USA");
        shippingAddress.setState("MO");
        shippingAddress.setCity("Kansas");
        shippingAddress.setStreet(" Overland part Apt-2");

        Item item = new Item();
//        item.setItemId(564);
        item.setColor(Item.Color.GREEN);
        item.setCost(23.0);
        item.setDescription("Awesome Yellow T shirt");
        item.setQuantity(3);
        item.setSize(Item.Size.XL);
        List<Item> items = new ArrayList<>();
        items.add(item);

        SalesOrder salesOrder = new SalesOrder();
//        salesOrder.setSalesId(576);
        salesOrder.setTotalPrice(34.0);
        salesOrder.setSalesTax(5.04);
        salesOrder.setOrderPrice(45);
        salesOrder.setDeliveryCost(1);
//        salesOrder.setOrderDate(new Date("2019-12-02"));
        salesOrder.setOrderStatus(SalesOrder.OrderStatus.Released);
        salesOrder.setOrderDate(new Date());
        salesOrder.setItems(items);
        salesOrder.setBillingAddress(billingAddress);
        salesOrder.setShippingAddress(shippingAddress);

        addressRepository.save(billingAddress);
        addressRepository.save(shippingAddress);
        itemsRepository.save(item);
        salesOrderRepository.save(salesOrder);
        salesOrderList = Collections.singletonList(salesOrder);

    }

    @After
    public void cleanup(){
        salesOrderRepository.deleteAll();
    }


    @Test
    public void getAllOrder() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/salesorder"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderPrice",Matchers.equalTo(45.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].orderStatus",Matchers.equalTo("Released")));

    }

    @Test
    public void getOrderById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/salesorder/"+salesOrderList.get(0).getSalesId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.salesId", Matchers.equalTo(salesOrderList.get(0).getSalesId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.orderPrice",Matchers.equalTo(45.0)));
    }

    @Test
    public void createOrder() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/api/v1/salesorder")
                .content(asJsonString(salesOrderList.get(0)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.orderPrice",Matchers.equalTo(45.0)));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateOrder() throws Exception {
        SalesOrder salesOrder1 = salesOrderList.get(0);
        salesOrder1.setDeliveryCost(23);
        salesOrder1.setSalesTax(13);

        mvc.perform( MockMvcRequestBuilders.put("/api/v1/salesorder/"+salesOrderList.get(0).getSalesId())
                .content(asJsonString(salesOrder1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.deliveryCost",Matchers.equalTo(23.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.salesTax",Matchers.equalTo(13.0)));

    }

    @Test
    public void deleteOrder() throws Exception {
        mvc.perform( MockMvcRequestBuilders.delete("/api/v1/salesorder/4"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());


    }
}
