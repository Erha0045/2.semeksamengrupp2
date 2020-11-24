package com.eksamengr2.alpha.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class melgaController {

    @GetMapping("edit_project")
    public String editProject(){

        return "edit_project";
    }


}
