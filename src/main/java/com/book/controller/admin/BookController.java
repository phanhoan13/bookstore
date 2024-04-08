package com.book.controller.admin;

import com.book.config.CloudinaryService;
import com.book.entity.Book;
import com.book.entity.User;
import com.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @RequestMapping(value = {"/addform"}, method = RequestMethod.GET)
    public String addForm(Model model, @RequestParam(value = "id", required = false) String id) {
        model.addAttribute("book", new Book());
        model.addAttribute("btn", "thêm");
        if(id != null){
            model.addAttribute("book", bookRepository.findById(Long.valueOf(id)).get());
            model.addAttribute("btn", "sửa");
        }
        return "addform";
    }

    @GetMapping("/books")
    public String listBooks(Model model) {
        List<Book> listBooks = bookRepository.findAll();
        model.addAttribute("listBooks", listBooks);
        return "books";
    }


    @GetMapping("/deletebook")
    public String deletebook(@RequestParam("id") Long id) {
        bookRepository.deleteById(id);
        return "redirect:books";
    }

    @PostMapping("/addBooks")
    public String addBook(Model model, @RequestPart("image") MultipartFile filePart,Book book) {
        String image = "";
        if(filePart.isEmpty() == false) {
            image = cloudinaryService.uploadFile(filePart);
        }
        if(book.getId() == null){
            book.setAnh(image);
        }
        else{
            if(image.equals("")){
                Book b = bookRepository.findById(book.getId()).get();
                book.setAnh(b.getAnh());
            }
            else{
                book.setAnh(image);
            }
        }
        bookRepository.save(book);
        return "redirect:books";
    }
}
