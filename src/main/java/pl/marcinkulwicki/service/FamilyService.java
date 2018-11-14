package pl.marcinkulwicki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.DTO.FamilyDTO;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.entity.Child;
import pl.marcinkulwicki.entity.Family;
import pl.marcinkulwicki.entity.Father;
import pl.marcinkulwicki.helpers.DateMatcher;
import pl.marcinkulwicki.helpers.GregorianDateMatcher;
import pl.marcinkulwicki.repository.ChildRepository;
import pl.marcinkulwicki.repository.FamilyRepository;
import pl.marcinkulwicki.repository.FatherRepository;

import javax.servlet.http.HttpSession;
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
    @Autowired
    HttpSession sess;

    //TODO Check it
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

    //TODO
    private void createFamily(Family family){
        familyRepository.save(family);
    }

    //TODO
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

    //TODO invoked test
    public void addChildToFamily(Family family, ChildDTO childDTO) {

        //Search in base, if this child is alredy exsist
        Child child = childRepository.findFirstByPesel(childDTO.getPesel());
        if(child == null){
            //if not exist we can add to Db
            child = childService.toChild(childDTO);
            child.setFamily(family);
            childRepository.save(child);
        }
    }

    //JAVA WORKS
    public FamilyDTO toFamilyDTO(Family family){
        FamilyDTO familyDTO = new FamilyDTO();
        familyDTO.setId(family.getId());
        //familyDTO.setFatherDTO(fatherService.toFatherDTO(family.getFather()));

        return familyDTO;
    }

    //JAVA WORKS
    public Family toFamily(FamilyDTO familyDTO){
        Family family = new Family();
        family.setId(familyDTO.getId());
        family.setFather(fatherService.toFather(familyDTO.getFatherDTO()));

        return family;
    }


    //Tested
    public List<ChildDTO> searchChild(ChildDTO childDTO) {

        childDTO = convertChildDTOToQuery(childDTO);

        List<Child> childs = childRepository.findAllBySearchParameters(
                childDTO.getFirstName(),
                childDTO.getSecondName(),
                childDTO.getSex(),
                childDTO.getPesel(),
                childDTO.getDate()
        );

        System.out.println("");


        List<ChildDTO> childsDTO = childService.toChildListDTO(childs);
        return childsDTO;
    }

    //Tested
    public ChildDTO convertChildDTOToQuery(ChildDTO childQuery) {

        //FirstName
        if(childQuery.getFirstName() == null || childQuery.getFirstName().length() < 3){
            childQuery.setFirstName("%");
        }
        //SecondName
        if(childQuery.getSecondName() == null || childQuery.getSecondName().length() < 3){
            childQuery.setSecondName("%");
        }
        //Sex
        if(childQuery.getSex() == null || childQuery.getSex().length() < 3){
            childQuery.setSex("%");
        }
        //Pesel
        if(childQuery.getPesel() == null || childQuery.getPesel().length() < 6){
            childQuery.setPesel("%");
        }
        //Date
        childQuery.setDate(convertDateToPesel(childQuery.getDate()));

        return childQuery;
    }

    //Tested
    public String convertDateToPesel(String date) {

        DateMatcher dateMatcher = new GregorianDateMatcher();

        //For null
        if(date == null) date = "%";
        //Incorrect date format
        if(!dateMatcher.matches(date)){ date = "%";}
        else {
            //Format is correct
            int yy = Integer.parseInt(date.split("/")[0]);
            int MM = Integer.parseInt(date.split("/")[1]);
            int dd = Integer.parseInt(date.split("/")[2]);

            if(yy>1999) MM += 20;

            date = (yy+""+MM+""+dd+"%").substring(2);

            System.out.println("");
        }

        return date;
    }

    //TODO Check it in Last
    public void readFamily(Long id) {

        Family family = familyRepository.findFirstById(id);
        FatherDTO fatherDTO = fatherService.toFatherDTO(
                fatherRepository.findFirstById(family.getFather().getId())
        );
        List<ChildDTO> childs = childService.toChildListDTO(
                childRepository.findAllByFamilyId(id)
        );
        System.out.println("");
        sess.setAttribute("father", fatherDTO);
        sess.setAttribute("children", childs);
    }

}
