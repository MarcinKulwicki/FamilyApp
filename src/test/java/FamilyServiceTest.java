import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.DTO.FamilyDTO;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.entity.Child;
import pl.marcinkulwicki.entity.Family;
import pl.marcinkulwicki.entity.Father;
import pl.marcinkulwicki.repository.ChildRepository;
import pl.marcinkulwicki.repository.FamilyRepository;
import pl.marcinkulwicki.service.ChildService;
import pl.marcinkulwicki.service.FamilyService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FamilyServiceTest {

    FamilyDTO familyDTO;
    ChildDTO childDTO;
    List<Child> childs;
    List<ChildDTO> childsDTO;

    @Mock
    FamilyRepository familyRepositoryMock;
    @Mock
    ChildRepository childRepositoryMock;
    @Mock
    ChildService childMock;

    @InjectMocks
    FamilyService familyImpl;

    @Before
    public void beforeFun() {
        familyDTO = new FamilyDTO();
        familyDTO.setFatherDTO(new FatherDTO());

        childDTO = new ChildDTO("Adam", "Potomski", "92112902576", "Man");
        childDTO.setDate("1992/11/29");


        childs = new ArrayList<>();
        childs.add(new Child("Ala", "Potocki", "81051555824", "Woman"));
        childs.add(new Child("Tosia", "Potocki", "82061132399", "Woman"));
        childs.add(new Child("Andrzej", "Potocki", "79041875582", "Man"));

        childsDTO = new ArrayList<>();
        childsDTO.add(new ChildDTO("Ala", "Potocki", "81051555824", "Woman"));
        childsDTO.add(new ChildDTO("Tosia", "Potocki", "82061132399", "Woman"));
        childsDTO.add(new ChildDTO("Andrzej", "Potocki", "79041875582", "Man"));

    }

    //Check if second parameter is correct
    @Test
    public void testAddChildToFamily() {

        FamilyService familyService = mock(FamilyService.class);
        ArgumentCaptor<ChildDTO> valueCapture = ArgumentCaptor.forClass(ChildDTO.class);

        doNothing().when(familyService).addChildToFamily(any(Family.class), valueCapture.capture());

        Family family = new Family();
        family.setFather(new Father("Andrzej", "Potocki", "92112902576", new Date(92,10,29)));
        ChildDTO childDTOTest = new ChildDTO("Andrzej", "Potocki", "79041875582", "Man");
        when(childRepositoryMock.findFirstByPesel("92112902576")).thenReturn(null);



        familyService.addChildToFamily(new Family(), this.childDTO);
        assertEquals("Adam", valueCapture.getValue().getFirstName());
        assertEquals("Potomski", valueCapture.getValue().getSecondName());
        assertEquals("92112902576", valueCapture.getValue().getPesel());
        assertEquals("Man", valueCapture.getValue().getSex());


        ChildRepository childRepository= mock(ChildRepository.class);
        verify(childRepository, times(1)).save(any(Child.class));

    }

    @Test
    public void testSearchChild() {

        when(childRepositoryMock.findAllBySearchParameters("%","Potocki","%","%","%")).thenReturn(this.childs);
        when(childMock.toChildListDTO(childs)).thenReturn(this.childsDTO);

        ChildDTO childTest = new ChildDTO("a","Potocki","2", "a");
        assertEquals(3, familyImpl.searchChild(childTest).size());


        childs = new ArrayList<>();
        childs.add(new Child("Ala", "Potocki", "81051555824", "Woman"));
        childs.add(new Child("Tosia", "Potocki", "82061132399", "Woman"));

        childsDTO = new ArrayList<>();
        childsDTO.add(new ChildDTO("Ala", "Potocki", "81051555824", "Woman"));
        childsDTO.add(new ChildDTO("Tosia", "Potocki", "82061132399", "Woman"));

        when(childRepositoryMock.findAllBySearchParameters("%","%","Woman","%","%")).thenReturn(childs);
        when(childMock.toChildListDTO(childs)).thenReturn(childsDTO);

        ChildDTO childTest2 = new ChildDTO("a","ds","2", "Woman");
        assertEquals(2, familyImpl.searchChild(childTest2).size());


        childs = new ArrayList<>();
        childs.add(new Child("Ala", "Potocki", "81051555824", "Woman"));

        childsDTO = new ArrayList<>();
        childsDTO.add(new ChildDTO("Ala", "Potocki", "81051555824", "Woman"));

        when(childRepositoryMock.findAllBySearchParameters("Ala","%","Woman","%","%")).thenReturn(childs);
        when(childMock.toChildListDTO(childs)).thenReturn(childsDTO);

        ChildDTO childTest3 = new ChildDTO("Ala","ds","2", "Woman");
        assertEquals(1, familyImpl.searchChild(childTest3).size());
    }

    @Test
    public void testConvertChildDTOToQuery() {

        assertEquals("Adam", familyImpl.convertChildDTOToQuery(this.childDTO).getFirstName());
        assertEquals("Potomski", familyImpl.convertChildDTOToQuery(this.childDTO).getSecondName());
        assertEquals("92112902576", familyImpl.convertChildDTOToQuery(this.childDTO).getPesel());
        assertEquals("Man", familyImpl.convertChildDTOToQuery(this.childDTO).getSex());
        this.childDTO.setDate("1992/11/29");
        assertEquals("921129%", familyImpl.convertChildDTOToQuery(this.childDTO).getDate());

        this.childDTO.setFirstName("Ada");
        assertEquals("Ada", familyImpl.convertChildDTOToQuery(this.childDTO).getFirstName());
        this.childDTO.setFirstName("Ad");
        assertEquals("%", familyImpl.convertChildDTOToQuery(this.childDTO).getFirstName());
        this.childDTO.setFirstName(null);
        assertEquals("%", familyImpl.convertChildDTOToQuery(this.childDTO).getFirstName());

        this.childDTO.setSecondName("Pot");
        assertEquals("Pot", familyImpl.convertChildDTOToQuery(this.childDTO).getSecondName());
        this.childDTO.setSecondName("Po");
        assertEquals("%", familyImpl.convertChildDTOToQuery(this.childDTO).getSecondName());
        this.childDTO.setSecondName(null);
        assertEquals("%", familyImpl.convertChildDTOToQuery(this.childDTO).getSecondName());

        this.childDTO.setPesel("921129");
        assertEquals("921129", familyImpl.convertChildDTOToQuery(this.childDTO).getPesel());
        this.childDTO.setPesel("92112");
        assertEquals("%", familyImpl.convertChildDTOToQuery(this.childDTO).getPesel());
        this.childDTO.setPesel(null);
        assertEquals("%", familyImpl.convertChildDTOToQuery(this.childDTO).getPesel());

        this.childDTO.setSex("Man");
        assertEquals("Man", familyImpl.convertChildDTOToQuery(this.childDTO).getSex());
        this.childDTO.setSex("Ma");
        assertEquals("%", familyImpl.convertChildDTOToQuery(this.childDTO).getSex());
        this.childDTO.setSex(null);
        assertEquals("%", familyImpl.convertChildDTOToQuery(this.childDTO).getSex());
    }

    @Test
    public void testConvertDateToPesel() {

        assertEquals("921129%",familyImpl.convertDateToPesel("1992/11/29"));
        assertEquals("023120%", familyImpl.convertDateToPesel("2002/11/20"));
        assertNotEquals("023320%", familyImpl.convertDateToPesel("2002/13/20"));
        assertNotEquals("023131%", familyImpl.convertDateToPesel("2002/11/31"));
        assertEquals("%", familyImpl.convertDateToPesel(null));
    }
}
