package auca.ac.rw.campus_talent.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import auca.ac.rw.campus_talent.domain.Opportunity;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, UUID> {
    Optional<Opportunity> findByOpportunityNumber(int opportunityNumber);

    @Query("SELECT o FROM Opportunity o WHERE NOT EXISTS (SELECT r FROM Recruiter r WHERE r.opportunity = o) ORDER BY o.opportunityNumber ASC")
    List<Opportunity> findFreeOpportunities();
}
