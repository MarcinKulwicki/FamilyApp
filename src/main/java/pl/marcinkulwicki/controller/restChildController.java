package pl.marcinkulwicki.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.entity.Child;
import pl.marcinkulwicki.service.ChildService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest")
@CrossOrigin(origins = "http://localhost:4200")
public class restChildController {

    @Autowired
    ChildService childService;

    @GetMapping
    public List<ChildDTO> getChilds(){
        return childService.childs();
    }

    @PostMapping("/child")
    public void addChild(@RequestBody ChildDTO childDTO){

            if(childService.checkPeselInDb(childDTO) && childDTO.getFirstName() == null){
                childService.addChildToList(childDTO);
            }else {
                System.out.println("Children exist in Db (ChildController.addChild())");
                throw new Error("Pesel and date invalid");
            }
    }
}
