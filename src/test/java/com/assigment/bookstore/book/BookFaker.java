package com.assigment.bookstore.book;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

public class BookFaker {

    @Test
    void createBook(){
        Faker faker = new Faker();
        Book book = faker.book();

        System.out.println(book.author());
        System.out.println(book.publisher());
        System.out.println(book.title());
    }
}
