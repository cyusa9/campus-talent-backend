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

    public String saveProjectTask(ProjectTask projectTask) {
        if (projectTask.getApplication() == null || projectTask.getApplication().getId() == null) {
            return "The application with that id does not exist";
        }
        Optional<Application> appOpt = applicationRepository.findById(projectTask.getApplication().getId());
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

    public List<ProjectTask> getAllProjectTasks() {
        return projectTaskRepository.findAll();
    }

    public Optional<ProjectTask> getProjectTaskById(UUID id) {
        return projectTaskRepository.findById(id);
    }

    public List<ProjectTask> getProjectTasksByApplication(UUID applicationId) {
        if (!applicationRepository.existsById(applicationId)) {
            return null;
        }
        return projectTaskRepository.findByApplicationId(applicationId);
    }

    public List<ProjectTask> getProjectTasksByStudentRegNumber(String regNumber) {
        return projectTaskRepository.findHistoryByStudentRegNumber(regNumber);
    }

    public String updateProjectTask(UUID id, ProjectTask payload) {
        Optional<ProjectTask> opt = projectTaskRepository.findById(id);
        if (!opt.isPresent()) {
            return "Project task not found";
        }
        ProjectTask task = opt.get();
        task.setTaskName(payload.getTaskName());
        task.setDescription(payload.getDescription());
        task.setHours(payload.getHours());
        projectTaskRepository.save(task);
        return "Project task updated successfully";
    }

    public String deleteProjectTask(UUID id) {
        if (!projectTaskRepository.existsById(id)) {
            return "Project task not found";
        }
        projectTaskRepository.deleteById(id);
        return "Project task deleted successfully";
    }
}
