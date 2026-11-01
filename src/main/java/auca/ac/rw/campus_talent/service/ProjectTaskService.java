package auca.ac.rw.campus_talent.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import auca.ac.rw.campus_talent.domain.Application;
import auca.ac.rw.campus_talent.domain.ApplicationStatus;
import auca.ac.rw.campus_talent.domain.ProjectTask;
import auca.ac.rw.campus_talent.repository.ApplicationRepository;
import auca.ac.rw.campus_talent.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public String saveProjectTask(UUID applicationId, ProjectTask projectTask) {
        Optional<Application> appOpt = applicationRepository.findById(applicationId);
        if (!appOpt.isPresent()) {
            return "The application with that id does not exist";
        }
        Application application = appOpt.get();
        if (application.getStatus() != ApplicationStatus.HIRED) {
            return "Project tasks are only for HIRED applications";
        }
        projectTask.setApplication(application);
        projectTask.setAssignedDate(LocalDate.now());
        projectTaskRepository.save(projectTask);
        return "Project task saved successfully";
    }

    public List<ProjectTask> getProjectTasksByStudentRegNumber(String regNumber) {
        return projectTaskRepository.findHistoryByStudentRegNumber(regNumber);
    }

    public String deleteProjectTask(UUID id) {
        if (!projectTaskRepository.existsById(id)) {
            return "The project task with that id does not exist";
        }
        projectTaskRepository.deleteById(id);
        return "Project task deleted successfully";
    }
}
