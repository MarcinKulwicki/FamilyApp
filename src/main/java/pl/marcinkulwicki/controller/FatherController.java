package pl.marcinkulwicki.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.service.FatherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping("/father")
public class FatherController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    FatherService fatherService;

    @GetMapping
    public String addFather(Model model){

        model.addAttribute("father", new FatherDTO());
        return "father/form";
    }
    @PostMapping
    public String addFather(@Valid FatherDTO fatherDTO, BindingResult bindingResult){
            if(!bindingResult.hasErrors()){

                fatherService.addFather(fatherDTO);
                return "redirect:/children";
            }else{
                System.out.println("Incorrect data");
            }
        return "redirect:/father";
    }
}
