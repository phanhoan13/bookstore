package com.book.repository;

import com.book.entity.Book;
import com.book.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @Query("select i from Invoice i inner join i.user u where u.email = ?1")
    public List<Invoice> findByEmail(String email);
}
