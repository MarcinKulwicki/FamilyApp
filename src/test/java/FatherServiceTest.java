
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.marcinkulwicki.DTO.FatherDTO;
import pl.marcinkulwicki.entity.Father;
import pl.marcinkulwicki.repository.FatherRepository;
import pl.marcinkulwicki.service.FatherService;

@RunWith(MockitoJUnitRunner.class)
public class FatherServiceTest {

    @Mock
    FatherRepository fatherRepositoryMock;

    @InjectMocks
    FatherService fatherImpl;

    @Test
    public void testAddFather(){
        FatherDTO fatherDTO = new FatherDTO();
        fatherDTO.setPesel("92112902576");


        when(fatherRepositoryMock.findFirstByPesel("92112902576")).thenReturn(new Father("Adam", "Nowak", "92112902576"));
        assertEquals(false, fatherImpl.checkPeselInDb(fatherDTO));
        fatherDTO.setPesel("93112606265");
        assertEquals(true, fatherImpl.checkPeselInDb(fatherDTO));
    }
}
