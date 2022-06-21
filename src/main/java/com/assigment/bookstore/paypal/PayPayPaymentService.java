package com.assigment.bookstore.paypal;

import com.assigment.bookstore.bookOrder.BookOrderRepository;
import com.assigment.bookstore.bookOrder.models.BookOrder;
import com.assigment.bookstore.bookOrder.models.EBookOrderStatus;
import com.assigment.bookstore.exceptions.AlreadyPayedException;
import com.assigment.bookstore.exceptions.NotFoundException;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static com.assigment.bookstore.Utils.asJsonString;

@Service
@Slf4j
public class PayPayPaymentService implements PaymentService{

    private final String APPROVE_LINK_REL = "approve";

    private final PayPalHttpClient payPalHttpClient;

    private final BookOrderRepository bookOrderRepository;
    public PayPayPaymentService(@Value("${paypal.clientId}") String clientId,
                                @Value("${paypal.clientSecret}") String clientSecret,
                                BookOrderRepository bookOrderRepository) {
        this.bookOrderRepository = bookOrderRepository;
        payPalHttpClient = new PayPalHttpClient(new PayPalEnvironment.Sandbox(clientId, clientSecret));
    }
    @Override
    @SneakyThrows
    public CreatedOrder createOrder(Double totalAmount, URI returnUrl, String bookOrderId) {
        BookOrder bookOrder = bookOrderRepository.findById(bookOrderId)
                .map(bo -> {
                    if (bo.isPayedOrCompletedOrShipped()) {
                        throw new AlreadyPayedException(bookOrderId);
                    }
                    return bo;
                })
                .orElseThrow(()-> new NotFoundException("BookOrder", bookOrderId));


        OrderRequest orderRequest = createOrderRequest(totalAmount, returnUrl);
        OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest().requestBody(orderRequest);
        HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
        Order order = orderHttpResponse.result();
        log.info("Created order: "+order.id());
        LinkDescription approveUri = extractApprovalLink(order);

        bookOrder.setPayPalOrderId(order.id());
        bookOrder.setOrderStatus(EBookOrderStatus.INPAYMENT);
        bookOrderRepository.save(bookOrder);

        return new CreatedOrder(order.id(),URI.create(approveUri.href()));
    }

    @Override
    @SneakyThrows
    public HttpResponse<Order> captureOrder(String orderId) {
        final OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(orderId);
        final HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
        String httpResponseAsJson = asJsonString(httpResponse);

        BookOrder bookOrder = bookOrderRepository.findByPayPalOrderId(orderId).orElseThrow(
                () -> new NotFoundException("BookOrder", "PayPalId: " + orderId)
        );
        bookOrder.setOrderStatus(EBookOrderStatus.PAYED);
        bookOrder.setHttpResponseJson(httpResponseAsJson);

        bookOrderRepository.save(bookOrder);
        log.info("Captured order: "+orderId);
        log.info("Order Capture Status: {}",httpResponse.result().status());


        return httpResponse;
    }

    private OrderRequest createOrderRequest(Double totalAmount, URI returnUrl) {
        final OrderRequest orderRequest = new OrderRequest();
        setCheckoutIntent(orderRequest);
        setPurchaseUnits(totalAmount, orderRequest);
        setApplicationContext(returnUrl, orderRequest);
        return orderRequest;
    }

    private OrderRequest setApplicationContext(URI returnUrl, OrderRequest orderRequest) {
        return orderRequest.applicationContext(new ApplicationContext().returnUrl(returnUrl.toString()));
    }

    private void setPurchaseUnits(Double totalAmount, OrderRequest orderRequest) {
        final PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value(totalAmount.toString()));
        orderRequest.purchaseUnits(Arrays.asList(purchaseUnitRequest));
    }

    private void setCheckoutIntent(OrderRequest orderRequest) {
        orderRequest.checkoutPaymentIntent("CAPTURE");
    }

    private LinkDescription extractApprovalLink(Order order) {
        LinkDescription approveUri = order.links().stream()
                .filter(link -> APPROVE_LINK_REL.equals(link.rel()))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        return approveUri;
    }
}
