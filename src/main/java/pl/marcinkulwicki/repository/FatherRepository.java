package pl.marcinkulwicki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.marcinkulwicki.entity.Father;

@Repository
public interface FatherRepository extends JpaRepository<Father, Long> {

    Father findFirstById(Long id);
    Father findFirstByPesel(String name);

    @Query(value = "SELECT * FROM father where first_name like :pFistName and second_name like :pSecondName and pesel like :pPesel" , nativeQuery = true)
    Father findFirstBySearchParameters(@Param("pFistName") String fistName, @Param("pSecondName") String secondName, @Param("pPesel") String pesel);
}
