package auca.ac.rw.campus_talent.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import auca.ac.rw.campus_talent.domain.Opportunity;
import auca.ac.rw.campus_talent.repository.OpportunityRepository;

@Service
public class OpportunityService {

    @Autowired
    private OpportunityRepository opportunityRepository;

    public String saveOpportunity(Opportunity opportunity) {
        opportunityRepository.save(opportunity);
        return "Opportunity saved successfully";
    }

    public List<Opportunity> getAllOpportunities() {
        return opportunityRepository.findAll();
    }

    public Optional<Opportunity> getOpportunityById(UUID id) {
        return opportunityRepository.findById(id);
    }

    public String updateOpportunity(UUID id, Opportunity opportunity) {
        if (!opportunityRepository.existsById(id)) {
            return "The opportunity with that id does not exist";
        }
        opportunity.setId(id);
        opportunityRepository.save(opportunity);
        return "Opportunity updated successfully";
    }

    public String deleteOpportunity(UUID id) {
        if (!opportunityRepository.existsById(id)) {
            return "The opportunity with that id does not exist";
        }
        opportunityRepository.deleteById(id);
        return "Opportunity deleted successfully";
    }

    public List<Opportunity> getFreeOpportunities() {
        return opportunityRepository.findFreeOpportunities();
    }
}
