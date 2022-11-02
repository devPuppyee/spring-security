package com.seolhan.demospringsecurityform.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SampleController {
    @Autowired
    SampleService sampleService;
    // 인증한 사용자만 접근 가능
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("message", "Hello" + principal.getName());
        sampleService.dashboard();
        return "dashboard";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Hello Spring Security");
        return "index";
    }

    // 모든 사용자 접근 가능
    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("message", "Info");
        return "info";
    }

    // 인증한 어드민 권한 사용자만 접근 가능
    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", "Hello Admin, " + principal.getName());
        return "admin";
    }
}
