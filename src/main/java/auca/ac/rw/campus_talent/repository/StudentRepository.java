package auca.ac.rw.campus_talent.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import auca.ac.rw.campus_talent.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    List<Student> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT a.student FROM Application a WHERE a.recruiter.id = :recruiterId")
    List<Student> findStudentsByRecruiterId(@Param("recruiterId") UUID recruiterId);
}
