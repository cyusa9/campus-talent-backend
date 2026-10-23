package auca.ac.rw.campus_talent.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import auca.ac.rw.campus_talent.domain.Skill;
import auca.ac.rw.campus_talent.repository.SkillRepository;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public String saveSkill(Skill skill) {
        skillRepository.save(skill);
        return "Skill saved successfully";
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Optional<Skill> getSkillById(UUID id) {
        return skillRepository.findById(id);
    }

    public String updateSkill(UUID id, Skill skill) {
        if (!skillRepository.existsById(id)) {
            return "The skill with that id does not exist";
        }
        skill.setId(id);
        skillRepository.save(skill);
        return "Skill updated successfully";
    }

    public String deleteSkill(UUID id) {
        if (!skillRepository.existsById(id)) {
            return "The skill with that id does not exist";
        }
        skillRepository.deleteById(id);
        return "Skill deleted successfully";
    }

    public List<Skill> getUnusedSkills() {
        return skillRepository.findUnusedSkills();
    }
}
