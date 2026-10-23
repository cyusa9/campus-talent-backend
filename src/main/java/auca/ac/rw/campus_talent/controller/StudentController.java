package auca.ac.rw.campus_talent.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import auca.ac.rw.campus_talent.domain.Student;
import auca.ac.rw.campus_talent.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<String> saveStudent(@RequestBody Student student) {
        String res = studentService.saveStudent(student);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable UUID id) {
        Optional<Student> opt = studentService.getStudentById(id);
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The student with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable UUID id, @RequestBody Student student) {
        String res = studentService.updateStudent(id, student);
        if ("Student updated successfully".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable UUID id) {
        String res = studentService.deleteStudent(id);
        if ("Student deleted successfully".equals(res)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Student>> getStudentsByLastName(@RequestParam("lastName") String lastName) {
        return new ResponseEntity<>(studentService.getStudentsByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping("/of-recruiter/{recruiterId}")
    public ResponseEntity<?> getStudentsOfRecruiter(@PathVariable UUID recruiterId) {
        if (!studentService.recruiterExists(recruiterId)) {
            return new ResponseEntity<>("The recruiter with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentService.getStudentsOfRecruiter(recruiterId), HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Student>> getStudentsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return new ResponseEntity<>(studentService.getStudentsPaged(page, size), HttpStatus.OK);
    }
}
