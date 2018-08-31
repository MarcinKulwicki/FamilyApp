package pl.marcinkulwicki.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.entity.Child;
import pl.marcinkulwicki.service.FamilyService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    @GetMapping("/search")
    public String searchFamily(Model model){

        ChildDTO child = new ChildDTO();

        model.addAttribute("childsFind", sess.getAttribute("childsFind"));
        model.addAttribute("sexMap", child.getSexMap());
        model.addAttribute("child", child);
        return "family/search";
    }
    @PostMapping("/search")
    public String searchFamily(@ModelAttribute ChildDTO childDTO){

        List<ChildDTO> childs = familyService.searchChild(childDTO);
        sess.setAttribute("childsFind", childs);

        return "redirect:/family/search";
    }
}
