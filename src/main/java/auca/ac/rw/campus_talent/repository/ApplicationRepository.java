package auca.ac.rw.campus_talent.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import auca.ac.rw.campus_talent.domain.Application;
import auca.ac.rw.campus_talent.domain.ApplicationStatus;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    List<Application> findByStatusOrderByApplicationDateAsc(ApplicationStatus status);
    List<Application> findByApplicationDateBetweenOrderByApplicationDateAsc(LocalDate start, LocalDate end);
}
