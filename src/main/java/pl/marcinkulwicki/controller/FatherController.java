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
import javax.servlet.http.HttpSession;
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
    @Autowired
    HttpSession sess;

    @GetMapping
    public String addFather(Model model){

        model.addAttribute("father", new FatherDTO());
        return "father/form";
    }
    @PostMapping
    public String addFather(@Valid FatherDTO fatherDTO, BindingResult bindingResult){

        System.out.println("");
            if(!bindingResult.hasErrors()){
                System.out.println("");
                if(fatherService.checkDateAndPesel(fatherDTO)){
                    sess.setAttribute("father", fatherDTO);
                    sess.setAttribute("children" , null);
                    return "redirect:/child";
                }

            }else{
                System.out.println("Incorrect data (Father)");
            }
        return "redirect:/father";
    }
}
