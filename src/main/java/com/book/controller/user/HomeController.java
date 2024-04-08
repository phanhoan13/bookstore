package com.book.controller.user;

import com.book.entity.User;
import com.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value = {"/","trang-chu"})
    public String home(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listBooks",bookRepository.findAll());
        return "index";
    }
}
