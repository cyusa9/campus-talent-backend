package auca.ac.rw.campus_talent.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import auca.ac.rw.campus_talent.domain.Application;
import auca.ac.rw.campus_talent.domain.ApplicationStatus;
import auca.ac.rw.campus_talent.repository.ApplicationRepository;
import auca.ac.rw.campus_talent.repository.ProjectTaskRepository;
import auca.ac.rw.campus_talent.repository.RecruiterRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    public String saveApplication(Application application) {
        if (application.getApplicationDate() != null && application.getApplicationDate().isBefore(LocalDate.now())) {
            return "Application date cannot be in the past";
        }
        application.setStatus(ApplicationStatus.SUBMITTED);
        boolean isBooked = applicationRepository.existsByRecruiterAndApplicationDateAndStatusNot(
                application.getRecruiter(),
                application.getApplicationDate(),
                ApplicationStatus.REJECTED
        );
        if (isBooked) {
            return "Recruiter already reviews an application on that date";
        }
        applicationRepository.save(application);
        return "Application saved successfully";
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Optional<Application> getApplicationById(UUID id) {
        return applicationRepository.findById(id);
    }

    public String updateApplication(UUID id, Application application) {
        Optional<Application> existingOpt = applicationRepository.findById(id);
        if (existingOpt.isPresent()) {
            Application existing = existingOpt.get();
            existing.setApplicationDate(application.getApplicationDate());
            existing.setCoverNote(application.getCoverNote());
            existing.setStudent(application.getStudent());
            existing.setRecruiter(application.getRecruiter());
            applicationRepository.save(existing);
            return "Application updated successfully";
        }
        return "The application with that id does not exist";
    }

    public String updateStatus(UUID id, ApplicationStatus newStatus) {
        Optional<Application> opt = applicationRepository.findById(id);
        if (!opt.isPresent()) {
            return "The application with that id does not exist";
        }
        Application application = opt.get();
        ApplicationStatus current = application.getStatus();
        boolean allowed = false;
        if (current == ApplicationStatus.SUBMITTED && (newStatus == ApplicationStatus.SHORTLISTED || newStatus == ApplicationStatus.REJECTED)) {
            allowed = true;
        } else if (current == ApplicationStatus.SHORTLISTED && (newStatus == ApplicationStatus.HIRED || newStatus == ApplicationStatus.REJECTED)) {
            allowed = true;
        }
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        application.setStatus(newStatus);
        applicationRepository.save(application);
        return "Application status updated successfully";
    }

    public String deleteApplication(UUID id) {
        if (!applicationRepository.existsById(id)) {
            return "The application with that id does not exist";
        }
        if (projectTaskRepository.existsByApplicationId(id)) {
            return "That application has project tasks, delete them first";
        }
        applicationRepository.deleteById(id);
        return "Application deleted successfully";
    }

    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatusOrderByApplicationDateAsc(status);
    }

    public List<Application> getApplicationsBetween(LocalDate start, LocalDate end) {
        return applicationRepository.findByApplicationDateBetweenOrderByApplicationDateAsc(start, end);
    }

    public List<Object[]> getApplicationStatsByStatus() {
        return applicationRepository.countApplicationsByStatus();
    }

    @Transactional
    public String cancelDayForRecruiter(UUID recruiterId, LocalDate date) {
        if (!recruiterRepository.existsById(recruiterId)) {
            return "The recruiter with that id does not exist";
        }
        int count = applicationRepository.cancelApplicationsForRecruiterOnDate(
                recruiterId, date, ApplicationStatus.REJECTED, ApplicationStatus.HIRED
        );
        return count + " applications cancelled";
    }

    public Page<Application> getApplicationsPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return applicationRepository.findAll(pageable);
    }
}
