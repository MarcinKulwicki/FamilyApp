package pl.marcinkulwicki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.DTO.FamilyDTO;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.entity.Child;
import pl.marcinkulwicki.entity.Family;
import pl.marcinkulwicki.entity.Father;
import pl.marcinkulwicki.repository.ChildRepository;
import pl.marcinkulwicki.repository.FamilyRepository;
import pl.marcinkulwicki.repository.FatherRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FamilyService {

    @Autowired
    ChildService childService;
    @Autowired
    FatherService fatherService;
    @Autowired
    FamilyRepository familyRepository;
    @Autowired
    FatherRepository fatherRepository;


    public void addFamily(FatherDTO fatherDTO, List<ChildDTO> childs) {

        //CREATE FAMILY
        Family family = createFamily();
        //ADD FATHER TO FAMILY
        fatherService.addFather(fatherDTO);
        Father father = fatherRepository.findFirstByPesel(fatherDTO.getPESEL());
        if(father == null){
            father = new Father();
        }
        family = addFatherToFamily(family,father);

        //ADD ALL CHILD TO FAMILY
        Iterator<ChildDTO> it = childs.iterator();
        while(it.hasNext()){
            family = addChildToFamily(family, it.next());
        }
        // SAVE to db
        fatherService.addFather(fatherDTO);
        Family family2 = familyRepository.findFirstByFatherId(family.getFather().getId());
        if(family2 == null){
            familyRepository.save(family);
        }else {
            familyRepository.save(family2);
        }
        //TODO jest tutaj dosc mocno namieszane najlepiej bedzie to napisac od nowa

    }

    private Family createFamily(){
        Family family = new Family();
        return family;
    }

    private Family addFatherToFamily(Family family , Father father){
        family.setFather(father);
        return family;
    }

    private Family addChildToFamily(Family family, ChildDTO childDTO) {

        List<Child> children = family.getChildren();
        if(children == null){
            children = new ArrayList<>();
        }

        Child child = childService.toChild(childDTO);
        children.add(child);

        family.setChildren(children);
        return family;
    }

    public FamilyDTO toFamilyDTO(Family family){
        FamilyDTO familyDTO = new FamilyDTO();
        familyDTO.setId(family.getId());
        familyDTO.setFatherDTO(fatherService.toFatherDTO(family.getFather()));

        return familyDTO;
    }

    public Family toFamily(FamilyDTO familyDTO){
        Family family = new Family();
        family.setId(familyDTO.getId());
        family.setFather(fatherService.toFather(familyDTO.getFatherDTO()));

        return family;
    }


}
