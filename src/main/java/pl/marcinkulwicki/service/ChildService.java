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
        child.setPesel(childDTO.getPesel());
        child.setSex(childDTO.getSex());
//        child.setFamily(familyService.toFamily(childDTO.getFamilyDTO()));
        return child;
    }
    private ChildDTO toChildDTO(Child child){
        ChildDTO childDTO = new ChildDTO();

        childDTO.setFirstName(child.getFirstName());
        childDTO.setSecondName(child.getSecondName());
        childDTO.setPesel(child.getPesel());
        childDTO.setSex(child.getSex());
        childDTO.setFamilyDTO(familyService.toFamilyDTO(child.getFamily()));

        return childDTO;
    }

    public List<ChildDTO> toChildListDTO(List<Child> childs){

        List<ChildDTO> childsDTO = new ArrayList<>();

        Iterator<Child> it = childs.iterator();
        while (it.hasNext()){
            childsDTO.add(toChildDTO(it.next()));
        }

        return childsDTO;
    }

    public boolean checkPeselInDb(ChildDTO childDTO){

        if(childRepository.findFirstByPesel(childDTO.getPesel()) == null ){
            return true;
        }
        return false;
    }

}
