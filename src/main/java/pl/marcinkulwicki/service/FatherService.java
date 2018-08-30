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
        fatherRepository.save(toFather(fatherDTO));
        return true;
    }

    public boolean checkDateAndPesel(FatherDTO fatherDTO) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        String date = dateFormat.format(fatherDTO.getDate());
        String pesel = fatherDTO.getPesel();
        if(pesel.charAt(2)=='0' || pesel.charAt(2)=='1' ){
            pesel = pesel.substring(4,6)+"-"+pesel.substring(2,4)+"-19"+pesel.substring(0,2);
        }else{
            Integer a = Integer.parseInt(pesel.charAt(2)+"");
            a = a - 2;
            pesel = pesel.substring(4,6)+"-"+a+pesel.charAt(3)+"-20"+pesel.substring(0,2);
        }
        if(date.compareTo(pesel) == 0){
            if(checkPeselInDb(fatherDTO)){
                return true;
            }else {

                System.out.println("multiple pesel in dB (FatherService.checkDateAndPesel -> checkPeselInDb)");
            }
        }
        System.out.println("PESEL and DateBirth not this same (FatherService.checkDateAndPesel)");
        return false;
    }

    public Father toFather(FatherDTO fatherDTO) {

        Father father = new Father();
        father.setFirstName(fatherDTO.getFirstName());
        father.setSecondName(fatherDTO.getSecondName());
        father.setDate(fatherDTO.getDate());
        father.setPesel(fatherDTO.getPesel());

        return father;
    }

    public FatherDTO toFatherDTO(Father father) {

        FatherDTO fatherDTO = new FatherDTO();
        fatherDTO.setFirstName(father.getFirstName());
        fatherDTO.setSecondName(father.getSecondName());
        fatherDTO.setDate(father.getDate());
        fatherDTO.setPesel(father.getPesel());

        return fatherDTO;
    }

    private boolean checkPeselInDb(FatherDTO fatherDTO){

        if(fatherRepository.findFirstByPesel(fatherDTO.getPesel()) == null ){
            return true;
        }
        return false;
    }
}
