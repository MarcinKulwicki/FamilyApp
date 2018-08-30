package pl.marcinkulwicki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marcinkulwicki.entity.Child;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    Child findFirstByPesel(String name);
}
