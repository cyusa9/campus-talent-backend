package auca.ac.rw.campus_talent.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import auca.ac.rw.campus_talent.domain.Application;
import auca.ac.rw.campus_talent.repository.ApplicationRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public String saveApplication(Application application) {
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
        if (!applicationRepository.existsById(id)) {
            return "The application with that id does not exist";
        }
        application.setId(id);
        applicationRepository.save(application);
        return "Application updated successfully";
    }

    public String deleteApplication(UUID id) {
        if (!applicationRepository.existsById(id)) {
            return "The application with that id does not exist";
        }
        applicationRepository.deleteById(id);
        return "Application deleted successfully";
    }
}
