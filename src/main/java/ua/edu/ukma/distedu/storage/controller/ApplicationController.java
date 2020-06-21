package ua.edu.ukma.distedu.storage.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.edu.ukma.distedu.storage.service.GroupService;
import ua.edu.ukma.distedu.storage.service.ProductService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ApplicationController {

//    private final ProductService productService;
//    private final GroupService groupService;

//    @Autowired
//    public ApplicationController(ProductService productService, GroupService groupService) {
//        this.productService = productService;
//        this.groupService = groupService;
//    }

    @GetMapping("/")
    public String main(Model model) {
        return "index";
    }

    @GetMapping("/test")
    public String test(Model model) {
        return "test";
    }

}