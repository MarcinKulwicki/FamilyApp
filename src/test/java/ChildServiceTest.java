import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;
//import static org.mockito.Matchers.any;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.marcinkulwicki.DTO.ChildDTO;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.entity.Child;
import pl.marcinkulwicki.repository.ChildRepository;
import pl.marcinkulwicki.service.ChildService;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ChildServiceTest {

    ChildDTO childDTO;
    Child child;
    List<ChildDTO> childsDTO;
    List<Child> childs;
    FatherDTO fatherDTO;

    @Mock
    ChildRepository childRepositoryMock;

    @InjectMocks
    ChildService childImpl;

    @Before
    public void beforeFun() {
        this.fatherDTO = new FatherDTO("Wojtek", "Potocki" , "68101787552");

        this.childDTO = new ChildDTO("Adam", "Potocki", "01290561662", "Man");
        this.child = new Child("Adam", "Potocki", "01290561662", "Man");

        childsDTO = new ArrayList<>();
        this.childsDTO.add(new ChildDTO("Ala", "Potocki", "81051555824"));
        this.childsDTO.add(new ChildDTO("Tosia", "Potocki", "82061132399"));
        this.childsDTO.add(new ChildDTO("Andrzej", "Polecki", "79041875582"));

        childs = new ArrayList<>();
        this.childs.add(new Child("Ala", "Potocki", "81051555824", "Woman"));
        this.childs.add(new Child("Tosia", "Potocki", "82061132399", "Woman"));
        this.childs.add(new Child("Andrzej", "Polecki", "79041875582", "Man"));
    }

    @Test
    public void testAadChild() {
        when(childRepositoryMock.save(any(Child.class))).thenReturn(this.child);

        assertEquals(true, childImpl.addChild(this.childDTO));
        assertNotEquals(true, childImpl.addChild(null));
    }

    @Test
    public void testToChild() {

        assertEquals("Adam", childImpl.toChild(this.childDTO).getFirstName());
        assertEquals("Potocki", childImpl.toChild(this.childDTO).getSecondName());
        assertEquals("01290561662", childImpl.toChild(this.childDTO).getPesel());
        assertEquals("Man", childImpl.toChild(this.childDTO).getSex());

        assertNotEquals("Adaa", childImpl.toChild(this.childDTO).getFirstName());
        assertNotEquals("Potocka", childImpl.toChild(this.childDTO).getSecondName());
        assertNotEquals("0129056166a", childImpl.toChild(this.childDTO).getPesel());
        assertNotEquals("Maa", childImpl.toChild(this.childDTO).getSex());
    }

    @Test
    public void testToChildDTO() {

        assertEquals("Adam", childImpl.toChildDTO(this.child).getFirstName());
        assertEquals("Potocki", childImpl.toChildDTO(this.child).getSecondName());
        assertEquals("01290561662", childImpl.toChildDTO(this.child).getPesel());
        assertEquals("Man", childImpl.toChildDTO(this.child).getSex());

        assertNotEquals("Adaa", childImpl.toChildDTO(this.child).getFirstName());
        assertNotEquals("Potocka", childImpl.toChildDTO(this.child).getSecondName());
        assertNotEquals("0129056166a", childImpl.toChildDTO(this.child).getPesel());
        assertNotEquals("Maa", childImpl.toChildDTO(this.child).getSex());
    }

    @Test
    public void testToChildListDTO() {

        assertEquals(3, childImpl.toChildListDTO(this.childs).size());
    }

    @Test
    public void testCheckPeselInDb() {
        when(childRepositoryMock.findFirstByPesel("01290561662")).thenReturn(this.child);

        assertEquals(false, childImpl.checkPeselInDb(childDTO));
        this.childDTO.setPesel("02290561662");
        assertEquals(true, childImpl.checkPeselInDb(childDTO));
    }

    @Test
    public void testChilds() {
        when(childRepositoryMock.findAll()).thenReturn(this.childs);

        assertEquals(3 , childImpl.childs().size());
    }

    @Test
    public void testCheckAllChild() {

        assertEquals(true, childImpl.checkAllChild(this.childsDTO, this.fatherDTO));

        this.fatherDTO.setPesel("81051555824");
        assertEquals(false, childImpl.checkAllChild(this.childsDTO, this.fatherDTO));
    }

    @Test
    public void testCheckPeselIsNumber() {

        assertEquals(true, childImpl.checkPeselIsNumber(this.childDTO));

        this.childDTO.setPesel("safds");
        assertEquals(false, childImpl.checkPeselIsNumber(this.childDTO));
        assertEquals(false, childImpl.checkPeselIsNumber(null));
    }

    @Test
    public void testCheckAllPesel() {
        assertEquals(true, childImpl.checkAllPesel(this.childsDTO, this.fatherDTO));

        this.childsDTO.add(new ChildDTO("Wojtek", "Jakostam", "52022287776"));
        assertEquals(true, childImpl.checkAllPesel(this.childsDTO, this.fatherDTO));

        this.fatherDTO.setPesel("81051555824");
        assertEquals(false, childImpl.checkAllPesel(this.childsDTO, this.fatherDTO));

        this.childsDTO = null;
        assertEquals(false, childImpl.checkAllPesel(this.childsDTO, this.fatherDTO));

        this.childsDTO = new ArrayList<>();
        this.childsDTO.add(new ChildDTO("Adam", "Poniatowski", "92112902576"));
        assertEquals(true, childImpl.checkAllPesel(this.childsDTO, this.fatherDTO));

        this.childsDTO.add(new ChildDTO("Wojtek", "Nieudasie", "92112902576"));
        assertEquals(false, childImpl.checkAllPesel(this.childsDTO, this.fatherDTO));

    }

    @Test
    public void testCheckMoreThanTwoLetter() {
        assertEquals(true, childImpl.checkMoreThanTwoLetter(this.childDTO));

        this.childDTO.setFirstName(null);
        assertEquals(false, childImpl.checkMoreThanTwoLetter(this.childDTO));

        this.childDTO.setFirstName("aa");
        assertEquals(false, childImpl.checkMoreThanTwoLetter(this.childDTO));

        this.childDTO.setFirstName("Adam");
        this.childDTO.setSecondName(null);
        assertEquals(false, childImpl.checkMoreThanTwoLetter(this.childDTO));

        this.childDTO.setSecondName("as");
        assertEquals(false, childImpl.checkMoreThanTwoLetter(this.childDTO));
    }
}
