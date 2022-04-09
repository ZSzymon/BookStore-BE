package com.assigment.bookstore.order;

import com.assigment.bookstore.GenericController;
import com.assigment.bookstore.IGenericService;
import com.assigment.bookstore.order.models.Order;
import com.assigment.bookstore.order.models.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController extends GenericController<Order, OrderDto> {


    private OrderService orderService;

    public OrderController(OrderService service) {
        super(service);
        orderService = service;
    }




}
