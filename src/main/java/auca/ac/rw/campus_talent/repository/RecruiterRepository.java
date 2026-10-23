package auca.ac.rw.campus_talent.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import auca.ac.rw.campus_talent.domain.Opportunity;
import auca.ac.rw.campus_talent.domain.Recruiter;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, UUID> {
    @Query("SELECT r FROM Recruiter r JOIN r.skills s WHERE LOWER(s.name) = LOWER(:name)")
    List<Recruiter> findBySkillName(@Param("name") String name);

    boolean existsByOpportunity(Opportunity opportunity);
}
