package com.assigment.bookstore.paypal.token;

import com.assigment.bookstore.bookOrder.BookOrderRepository;
import com.assigment.bookstore.bookOrder.models.BookOrder;
import com.assigment.bookstore.bookOrder.models.EBookOrderStatus;
import com.assigment.bookstore.paypal.CreatedOrder;
import com.assigment.bookstore.paypal.PaymentService;
import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final PaymentService paymentService;
    private final TokenRepository tokenRepository;
    private final BookOrderRepository bookOrderRepository;

    public OrderController(PaymentService paymentService,
                           TokenRepository tokenRepository,
                           BookOrderRepository bookOrderRepository) {
        this.paymentService = paymentService;
        this.tokenRepository = tokenRepository;
        this.bookOrderRepository = bookOrderRepository;
    }

    private String orderId = "";
    private String bookOrderString;

    @GetMapping
    public String orderPage(Model model){
        model.addAttribute("orderId",orderId);
        model.addAttribute("bookOrder", bookOrderString);
        return "order";
    }

    @GetMapping("/capture")
    public String captureOrder(@RequestParam String token){
        //FIXME(Never Do this either put it in proper scope or in DB)
        orderId = token;
        HttpResponse<Order> orderHttpResponse = paymentService.captureOrder(token);
        BookOrder bookOrder = bookOrderRepository.findByPayPalOrderId(token).map(_bookOrder -> {
            _bookOrder.setOrderStatus(EBookOrderStatus.PAYED);
            return bookOrderRepository.save(_bookOrder);
        }).get();

        bookOrderString = bookOrder.toString();
        tokenRepository.save(new OrderModel(token,
                orderHttpResponse.headers(),
                orderHttpResponse.statusCode(),
                orderHttpResponse.result(),
                bookOrder));

        return "redirect:/orders";
    }

    @PostMapping
    public String placeOrder(@RequestParam Double totalAmount,String bookOrderId, HttpServletRequest request){
        final URI returnUrl = buildReturnUrl(request);
        if (bookOrderId==null){
            return "redirect:error";
        }

        CreatedOrder createdOrder = paymentService.createOrder(totalAmount, returnUrl);
        bookOrderRepository.findById(bookOrderId).ifPresent(bookOrder -> {
            bookOrder.setOrderStatus(EBookOrderStatus.INPAYMENT);
            bookOrder.setPayPalOrderId(createdOrder.getOrderId());
            bookOrderRepository.save(bookOrder);
        });

        return "redirect:"+createdOrder.getApprovalLink();
    }

    private URI buildReturnUrl(HttpServletRequest request) {
        try {
            URI requestUri = URI.create(request.getRequestURL().toString());
            return new URI(requestUri.getScheme(),
                    requestUri.getUserInfo(),
                    requestUri.getHost(),
                    requestUri.getPort(),
                    "/orders/capture",
                    null, null);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
