package com.book.controller.user;

import com.book.entity.Book;
import com.book.entity.Comment;
import com.book.entity.User;
import com.book.repository.BookRepository;
import com.book.repository.CommentRepository;
import com.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Controller
public class CommenController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = {"/commentBook"})
    public String home(Model model, @RequestParam("bookid") Long id, Comment comment, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username);
        comment.setUser(user);
        comment.setCreatedDate(new Date(System.currentTimeMillis()));
        Book book = bookRepository.findById(id).get();
        comment.setBook(book);
        commentRepository.save(comment);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}
