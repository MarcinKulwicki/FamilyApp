package pl.marcinkulwicki.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.service.FamilyService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/family")
public class FamilyController {

    @Autowired
    HttpSession sess;
    @Autowired
    FamilyService familyService;

    @GetMapping
    public String showFamily(Model model){

        model.addAttribute("father", sess.getAttribute("father"));
        model.addAttribute("children", sess.getAttribute("children"));
        return "family/list";
    }

    @GetMapping("/add")
    public String addFamily(Model model){

        List<ChildDTO> children = (List<ChildDTO>) sess.getAttribute("children");
        FatherDTO fatherDTO = (FatherDTO) sess.getAttribute("father");
        familyService.addFamily(fatherDTO, children);

        return "redirect:/family";
    }
}
