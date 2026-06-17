package Warehousemanagement.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {
    @RequestMapping("/")
    @RequestBody
    public String hello(){
        return "Hello Nigga how are you today";
    }

}
