package pl.marcinkulwicki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marcinkulwicki.entity.Family;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    Family findFirstByFatherId(Long id);
    Family findFirstById(Long id);
}
