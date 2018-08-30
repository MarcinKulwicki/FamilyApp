package pl.marcinkulwicki.DTO;

import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

public class ChildDTO {

    private Long id;
    @Size(min = 3, max = 20)
    private String firstName;
    @Size(min = 3, max = 30)
    private String secondName;
    @PESEL
    private String pesel;
    private String sex;
    private FamilyDTO familyDTO;

    private Map< String, String > sexMap = new HashMap<>();


    public ChildDTO() {

        sexMap.put("man", "Man");
        sexMap.put("woman", "Woman");
        sexMap.put("other", "Other");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public FamilyDTO getFamilyDTO() {
        return familyDTO;
    }

    public void setFamilyDTO(FamilyDTO familyDTO) {
        this.familyDTO = familyDTO;
    }

    public Map<String, String> getSexMap() {
        return sexMap;
    }

}
