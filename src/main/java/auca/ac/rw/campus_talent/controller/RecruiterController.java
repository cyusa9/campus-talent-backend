package auca.ac.rw.campus_talent.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import auca.ac.rw.campus_talent.domain.Recruiter;
import auca.ac.rw.campus_talent.service.RecruiterService;

@RestController
@RequestMapping("/api/recruiters")
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    @PostMapping("/save")
    public ResponseEntity<String> saveRecruiter(@RequestBody Recruiter recruiter) {
        String res = recruiterService.saveRecruiter(recruiter);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Recruiter>> getAllRecruiters() {
        return new ResponseEntity<>(recruiterService.getAllRecruiters(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecruiterById(@PathVariable UUID id) {
        Optional<Recruiter> opt = recruiterService.getRecruiterById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The recruiter with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRecruiter(@PathVariable UUID id, @RequestBody Recruiter recruiter) {
        String res = recruiterService.updateRecruiter(id, recruiter);
        if ("Recruiter updated successfully".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRecruiter(@PathVariable UUID id) {
        String res = recruiterService.deleteRecruiter(id);
        if ("Recruiter deleted successfully".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-skill")
    public ResponseEntity<List<Recruiter>> getRecruitersBySkill(@RequestParam("name") String name) {
        return new ResponseEntity<>(recruiterService.getRecruitersBySkill(name), HttpStatus.OK);
    }

    @PutMapping("/{recruiterId}/skills/{skillId}")
    public ResponseEntity<?> addSkillToRecruiter(@PathVariable UUID recruiterId, @PathVariable UUID skillId) {
        String res = recruiterService.addSkillToRecruiter(recruiterId, skillId);
        if ("Skill added to the recruiter".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else if ("The recruiter already has that skill".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{recruiterId}/opportunity")
    public ResponseEntity<?> changeRecruiterOpportunity(@PathVariable UUID recruiterId, @RequestParam("opportunityNumber") int opportunityNumber) {
        String res = recruiterService.changeRecruiterOpportunity(recruiterId, opportunityNumber);
        if ("Opportunity changed successfully".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else if ("That opportunity is taken by another recruiter".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }
}
