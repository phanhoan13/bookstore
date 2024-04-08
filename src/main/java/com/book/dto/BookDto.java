package com.book.dto;

import com.book.entity.Book;

public class BookDto {
    private Book book;
    private Integer quantity;

    public BookDto() {
    }

    public BookDto(Book book, Integer quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
