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
    @Autowired
    ChildRepository childRepository;


    public void addFamily(FatherDTO fatherDTO, List<ChildDTO> childs) {

        //CREATE FAMILY
        Family family = new Family();

        //ADD FATHER TO FAMILY
        addFatherToFamily(family,fatherDTO);

        //ADD ALL CHILD TO FAMILY
        Father father = fatherRepository.findFirstByPesel(fatherDTO.getPesel());
        Iterator<ChildDTO> it = childs.iterator();
        while(it.hasNext()){
            addChildToFamily(familyRepository.findFirstByFatherId(father.getId()), it.next());
        }
    }

    private void createFamily(Family family){
        familyRepository.save(family);
    }

    private void addFatherToFamily(Family family , FatherDTO fatherDTO){
        //addFatherToDb
        fatherService.addFather(fatherDTO);
        //Find this Father
        family.setFather(
                fatherRepository.findFirstByPesel(fatherDTO.getPesel())
        );
        //addFamilyToDb
        createFamily(family);
    }

    private void addChildToFamily(Family family, ChildDTO childDTO) {

        //Search in base, if this child is alredy exsist
        Child child = childRepository.findFirstByPesel(childDTO.getPesel());
        if(child == null){
            //if not exist we can add to Db
            child = childService.toChild(childDTO);
            child.setFamily(family);
            childRepository.save(child);
        }
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
