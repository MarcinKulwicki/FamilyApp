package pl.marcinkulwicki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.marcinkulwicki.entity.Child;

import java.util.List;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface ChildRepository extends JpaRepository<Child, Long> {

    Child findFirstByPesel(String name);

    List<Child> findAllByFamilyId(Long id);

    @Query(value = "SELECT * FROM family.child where first_name like :pFirstName and second_name like :pSecondName and sex like :pSex and pesel like :pPesel and pesel like :pDate" , nativeQuery = true)
    List<Child> findAllBySearchParameters(@Param("pFirstName") String fistName, @Param("pSecondName") String secondName, @Param("pSex") String sex, @Param("pPesel") String pesel, @Param("pDate") String date);

    //Date is compare to pesele using for example 921129% then you must convert Date 1992/11/29 to 921129%
}
