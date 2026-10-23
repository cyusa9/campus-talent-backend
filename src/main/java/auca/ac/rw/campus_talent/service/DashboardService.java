package auca.ac.rw.campus_talent.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import auca.ac.rw.campus_talent.domain.ApplicationStatus;
import auca.ac.rw.campus_talent.repository.ApplicationRepository;
import auca.ac.rw.campus_talent.repository.OpportunityRepository;
import auca.ac.rw.campus_talent.repository.ProjectTaskRepository;
import auca.ac.rw.campus_talent.repository.RecruiterRepository;
import auca.ac.rw.campus_talent.repository.StudentRepository;

@Service
public class DashboardService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public Map<String, Object> getSummary() {
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("students", studentRepository.count());
        summary.put("recruiters", recruiterRepository.count());
        summary.put("freeOpportunities", opportunityRepository.findFreeOpportunities().size());
        summary.put("applicationsToday", applicationRepository.countByApplicationDateAndStatusNot(LocalDate.now(), ApplicationStatus.REJECTED));
        summary.put("submitted", applicationRepository.countByStatus(ApplicationStatus.SUBMITTED));
        summary.put("projectTasks", projectTaskRepository.count());
        return summary;
    }
}
