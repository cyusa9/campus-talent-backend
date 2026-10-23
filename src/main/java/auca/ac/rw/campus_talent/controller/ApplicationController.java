package auca.ac.rw.campus_talent.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import auca.ac.rw.campus_talent.domain.Application;
import auca.ac.rw.campus_talent.domain.ApplicationStatus;
import auca.ac.rw.campus_talent.service.ApplicationService;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/save")
    public ResponseEntity<?> saveApplication(@RequestBody Application application) {
        String res = applicationService.saveApplication(application);
        if ("Application date cannot be in the past".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        } else if ("Recruiter already reviews an application on that date".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Application>> getAllApplications() {
        return new ResponseEntity<>(applicationService.getAllApplications(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable UUID id) {
        Optional<Application> opt = applicationService.getApplicationById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The application with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateApplication(@PathVariable UUID id, @RequestBody Application application) {
        String res = applicationService.updateApplication(id, application);
        if ("Application updated successfully".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestParam("status") ApplicationStatus status) {
        String res = applicationService.updateStatus(id, status);
        if ("Application status updated successfully".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else if ("The application with that id does not exist".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = {"/{id}", "/delete/{id}"})
    public ResponseEntity<String> deleteApplication(@PathVariable UUID id) {
        String res = applicationService.deleteApplication(id);
        if ("Application deleted successfully".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else if ("That application has project tasks, delete them first".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<Application>> getApplicationsByStatus(@RequestParam("status") ApplicationStatus status) {
        return new ResponseEntity<>(applicationService.getApplicationsByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Application>> getApplicationsBetween(@RequestParam("start") String start, @RequestParam("end") String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(applicationService.getApplicationsBetween(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getApplicationStatsByStatus() {
        return new ResponseEntity<>(applicationService.getApplicationStatsByStatus(), HttpStatus.OK);
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<?> cancelDay(@RequestParam("recruiterId") UUID recruiterId, @RequestParam("date") String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        String res = applicationService.cancelDayForRecruiter(recruiterId, parsedDate);
        if ("The recruiter with that id does not exist".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Application>> getApplicationsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return new ResponseEntity<>(applicationService.getApplicationsPaged(page, size), HttpStatus.OK);
    }
}
