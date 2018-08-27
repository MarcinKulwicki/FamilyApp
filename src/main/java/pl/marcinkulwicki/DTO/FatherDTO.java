package pl.marcinkulwicki.DTO;

import pl.marcinkulwicki.entity.Family;

import java.time.LocalDateTime;
import java.util.List;

public class FatherDTO {

    private Long id;
    private String firstName;
    private String secondName;
    private String PESEL;
    private LocalDateTime date;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<FamilyDTO> getFamiliesDTO() {
        return familiesDTO;
    }

    public void setFamiliesDTO(List<FamilyDTO> familiesDTO) {
        this.familiesDTO = familiesDTO;
    }
}
