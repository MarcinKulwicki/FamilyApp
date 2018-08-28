package pl.marcinkulwicki.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.entity.Child;
import pl.marcinkulwicki.service.ChildService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/child")
public class ChildController {

    @Autowired
    ChildService childService;
    @Autowired
    HttpSession sess;



    @GetMapping
    public String addChild(Model model){
        ChildDTO child = new ChildDTO();
        model.addAttribute("sexMap", child.getSexMap());
        model.addAttribute("child", child);
        return "child/form";
    }

    //TODO jak juz bedzie dzialac to zapytanie do bazy dopiero jak bedzie zebrana cala rodzina!!
    @PostMapping
    public String addChild(@Valid ChildDTO childDTO, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            sess.setAttribute("child", childDTO);
            childService.addChild(childDTO);
            return "redirect:/family";
        }
        System.out.println("Children has error in form");
        return "redirect:/child";
    }

}
