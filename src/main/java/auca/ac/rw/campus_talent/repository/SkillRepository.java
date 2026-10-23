package auca.ac.rw.campus_talent.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import auca.ac.rw.campus_talent.domain.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, UUID> {
    @Query("SELECT s FROM Skill s WHERE s.recruiters IS EMPTY")
    List<Skill> findUnusedSkills();
}
