package pl.marcinkulwicki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.entity.Father;
import pl.marcinkulwicki.repository.FatherRepository;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class FatherService {

    @Autowired
    FatherRepository fatherRepository;

//    FatherService(){
//        super();
//    }


    //Do Bazy
    public boolean addFather(FatherDTO fatherDTO) {

        if (!checkDateAndPesel(fatherDTO)) {
            return false;
        }
        fatherRepository.save(toFather(fatherDTO));
        return true;
    }

    //Bez bazy
    public boolean checkDateAndPesel(FatherDTO fatherDTO) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if(fatherDTO.getDate() == null) return false;
        String date = dateFormat.format(fatherDTO.getDate());
        String pesel = fatherDTO.getPesel();
        if (pesel.charAt(2) == '0' || pesel.charAt(2) == '1') {
            pesel = pesel.substring(4, 6) + "-" + pesel.substring(2, 4) + "-19" + pesel.substring(0, 2);
        } else {
            Integer a = Integer.parseInt(pesel.charAt(2) + "");
            a = a - 2;
            pesel = pesel.substring(4, 6) + "-" + a + pesel.charAt(3) + "-20" + pesel.substring(0, 2);
        }
        if (date.compareTo(pesel) == 0) {
            if (checkPeselInDb(fatherDTO)) {
                return true;
            } else {

                System.out.println("multiple pesel in dB (FatherService.checkDateAndPesel -> checkPeselInDb)");
            }
        }
        System.out.println("PESEL and DateBirth not this same (FatherService.checkDateAndPesel)");
        return false;
    }

    //Bez bazy
    public Father toFather(FatherDTO fatherDTO) {

        Father father = new Father();
        father.setFirstName(fatherDTO.getFirstName());
        father.setSecondName(fatherDTO.getSecondName());
        father.setDate(fatherDTO.getDate());
        father.setPesel(fatherDTO.getPesel());

        return father;
    }

    //Bez bazy
    public FatherDTO toFatherDTO(Father father) {

        FatherDTO fatherDTO = new FatherDTO();
        fatherDTO.setFirstName(father.getFirstName());
        fatherDTO.setSecondName(father.getSecondName());
        fatherDTO.setDate(father.getDate());
        fatherDTO.setPesel(father.getPesel());

        return fatherDTO;
    }

    //Z baza :D
    public boolean checkPeselInDb(FatherDTO fatherDTO) {

        if (fatherRepository.findFirstByPesel(fatherDTO.getPesel()) == null) {
            return true;
        }
        return false;
    }

    public boolean checkFather(FatherDTO fatherDTO) {

        if (!checkPeselIsNumber(fatherDTO)) return false;
        if (!checkDateAndPesel(fatherDTO)) return false;
        if (!checkMoreThanTwoLetter(fatherDTO)) return false;
        if (!checkPeselInDb(fatherDTO)) return false;

        return true;
    }

    private boolean checkMoreThanTwoLetter(FatherDTO fatherDTO) {
        if (fatherDTO.getFirstName() != null && fatherDTO.getSecondName() != null) {
            if (fatherDTO.getFirstName().length() > 2 && fatherDTO.getSecondName().length() > 2) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPeselIsNumber(FatherDTO fatherDTO) { //TODO check this
        try {
            Double.parseDouble(fatherDTO.getPesel());
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }
}
