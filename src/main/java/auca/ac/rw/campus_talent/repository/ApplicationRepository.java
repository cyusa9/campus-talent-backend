package auca.ac.rw.campus_talent.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import auca.ac.rw.campus_talent.domain.Application;
import auca.ac.rw.campus_talent.domain.ApplicationStatus;
import auca.ac.rw.campus_talent.domain.Recruiter;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    List<Application> findByStatusOrderByApplicationDateAsc(ApplicationStatus status);

    List<Application> findByApplicationDateBetweenOrderByApplicationDateAsc(LocalDate start, LocalDate end);

    boolean existsByRecruiterAndApplicationDateAndStatusNot(Recruiter recruiter, LocalDate applicationDate, ApplicationStatus status);

    long countByStatus(ApplicationStatus status);

    long countByApplicationDateAndStatusNot(LocalDate applicationDate, ApplicationStatus status);

    @Query("SELECT a.status, COUNT(a) FROM Application a GROUP BY a.status")
    List<Object[]> countApplicationsByStatus();

    @Modifying
    @Query("UPDATE Application a SET a.status = :cancelled WHERE a.recruiter.id = :recruiterId AND a.applicationDate = :date AND a.status != :hired")
    int cancelApplicationsForRecruiterOnDate(
        @Param("recruiterId") UUID recruiterId,
        @Param("date") LocalDate date,
        @Param("cancelled") ApplicationStatus cancelled,
        @Param("hired") ApplicationStatus hired
    );
}
