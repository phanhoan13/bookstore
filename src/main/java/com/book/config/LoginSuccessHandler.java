package com.book.config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

    @Autowired
    private UserRepository userRepository;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        String username = authentication.getName();

        List<String> roles = new ArrayList<>();
        roles.add(userRepository.findByEmail(username).getRole());

        String redirectURL = request.getContextPath();

        if (checkRole("ADMIN", roles)) {
            redirectURL = "admin/books";
        } else if (checkRole("USER", roles)) {
            redirectURL = "/";
        }

        response.sendRedirect(redirectURL);

    }



    public boolean checkRole(String role,List<String> list) {
        for(String s : list) {
            if(s.equals(role)) {
                return true;
            }
        }
        return false;
    }
}
