package pl.marcinkulwicki.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.service.FatherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/rest/father")
@CrossOrigin(origins = "http://localhost:4200")
public class restFatherController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    FatherService fatherService;
    @Autowired
    HttpSession sess;

    @PostMapping
    public void addFather(@RequestBody FatherDTO fatherDTO){

        System.out.println(fatherDTO.getFirstName());
        System.out.println(fatherDTO.getSecondName());

            if(fatherService.checkDateAndPesel(fatherDTO)){
                sess.setAttribute("father", fatherDTO);
                sess.setAttribute("children" , null);
                System.out.println("FatherAddedToSess in restFatherController");
                System.out.println(fatherDTO.getPesel());
                System.out.println(fatherDTO.getDate().toString());
            }else {
                System.out.println("Pesel_and_date_invalid");
            }
    }
}
