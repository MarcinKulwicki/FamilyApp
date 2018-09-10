package pl.marcinkulwicki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.entity.Child;
import pl.marcinkulwicki.repository.ChildRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
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
        while (it.hasNext()) {
            if (!addChild(it.next())) {
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
        if (children == null) {
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

    private ChildDTO toChildDTO(Child child) {
        ChildDTO childDTO = new ChildDTO();

        childDTO.setFirstName(child.getFirstName());
        childDTO.setSecondName(child.getSecondName());
        childDTO.setPesel(child.getPesel());
        childDTO.setSex(child.getSex());
        childDTO.setFamilyDTO(familyService.toFamilyDTO(child.getFamily()));

        return childDTO;
    }

    public List<ChildDTO> toChildListDTO(List<Child> childs) {

        List<ChildDTO> childsDTO = new ArrayList<>();

        Iterator<Child> it = childs.iterator();
        while (it.hasNext()) {
            childsDTO.add(toChildDTO(it.next()));
        }

        return childsDTO;
    }

    public boolean checkPeselInDb(ChildDTO childDTO) {
        Child child = childRepository.findFirstByPesel(childDTO.getPesel());
        if (child == null) {
            return true;
        }
        return false;
    }

    public List<ChildDTO> childs() {
        return toChildListDTO(childRepository.findAll());
    }

    public boolean checkAllChild(List<ChildDTO> childList, FatherDTO fatherDTO) {

        //Test for List
        if (!checkAllPesel(childList, fatherDTO)) return false;

        Iterator<ChildDTO> it = childList.iterator();
        while (it.hasNext()) {
            ChildDTO childDTO = it.next();

            //Test for Child
            if (!checkMoreThanTwoLetter(childDTO)) return false;
            if (!checkPeselIsNumber(childDTO)) return false;
            if (!checkPeselInDb(childDTO)) return false;
        }


        return true;
    }

    private boolean checkPeselIsNumber(ChildDTO childDTO) {
        try {
            Double.parseDouble(childDTO.getPesel());
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean checkAllPesel(List<ChildDTO> childList, FatherDTO fatherDTO) {

        if (childList.toArray().length < 2) return true; //if child list have under 2 elements, dont check

        for (int i = 0; i < childList.toArray().length - 1; i++) { //from 0 elem to (last elem)-1
            for (int j = i + 1; j < childList.toArray().length; j++) {
                if (childList.get(i).getPesel().equals(
                        childList.get(j).getPesel()
                )) return false;
                //child cannot have the same pesel as Father
            }
        }
        Iterator<ChildDTO> it = childList.iterator();
        while (it.hasNext()) {
            if (it.next().getPesel().equals(fatherDTO.getPesel())) return false;
        }
        return true;
    }

    private boolean checkMoreThanTwoLetter(ChildDTO childDTO) {

        if (childDTO.getFirstName() != null && childDTO.getSecondName() != null) {
            if (childDTO.getFirstName().length() > 2 && childDTO.getSecondName().length() > 2) {
                return true;
            }
        }
        return false;
    }
}
