package com.book.controller.user;

import com.book.entity.Book;
import com.book.entity.Comment;
import com.book.entity.User;
import com.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DetailProductController {
    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value = {"/detail"})
    public String home(Model model, @RequestParam("id") Long id) {

        Book book = bookRepository.findById(id).get();
        model.addAttribute("book", book);
        model.addAttribute("comment", new Comment());
        model.addAttribute("listComment", book.getComments());
        return "detail";
    }
}
