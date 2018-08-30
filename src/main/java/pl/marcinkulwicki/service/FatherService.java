package pl.marcinkulwicki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marcinkulwicki.DTO.FamilyDTO;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.entity.Father;
import pl.marcinkulwicki.repository.FatherRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FatherService {

    @Autowired
    FatherRepository fatherRepository;

    public boolean addFather(FatherDTO fatherDTO) {

        if(!checkDateAndPesel(fatherDTO)){
            return false;
        }
        Father father = toFather(fatherDTO);
        fatherRepository.save(father);
        return true;
    }

    public boolean checkDateAndPesel(FatherDTO fatherDTO) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        String date = dateFormat.format(fatherDTO.getDate());
        String pesel = fatherDTO.getPESEL();
        if(pesel.charAt(2)=='0' || pesel.charAt(2)=='1' ){
            pesel = pesel.substring(4,6)+"-"+pesel.substring(2,4)+"-19"+pesel.substring(0,2);
        }else{
            Integer a = Integer.parseInt(pesel.charAt(2)+"");
            a = a - 2;
            pesel = pesel.substring(4,6)+"-"+a+pesel.charAt(3)+"-20"+pesel.substring(0,2);
        }

        if(date.compareTo(pesel) == 0){
            return true;
        }
        System.out.println("PESEL and DateBirth not this same (FatherService.checkDateAndPesel)");
        return false;

    }

    public Father toFather(FatherDTO fatherDTO) {

        Father father = new Father();
        father.setFirstName(fatherDTO.getFirstName());
        father.setSecondName(fatherDTO.getSecondName());
        father.setDate(fatherDTO.getDate());
        father.setPesel(fatherDTO.getPESEL());

        return father;
    }

    public FatherDTO toFatherDTO(Father father) {

        FatherDTO fatherDTO = new FatherDTO();
        fatherDTO.setFirstName(father.getFirstName());
        fatherDTO.setSecondName(father.getSecondName());
        fatherDTO.setDate(father.getDate());
        fatherDTO.setPESEL(father.getPesel());

        return fatherDTO;
    }
}
