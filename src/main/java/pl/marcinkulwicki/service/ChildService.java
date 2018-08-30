package pl.marcinkulwicki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.entity.Child;
import pl.marcinkulwicki.repository.ChildRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ChildService {

    @Autowired
    HttpSession sess;
    @Autowired
    ChildRepository childRepository;
    @Autowired
    FamilyService familyService;

    public boolean addChilds(List<ChildDTO> childs) {

        Iterator<ChildDTO> it = childs.iterator();
        while (it.hasNext()){
            if(!addChild(it.next())){
                System.out.println("Cannot add child (ChildService.addChild())");
                return false;
            }

        }
        return true;
    }

    private boolean addChild(ChildDTO childDTO) {
        Child child = toChild(childDTO);
        childRepository.save(child);
        return true;
    }

    public boolean addChildToList(ChildDTO childDTO) {

        List<ChildDTO> children = (List<ChildDTO>) sess.getAttribute("children");
        if(children == null){
            children = new ArrayList<>();
        }
        children.add(childDTO);
        sess.setAttribute("children", children);
        return true;
    }

    public Child toChild(ChildDTO childDTO) {
        Child child = new Child();

        child.setFirstName(childDTO.getFirstName());
        child.setSecondName(childDTO.getSecondName());
        child.setPESEL(childDTO.getPESEL());
        child.setSex(childDTO.getSex());
        child.setFamily(familyService.toFamily(childDTO.getFamilyDTO()));

        return child;
    }


}
