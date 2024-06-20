//로그인 전, 후 홈페이지 
package com.hotSix.itemrier_boot.controller.home;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/home")
    public String userHome(Model model) {
        return "home";
    }
}
