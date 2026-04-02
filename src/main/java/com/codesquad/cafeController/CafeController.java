package com.codesquad.cafeController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CafeController {


    @GetMapping("/")
    public String mainPage(){

        return "frontPage";
    }

}
