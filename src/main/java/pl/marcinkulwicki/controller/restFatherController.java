package pl.marcinkulwicki.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.service.FamilyService;
import pl.marcinkulwicki.service.FatherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest")
@CrossOrigin(origins = "http://localhost:4200")
public class restFatherController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    FatherService fatherService;
    @Autowired
    HttpSession sess;
    @Autowired
    FamilyService familyService;

    @PostMapping("/father")
    public void addFather(@RequestBody FatherDTO fatherDTO){

            if(fatherService.checkDateAndPesel(fatherDTO)){
                sess.setAttribute("father", fatherDTO);
                sess.setAttribute("children" , null);
            }else {
                System.out.println("Pesel_and_date_invalid");
                throw new Error("Pesel and date invalid");
            }
    }
}
