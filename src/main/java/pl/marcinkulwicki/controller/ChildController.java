package pl.marcinkulwicki.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.marcinkulwicki.DTO.ChildDTO;
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
        model.addAttribute("children", sess.getAttribute("children"));
        model.addAttribute("sexMap", child.getSexMap());
        model.addAttribute("child", child);
        return "child/form";
    }

    //TODO jak juz bedzie dzialac to zapytanie do bazy dopiero jak bedzie zebrana cala rodzina!!
    @PostMapping
    public String addChild(@Valid ChildDTO childDTO, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            if(childService.checkPeselInDb(childDTO)){
                childService.addChildToList(childDTO);
            }else {
                System.out.println("Children exist in Db (ChildController.addChild())");
            }
        }else {
            System.out.println("Children has error in form (ChildController.addChild())");
        }
        return "redirect:/child";
    }

}
