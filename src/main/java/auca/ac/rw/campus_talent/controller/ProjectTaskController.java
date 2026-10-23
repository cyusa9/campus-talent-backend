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
import auca.ac.rw.campus_talent.domain.ProjectTask;
import auca.ac.rw.campus_talent.service.ProjectTaskService;

@RestController
@RequestMapping("/api/project-tasks")
public class ProjectTaskController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @PostMapping("/save")
    public ResponseEntity<?> saveProjectTask(@RequestBody ProjectTask projectTask) {
        String result = projectTaskService.saveProjectTask(projectTask);
        if ("Project task saved successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else if ("The application with that id does not exist".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if ("Project tasks are only for HIRED applications".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectTask>> getAllProjectTasks() {
        return new ResponseEntity<>(projectTaskService.getAllProjectTasks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectTaskById(@PathVariable UUID id) {
        Optional<ProjectTask> opt = projectTaskService.getProjectTaskById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("The project task with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<?> getProjectTasksByApplication(@PathVariable UUID applicationId) {
        List<ProjectTask> list = projectTaskService.getProjectTasksByApplication(applicationId);
        if (list == null) {
            return new ResponseEntity<>("The application with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/student/{regNumber}")
    public ResponseEntity<List<ProjectTask>> getProjectTasksByStudentRegNumber(@PathVariable String regNumber) {
        List<ProjectTask> list = projectTaskService.getProjectTasksByStudentRegNumber(regNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping(value = {"/{id}", "/update/{id}"})
    public ResponseEntity<?> updateProjectTask(@PathVariable UUID id, @RequestBody ProjectTask projectTask) {
        String result = projectTaskService.updateProjectTask(id, projectTask);
        if ("Project task updated successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = {"/{id}", "/delete/{id}"})
    public ResponseEntity<?> deleteProjectTask(@PathVariable UUID id) {
        String result = projectTaskService.deleteProjectTask(id);
        if ("Project task deleted successfully".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}
