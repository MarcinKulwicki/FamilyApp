
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.entity.Father;
import pl.marcinkulwicki.repository.FatherRepository;
import pl.marcinkulwicki.service.FatherService;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class FatherServiceTest {

    private FatherDTO fatherDTO;
    private Father father;

    @Mock
    FatherRepository fatherRepositoryMock;

    @InjectMocks
    FatherService fatherImpl;

    @Before
    public void beforeFun() {
        fatherDTO = new FatherDTO();
        father = new Father("Adam", "Nowak", "92112902576", new Date(92, 10, 29));
    }

    @Test
    public void testAddFather() {

        when(fatherRepositoryMock.save(any(Father.class))).thenReturn(father);

        fatherDTO.setPesel("92112902576");
        assertEquals(false, fatherImpl.addFather(fatherDTO));

        fatherDTO.setPesel("91112902576");
        fatherDTO.setDate(new Date(91, 10, 29));
        assertEquals(true, fatherImpl.addFather(fatherDTO));

        fatherDTO.setPesel("91112902000");
        assertEquals(true, fatherImpl.addFather(fatherDTO));
    }

    @Test
    public void testCheckFather() {
        fatherDTO.setPesel("92112902576");
        fatherDTO.setDate(new Date(92, 10, 29));
        fatherDTO.setFirstName("Ada");
        fatherDTO.setSecondName("Morgan");

        assertEquals(true, fatherImpl.checkFather(fatherDTO));

        fatherDTO.setFirstName("aa");
        assertEquals(false, fatherImpl.checkFather(fatherDTO));

        when(fatherRepositoryMock.findFirstByPesel("92112902576")).thenReturn(new Father("Adam", "Mock", "92112902576"));
        assertEquals(false, fatherImpl.checkFather(fatherDTO));

        fatherDTO.setPesel("dsfsd2123");
        assertEquals(false, fatherImpl.checkFather(fatherDTO));

    }

    @Test
    public void testCheckMoreThanTwoLetter() {
        fatherDTO.setFirstName("Ada");
        fatherDTO.setSecondName("Mon");

        assertEquals(true, fatherImpl.checkMoreThanTwoLetter(fatherDTO));

        fatherDTO.setFirstName("Ad");
        assertEquals(false, fatherImpl.checkMoreThanTwoLetter(fatherDTO));

        fatherDTO.setFirstName("Ada");
        fatherDTO.setSecondName("Ad");
        assertEquals(false, fatherImpl.checkMoreThanTwoLetter(fatherDTO));

        fatherDTO.setSecondName(null);
        assertEquals(false, fatherImpl.checkMoreThanTwoLetter(fatherDTO));
    }

    @Test
    public void testCheckPeselIsNumber() {

        fatherDTO.setPesel("234234324");
        assertEquals(true, fatherImpl.checkPeselIsNumber(fatherDTO));

        fatherDTO.setPesel("sdf213");
        assertEquals(false, fatherImpl.checkPeselIsNumber(fatherDTO));

        fatherDTO.setPesel(null);
        assertEquals(false, fatherImpl.checkPeselIsNumber(fatherDTO));
    }

    @Test
    public void testToFatherDTO() {
        fatherDTO.setFirstName("Adam");
        fatherDTO.setSecondName("Nowak");
        fatherDTO.setPesel("92112902576");
        fatherDTO.setDate(new Date(92, 10, 29));

        assertEquals(fatherDTO, fatherImpl.toFatherDTO(father));

        fatherDTO.setFirstName("Adas");
        assertNotEquals(fatherDTO, fatherImpl.toFatherDTO(father));

        fatherDTO.setFirstName("Adam");
        fatherDTO.setSecondName("Nowaa");
        assertNotEquals(fatherDTO, fatherImpl.toFatherDTO(father));

        fatherDTO.setSecondName("Nowak");
        fatherDTO.setPesel("1232131231");
        assertNotEquals(fatherDTO, fatherImpl.toFatherDTO(father));

        fatherDTO.setPesel("92112902576");
        fatherDTO.setDate(new Date(93, 10, 29));
        assertNotEquals(fatherDTO, fatherImpl.toFatherDTO(father));

        fatherDTO.setDate(new Date(92, 10, 29));
        fatherDTO.setId(12L);
        assertEquals(fatherDTO, fatherImpl.toFatherDTO(father));

        fatherDTO.setId(13L);
        assertEquals(fatherDTO, fatherImpl.toFatherDTO(father));
    }

    @Test
    public void testToFather() {
        fatherDTO.setFirstName("Adam");
        fatherDTO.setSecondName("Nowak");
        fatherDTO.setPesel("92112902576");
        fatherDTO.setDate(new Date(92, 10, 29));

        assertEquals(father, fatherImpl.toFather(fatherDTO));

        father.setFirstName("Adas");
        assertNotEquals(father, fatherImpl.toFather(fatherDTO));

        father.setFirstName("Adam");
        father.setSecondName("Nowaa");
        assertNotEquals(father, fatherImpl.toFather(fatherDTO));

        father.setSecondName("Nowak");
        father.setPesel("1232131231");
        assertNotEquals(father, fatherImpl.toFather(fatherDTO));

        father.setPesel("92112902576");
        father.setDate(new Date(93, 10, 29));
        assertNotEquals(father, fatherImpl.toFather(fatherDTO));

        father.setDate(new Date(92, 10, 29));
        father.setId(12L);
        assertEquals(father, fatherImpl.toFather(fatherDTO));

        father.setId(13L);
        assertEquals(father, fatherImpl.toFather(fatherDTO));
    }

    @Test
    public void testCheckDateAndPesel() {
        fatherDTO.setPesel("92112902576");
        fatherDTO.setDate(new Date(92, 10, 29));

        assertEquals(true, fatherImpl.checkDateAndPesel(fatherDTO));

        fatherDTO.setPesel("92112911111");
        assertEquals(true, fatherImpl.checkDateAndPesel(fatherDTO));

        fatherDTO.setPesel("91112902676");
        assertNotEquals(true, fatherImpl.checkDateAndPesel(fatherDTO));

        when(fatherRepositoryMock.findFirstByPesel("92112902576")).thenReturn(new Father("Adam", "Mock", "92112902576"));

        fatherDTO.setDate(new Date(91, 10, 29));
        assertEquals(true, fatherImpl.checkDateAndPesel(fatherDTO));
        fatherDTO.setPesel("92112902576");
        assertNotEquals(true, fatherImpl.checkDateAndPesel(fatherDTO));
    }
}
