package pl.marcinkulwicki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.service.ChildService;
import pl.marcinkulwicki.service.FamilyService;
import pl.marcinkulwicki.service.FatherService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/rest")
@CrossOrigin(origins = "http://localhost:4200")
public class restFamilyController {

    @Autowired
    HttpSession sess;
    @Autowired
    FamilyService familyService;
    @Autowired
    FatherService fatherService;
    @Autowired
    ChildService childService;


    @PostMapping("/familyAdd")
    public void addFamily(@RequestBody FatherDTO fatherDTO, @RequestBody List<ChildDTO> childList) {


        if (fatherService.checkFather(fatherDTO)) {
            if (childService.checkAllChild(childList, fatherDTO)) {
                familyService.addFamily(fatherDTO, childList);
            } else {
                throw new Error("Child Details are incorrect");
            }
        } else {
            throw new Error("Father Details are incorrect");
        }

    }
}
