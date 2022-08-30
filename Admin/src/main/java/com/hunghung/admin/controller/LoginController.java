package com.hunghung.admin.controller;


import com.hunghung.library.dto.AdminDto;
import com.hunghung.library.model.Admin;
import com.hunghung.library.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private AdminServiceImpl adminService;
    @GetMapping("/login")
    public String LoginForm(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }
    @GetMapping("/forgot-password")
    public String forgetPassword(Model model){
        return "forgot-password";
    }
    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes){
        try {
            if (result.hasErrors()){
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if(admin != null){
                model.addAttribute("adminDto",adminDto);
                redirectAttributes.addFlashAttribute("message","Your email has been registered!");
                System.out.println("admin not null");
                return "register";
            }
            if(adminDto.getPassword().equals(adminDto.getRepeatPassword())){
                adminService.save(adminDto);
                System.out.println("success");
                model.addAttribute("adminDto", adminDto);
                redirectAttributes.addFlashAttribute("message","Register Successfully!");
            }else {
                model.addAttribute("adminDto", adminDto);
                redirectAttributes.addFlashAttribute("message","Password is not same!");
                System.out.println("password not same");
                return "register";
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("message","Error Server!");

        }
        return "register";
    }
}
