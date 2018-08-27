package pl.marcinkulwicki.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.marcinkulwicki.DTO.FatherDTO;

@Controller
@RequestMapping("/father")
public class FatherController {

    @GetMapping
    public String addFather(Model model){

        model.addAttribute("father", new FatherDTO());
        return "father/form";
    }
}
