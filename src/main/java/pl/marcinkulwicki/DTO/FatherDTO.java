package pl.marcinkulwicki.DTO;

import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class FatherDTO {


    private Long id;
    @Size(min = 3, max = 20)
    private String firstName;
    @Size(min = 3, max = 30)
    private String secondName;
    @PESEL
    private String PESEL;
    private SimpleDateFormat date;
    private List<FamilyDTO> familiesDTO;

    public FatherDTO() {
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

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }

    public List<FamilyDTO> getFamiliesDTO() {
        return familiesDTO;
    }

    public void setFamiliesDTO(List<FamilyDTO> familiesDTO) {
        this.familiesDTO = familiesDTO;
    }
}
