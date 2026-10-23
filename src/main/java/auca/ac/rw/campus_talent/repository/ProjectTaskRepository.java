package auca.ac.rw.campus_talent.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import auca.ac.rw.campus_talent.domain.ProjectTask;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, UUID> {
    List<ProjectTask> findByApplicationId(UUID applicationId);

    boolean existsByApplicationId(UUID applicationId);

    @Query("SELECT pt FROM ProjectTask pt WHERE pt.application.student.regNumber = :regNumber ORDER BY pt.assignedDate DESC")
    List<ProjectTask> findHistoryByStudentRegNumber(@Param("regNumber") String regNumber);
}
