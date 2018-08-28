package pl.marcinkulwicki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.entity.Child;
import pl.marcinkulwicki.repository.ChildRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChildService {

    @Autowired
    ChildRepository childRepository;
    @Autowired
    HttpSession sess;

    public boolean addChild(ChildDTO childDTO) {

        List<ChildDTO> children = (List<ChildDTO>) sess.getAttribute("children");
        if(children == null){
            children = new ArrayList<>();
        }
        children.add(childDTO);
        sess.setAttribute("children", children);


        Child child = toChild(childDTO);
        childRepository.save(child);
        return true;
    }

    private Child toChild(ChildDTO childDTO) {
        Child child = new Child();

        child.setFirstName(childDTO.getFirstName());
        child.setSecondName(childDTO.getSecondName());
        child.setPESEL(childDTO.getPESEL());
        child.setSex(childDTO.getSex());

        return child;
    }
}
