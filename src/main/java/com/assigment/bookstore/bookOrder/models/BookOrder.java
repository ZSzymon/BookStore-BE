package com.assigment.bookstore.bookOrder.models;

import com.assigment.bookstore.book.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookOrder {

    @Id
    private String id;
    @DocumentReference(lazy = true)
    private List<Book> orderList;
    private String clientEmail;
    private String description;
    private EBookOrderStatus orderStatus;
    private String payPalOrderId;
    public BookOrder(List<Book> books, String email, String description, String payPalOrderId) {
        this.orderList = books;
        this.clientEmail = email;
        this.description = description;
        this.orderStatus = EBookOrderStatus.CREATED;
        this.payPalOrderId = payPalOrderId;
    }

    public boolean isPayedOrCompletedOrShipped(){
        List<Boolean> conditions = List.of(
                orderStatus.equals(EBookOrderStatus.PAYED),
                orderStatus.equals(EBookOrderStatus.COMPLETED),
                orderStatus.equals(EBookOrderStatus.SHIPPED)
        );
        return conditions.contains(true);

    }
    public double getTotalOrderAmount(){
        return orderList.stream().map(Book::getPrice)
                .reduce(Double.valueOf("0"), Double::sum);
    }

    @Override
    public String toString() {
        return "BookOrder{" +
                "id='" + id + '\'' +
                ", orderList=" + orderList +
                ", clientEmail='" + clientEmail + '\'' +
                ", description='" + description + '\'' +
                ", orderStatus=" + orderStatus +
                ", payPalOrderId='" + payPalOrderId + '\'' +
                '}';
    }
}
