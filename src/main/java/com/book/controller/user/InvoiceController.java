package com.book.controller.user;

import com.book.dto.BookDto;
import com.book.entity.Book;
import com.book.entity.Invoice;
import com.book.entity.InvoiceDetail;
import com.book.entity.User;
import com.book.repository.InvoiceDetailRepository;
import com.book.repository.InvoiceRepository;
import com.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @PostMapping(value = {"/addinvoice"})
    public String addInvoice(HttpSession session) {
        List<BookDto> list = (List<BookDto>) session.getAttribute("cart");
        if(list == null || list.size() == 0){
            return "redirect:cart";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username);
        Invoice invoice = new Invoice();
        invoice.setCreatedDate(new Date(System.currentTimeMillis()));
        invoice.setUser(user);
        Invoice result = invoiceRepository.save(invoice);

        for(BookDto b : list){
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setInvoice(result);
            invoiceDetail.setBook(b.getBook());
            invoiceDetail.setQuantity(b.getQuantity());
            invoiceDetailRepository.save(invoiceDetail);
        }
        return "redirect:cart";
    }

    @GetMapping(value = {"/deleteinvoice"})
    public String delete(@RequestParam("id") Long id) {
        invoiceRepository.deleteById(id);
        return "redirect:cart";
    }
}
