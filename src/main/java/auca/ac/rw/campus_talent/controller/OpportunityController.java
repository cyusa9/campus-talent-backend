package auca.ac.rw.campus_talent.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import auca.ac.rw.campus_talent.domain.Opportunity;
import auca.ac.rw.campus_talent.service.OpportunityService;

@RestController
@RequestMapping("/api/opportunities")
public class OpportunityController {

    @Autowired
    private OpportunityService opportunityService;

    @PostMapping("/save")
    public ResponseEntity<String> saveOpportunity(@RequestBody Opportunity opportunity) {
        String res = opportunityService.saveOpportunity(opportunity);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Opportunity>> getAllOpportunities() {
        return new ResponseEntity<>(opportunityService.getAllOpportunities(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOpportunityById(@PathVariable UUID id) {
        Optional<Opportunity> opt = opportunityService.getOpportunityById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The opportunity with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOpportunity(@PathVariable UUID id, @RequestBody Opportunity opportunity) {
        String res = opportunityService.updateOpportunity(id, opportunity);
        if ("Opportunity updated successfully".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOpportunity(@PathVariable UUID id) {
        String res = opportunityService.deleteOpportunity(id);
        if ("Opportunity deleted successfully".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }
}
