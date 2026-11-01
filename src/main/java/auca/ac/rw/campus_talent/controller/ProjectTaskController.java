package auca.ac.rw.campus_talent.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import auca.ac.rw.campus_talent.domain.ProjectTask;
import auca.ac.rw.campus_talent.service.ProjectTaskService;

@RestController
@RequestMapping("/api/project-tasks")
public class ProjectTaskController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @PostMapping("/save")
    public ResponseEntity<?> saveProjectTask(@RequestParam("applicationId") UUID applicationId, @RequestBody ProjectTask projectTask) {
        String result = projectTaskService.saveProjectTask(applicationId, projectTask);
        if ("Project task saved successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else if ("The application with that id does not exist".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/student/{regNumber}")
    public ResponseEntity<List<ProjectTask>> getProjectTasksByStudentRegNumber(@PathVariable String regNumber) {
        return new ResponseEntity<>(projectTaskService.getProjectTasksByStudentRegNumber(regNumber), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProjectTask(@PathVariable UUID id) {
        String result = projectTaskService.deleteProjectTask(id);
        if ("Project task deleted successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}
