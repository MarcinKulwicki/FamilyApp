package pl.marcinkulwicki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marcinkulwicki.entity.Father;

@Repository
public interface FatherRepository extends JpaRepository<Father, Long> {
}
