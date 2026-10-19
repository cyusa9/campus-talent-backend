package auca.ac.rw.campus_talent.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import auca.ac.rw.campus_talent.domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {
}
