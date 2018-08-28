package pl.marcinkulwicki.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.entity.Child;

import javax.validation.Valid;

@Controller
@RequestMapping("/child")
public class ChildController {



    @GetMapping
    public String addChild(Model model){
        ChildDTO child = new ChildDTO();
        model.addAttribute("sexMap", child.getSexMap());
        model.addAttribute("child", child);
        return "child/form";
    }

    @PostMapping
    public void addChild(@Valid ChildDTO childDTO, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){


        }

        System.out.println("");
    }

}
