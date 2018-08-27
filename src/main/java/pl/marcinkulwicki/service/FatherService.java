package pl.marcinkulwicki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.entity.Father;
import pl.marcinkulwicki.repository.FatherRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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

    private boolean checkDateAndPesel(FatherDTO fatherDTO) {
        String date =fatherDTO.getDate().toPattern();
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
        return false;

    }

    private Father toFather(FatherDTO fatherDTO) {

        Father father = new Father();
        father.setFirstName(fatherDTO.getFirstName());
        father.setSecondName(fatherDTO.getSecondName());

        SimpleDateFormat simpleDateFormat = fatherDTO.getDate();
        Date date = new Date();
        try {
            father.setDate(fatherDTO.getDate().parse(fatherDTO.getDate().toPattern()));
        }catch (ParseException e){
            e.getErrorOffset();
            System.out.println("Problem with parsing Date from FatherDTO to Father, in FatherService.toFather");
        }
        //TODO change logic, all must be Data, i can convert Data to string, using http://www.codebind.com/java-tutorials/java-example-convert-date-string/


        father.setPESEL(fatherDTO.getPESEL());

        return father;
    }
}
