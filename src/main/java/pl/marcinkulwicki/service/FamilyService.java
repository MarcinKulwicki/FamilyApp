package pl.marcinkulwicki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.DTO.FamilyDTO;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.entity.Family;
import pl.marcinkulwicki.repository.ChildRepository;
import pl.marcinkulwicki.repository.FamilyRepository;

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
    ChildRepository childRepository;


    public void addFamily(FatherDTO fatherDTO, List<ChildDTO> childs) {

        Family family = createFamily();
        family = addFatherToFamily(family, fatherDTO);

        Iterator<ChildDTO> it = childs.iterator();
        while(it.hasNext()){
            addChildToFamily(family, it.next());
        }
    }

    private void addChildToFamily(Family family, ChildDTO childDTO) {

        childDTO.setFamilyDTO(toFamilyDTO(family));
        childRepository.save(childService.toChild(childDTO));
    }

    private Family createFamily(){
        Family family = new Family();
        familyRepository.save(family);
        return family;
    }

    private Family addFatherToFamily(Family family , FatherDTO fatherDTO){
        fatherService.addFather(fatherDTO);
        family.setFather(fatherService.toFather(fatherDTO));
        familyRepository.save(family);
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
