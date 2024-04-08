package com.book.controller.user;

import com.book.entity.User;
import com.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/regis")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "regis";
    }

    @PostMapping("/regisaction")
    public String postRegis(User user,Model model) {
        User u = userRepository.findByEmail(user.getEmail());
        if(u!=null) {
            model.addAttribute("mes","Email already exists!!!");
            model.addAttribute("user", new User());
            return "regis";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("USER");
        userRepository.save(user);
        return "redirect:login";
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("admins");
        System.out.println(encodedPassword);
    }
}


