package com.sairamvankina.salesorder.exceptions;

import com.sairamvankina.salesorder.entity.SalesOrder;

public class SalesResponse extends Response<SalesOrder> {

    public SalesResponse(Integer status, String message, SalesOrder response) {
        super.status = status;
        super.message = message;
        super.response = response;
    }
}
