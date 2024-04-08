package com.book.controller.user;

import com.book.dto.BookDto;
import com.book.entity.Book;
import com.book.entity.Comment;
import com.book.entity.Invoice;
import com.book.repository.BookRepository;
import com.book.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/cart")
    public String addToCart(Model model, HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<BookDto> list = (List<BookDto>) session.getAttribute("cart");
        model.addAttribute("listcart", list);
        if (username != null){
            model.addAttribute("listinvoice", invoiceRepository.findByEmail(username));
        }
        else{
            model.addAttribute("listinvoice", new ArrayList<Invoice>());
        }
        if(list == null){
            model.addAttribute("listcart", new ArrayList<>());
        }
        return "cart";
    }

    @GetMapping("/deletecart")
    public String addToCart(@RequestParam("id") Long id, HttpSession session){
        List<BookDto> list = (List<BookDto>) session.getAttribute("cart");
        for(BookDto b : list){
            if (b.getBook().getId() == id){
                list.remove(b);
                return "redirect:cart";
            }
        }
        return "redirect:cart";
    }

    @PostMapping(value = {"/addToCart"})
    public String addToCart(@RequestParam("id") Long id, @RequestParam("quantity") Integer quantity, HttpSession session) {
        Book book = bookRepository.findById(id).get();
        BookDto bookDto = new BookDto(book,quantity);

        List<BookDto> list = (List<BookDto>) session.getAttribute("cart");
        if(list == null){
            list = new ArrayList<>();
        }
        else{
            for(BookDto b : list){
                if (b.getBook().getId() == id){
                    b.setQuantity(b.getQuantity() + quantity);
                    return "redirect:cart";
                }
            }
        }
        list.add(bookDto);
        session.setAttribute("cart",list);
        return "redirect:cart";
    }
}
