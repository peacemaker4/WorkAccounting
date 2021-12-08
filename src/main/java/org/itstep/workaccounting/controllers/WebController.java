package org.itstep.workaccounting.controllers;

import org.itstep.workaccounting.entities.DbUser;
import org.itstep.workaccounting.services.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    private final UserService userService;

    public WebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "login")
    public String loginPage(){
        return "login";
    }

    @GetMapping(value = "/")
    public String indexPage(Model model){
        model.addAttribute("user", getUser());

        return "index";
    }

    private DbUser getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User securityUser = (User) authentication.getPrincipal();
            DbUser user = userService.getUser(securityUser.getUsername());
            return user;
        }
        return null;
    }


}

