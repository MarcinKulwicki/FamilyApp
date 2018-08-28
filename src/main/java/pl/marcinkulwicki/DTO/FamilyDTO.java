package pl.marcinkulwicki.DTO;


import java.util.List;

public class FamilyDTO {

    private Long id;
    private FatherDTO fatherDTO;
    private List<ChildDTO> childrenDTO;

    public FamilyDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FatherDTO getFatherDTO() {
        return fatherDTO;
    }

    public void setFatherDTO(FatherDTO fatherDTO) {
        this.fatherDTO = fatherDTO;
    }

    public List<ChildDTO> getChildrenDTO() {
        return childrenDTO;
    }

    public void setChildrenDTO(List<ChildDTO> childrenDTO) {
        this.childrenDTO = childrenDTO;
    }
}
