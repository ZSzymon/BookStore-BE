package com.assigment.bookstore.paypal;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;

import java.net.URI;

public interface PaymentService {

    CreatedOrder createOrder(Double totalAmount, URI returnUrl);

    HttpResponse<Order> captureOrder(String orderId);
}
