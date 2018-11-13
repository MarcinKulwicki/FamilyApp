package pl.marcinkulwicki.DTO;

import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FatherDTO {


    private Long id;
    @Size(min = 3, max = 20)
    private String firstName;
    @Size(min = 3, max = 30)
    private String secondName;
    @PESEL
    private String pesel;
    private Date date;
    private List<FamilyDTO> familiesDTO;

    public FatherDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FatherDTO)) return false;
        FatherDTO fatherDTO = (FatherDTO) o;
        return Objects.equals(getFirstName(), fatherDTO.getFirstName()) &&
                Objects.equals(getSecondName(), fatherDTO.getSecondName()) &&
                Objects.equals(getPesel(), fatherDTO.getPesel()) &&
                Objects.equals(getDate(), fatherDTO.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getSecondName(), getPesel(), getDate());
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<FamilyDTO> getFamiliesDTO() {
        return familiesDTO;
    }

    public void setFamiliesDTO(List<FamilyDTO> familiesDTO) {
        this.familiesDTO = familiesDTO;
    }
}
