package auca.ac.rw.campus_talent.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import auca.ac.rw.campus_talent.domain.Opportunity;
import auca.ac.rw.campus_talent.domain.Recruiter;
import auca.ac.rw.campus_talent.domain.Skill;
import auca.ac.rw.campus_talent.repository.OpportunityRepository;
import auca.ac.rw.campus_talent.repository.RecruiterRepository;
import auca.ac.rw.campus_talent.repository.SkillRepository;

@Service
public class RecruiterService {

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private OpportunityRepository opportunityRepository;

    public String saveRecruiter(Recruiter recruiter) {
        recruiterRepository.save(recruiter);
        return "Recruiter saved successfully";
    }

    public List<Recruiter> getAllRecruiters() {
        return recruiterRepository.findAll();
    }

    public Optional<Recruiter> getRecruiterById(UUID id) {
        return recruiterRepository.findById(id);
    }

    public String updateRecruiter(UUID id, Recruiter recruiter) {
        if (!recruiterRepository.existsById(id)) {
            return "The recruiter with that id does not exist";
        }
        recruiter.setId(id);
        recruiterRepository.save(recruiter);
        return "Recruiter updated successfully";
    }

    public String deleteRecruiter(UUID id) {
        if (!recruiterRepository.existsById(id)) {
            return "The recruiter with that id does not exist";
        }
        recruiterRepository.deleteById(id);
        return "Recruiter deleted successfully";
    }

    public List<Recruiter> getRecruitersBySkill(String name) {
        return recruiterRepository.findBySkillName(name);
    }

    public String addSkillToRecruiter(UUID recruiterId, UUID skillId) {
        Optional<Recruiter> recruiterOpt = recruiterRepository.findById(recruiterId);
        if (!recruiterOpt.isPresent()) {
            return "The recruiter with that id does not exist";
        }
        Optional<Skill> skillOpt = skillRepository.findById(skillId);
        if (!skillOpt.isPresent()) {
            return "That skill does not exist";
        }
        Recruiter recruiter = recruiterOpt.get();
        Skill skill = skillOpt.get();
        if (recruiter.getSkills().contains(skill)) {
            return "The recruiter already has that skill";
        }
        recruiter.getSkills().add(skill);
        recruiterRepository.save(recruiter);
        return "Skill added to the recruiter";
    }

    public String changeRecruiterOpportunity(UUID recruiterId, int opportunityNumber) {
        Optional<Recruiter> recruiterOpt = recruiterRepository.findById(recruiterId);
        if (!recruiterOpt.isPresent()) {
            return "The recruiter with that id does not exist";
        }
        Optional<Opportunity> oppOpt = opportunityRepository.findByOpportunityNumber(opportunityNumber);
        if (!oppOpt.isPresent()) {
            return "The opportunity with that opportunityNumber does not exist";
        }
        Opportunity opp = oppOpt.get();
        if (recruiterRepository.existsByOpportunity(opp)) {
            return "That opportunity is taken by another recruiter";
        }
        Recruiter recruiter = recruiterOpt.get();
        recruiter.setOpportunity(opp);
        recruiterRepository.save(recruiter);
        return "Opportunity changed successfully";
    }
}
