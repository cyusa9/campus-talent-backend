package auca.ac.rw.campus_talent.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import auca.ac.rw.campus_talent.domain.Recruiter;
import auca.ac.rw.campus_talent.repository.RecruiterRepository;

@Service
public class RecruiterService {

    @Autowired
    private RecruiterRepository recruiterRepository;

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
}
