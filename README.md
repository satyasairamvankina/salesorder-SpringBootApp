# Salesorder-SpringBootApp
## Salesorder-SpringBoot-Application
### Created all the crud operations for Sales Order with Address and Items. 
Tested using Unit testing and Integartion testing 
Exposing API's with swagger UI
H2 Database for Integration testing and running application.
Database UI can be seen in H2 console 
http://localhost:8099/h2-console/login.do?jsessionid=b0fb647b94539ddf476da21e390d9cb2
API's : 
```
http://localhost:8099/api/v1/salesorder/
```
Swagger UI exposes : 
```
http://localhost:8099/swagger-ui.html#/
```

Request Body:
```
{
	"items":[{
		"cost":2.0,
		"color":"RED",
		"size":"L",
		"quantity":1,
		"description":"blue color shirt"
	}],
	"orderDate":"2019-11-12T22:22:22",
	"billingAddress":{
		"street":"1011 N walnut",
		"city":"Seattle",
		"state":"MO",
		"zipcode":64468,
		"country":"USA"
	},
	"shippingAddress":{
		"street":"1012 N walnut",
		"city":"Seattle",
		"state":"MO",
		"zipcode":64468,
		"country":"USA"
	},
	"orderStatus":"Scheduled",
	"orderPrice":20.0,
	"deliveryCost":2.0,
	"salesTax":790,
	"totalPrice":24.0
}
```
Response body for successful request
```
{
    "status": 201,
    "message": "sales order created succesfully",
    "response": {
        "salesId": 23,
        "items": [
            {
                "itemId": 22,
                "cost": 2.0,
                "color": "RED",
                "size": "L",
                "quantity": 1,
                "description": "blue color shirt"
            }
        ],
        "orderDate": "2019-11-12T22:22:22.000+0000",
        "billingAddress": {
            "id": 20,
            "street": "1011 N walnut",
            "city": "Seattle",
            "state": "MO",
            "zipCode": null,
            "country": "USA"
        },
        "shippingAddress": {
            "id": 21,
            "street": "1012 N walnut",
            "city": "Seattle",
            "state": "MO",
            "zipCode": null,
            "country": "USA"
        },
        "orderStatus": "Scheduled",
        "orderPrice": 20.0,
        "deliveryCost": 2.0,
        "salesTax": 7.0,
        "totalPrice": 24.0
    }
}
```

Response Body for validation error:
```
{
    "timestamp": "2019-12-02T20:12:28.083+0000",
    "status": 400,
    "error": "Bad Request",
    "errors": [
        {
            "codes": [
                "Max.salesOrder.salesTax",
                "Max.salesTax",
                "Max.double",
                "Max"
            ],
            "arguments": [
                {
                    "codes": [
                        "salesOrder.salesTax",
                        "salesTax"
                    ],
                    "arguments": null,
                    "defaultMessage": "salesTax",
                    "code": "salesTax"
                },
                100
            ],
            "defaultMessage": "Sales tax % has to be between in percentage 0 to 100",
            "objectName": "salesOrder",
            "field": "salesTax",
            "rejectedValue": 790.0,
            "bindingFailure": false,
            "code": "Max"
        }
    ],
    "message": "Validation failed for object='salesOrder'. Error count: 1",
    "path": "/api/v1/salesorder"
}
```
Run the Application: Run both Junit and Integration test before building application
```
maven clean install
```
