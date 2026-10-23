package auca.ac.rw.campus_talent.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import auca.ac.rw.campus_talent.domain.Student;
import auca.ac.rw.campus_talent.repository.RecruiterRepository;
import auca.ac.rw.campus_talent.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    public String saveStudent(Student student) {
        studentRepository.save(student);
        return "Student saved successfully";
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(UUID id) {
        return studentRepository.findById(id);
    }

    public String updateStudent(UUID id, Student student) {
        if (!studentRepository.existsById(id)) {
            return "The student with that id does not exist";
        }
        student.setId(id);
        studentRepository.save(student);
        return "Student updated successfully";
    }

    public String deleteStudent(UUID id) {
        if (!studentRepository.existsById(id)) {
            return "The student with that id does not exist";
        }
        studentRepository.deleteById(id);
        return "Student deleted successfully";
    }

    public List<Student> getStudentsByLastName(String lastName) {
        return studentRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public boolean recruiterExists(UUID recruiterId) {
        return recruiterRepository.existsById(recruiterId);
    }

    public List<Student> getStudentsOfRecruiter(UUID recruiterId) {
        return studentRepository.findStudentsByRecruiterId(recruiterId);
    }

    public Page<Student> getStudentsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.findAll(pageable);
    }
}
