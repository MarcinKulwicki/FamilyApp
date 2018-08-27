package pl.marcinkulwicki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.entity.Father;
import pl.marcinkulwicki.repository.FatherRepository;

@Component
public class FatherService {

    @Autowired
    FatherRepository fatherRepository;

    public void addFather(FatherDTO fatherDTO) {

        Father father = toFather(fatherDTO);
        fatherRepository.save(father);
    }

    private Father toFather(FatherDTO fatherDTO) {

        Father father = new Father();
        father.setFirstName(fatherDTO.getFirstName());
        father.setSecondName(fatherDTO.getSecondName());
        father.setDate(fatherDTO.getDate());
        father.setPESEL(fatherDTO.getPESEL());

        return father;
    }
}
