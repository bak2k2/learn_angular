package com.gap.metrics.controller;

import com.gap.metrics.dto.IterationReport;
import com.gap.metrics.dto.Report;
import com.gap.metrics.model.Iteration;
import com.gap.metrics.model.Project;
import com.gap.metrics.model.ProjectIterationDetails;
import com.gap.metrics.service.IterationService;
import com.gap.metrics.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ReportsController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private IterationService iterationService;

    @RequestMapping(value = "/iterations/reports", method = RequestMethod.GET)
    public ResponseEntity<?> fetchReports(){
        List<IterationReport> iterationReports = new ArrayList<IterationReport>();
        for(Iteration iteration : iterationService.listIterations()){
            IterationReport report = new IterationReport();
            report.setIteration(iteration);
            report.setProjectReports(fetchProjectLevelReportsFor(iteration));
            iterationReports.add(report);
        }

        return new ResponseEntity<List<IterationReport>>(iterationReports, HttpStatus.OK);
    }

    private List<Report> fetchProjectLevelReportsFor(Iteration iteration) {
        List<Report> reports = new ArrayList<Report>();
        List<Project> projects = projectService.listProjects();
        Collections.sort(projects);
        for(Project project : projects){
            Report report = new Report();
            report.setIteration(iteration);
            report.setProject(project);
            List<String> messages = fetchProjectIterationReportMessages(iteration, project);
            report.setMessages(messages);
            report.setReportScore(messages.size());
            reports.add(report);
        }
        return reports;
    }

    private List<String> fetchProjectIterationReportMessages(Iteration iteration, Project project) {
        List<String> messages = new ArrayList<String>();
        ProjectIterationDetails projectIterationDetails = projectService.getProjectIterationDetails(project.getId(), iteration.getId());
        if(demographicInfoIsNotAvailable(projectIterationDetails))
            messages.add("Demographic information is not complete.");
        if(cycleTimeInfoIsNotAvailable(projectIterationDetails))
            messages.add("Cycletime information is not complete.");
        if(employeeContractorInfoIsNotAvailable(projectIterationDetails))
            messages.add("Employee Contractor information is not complete.");
        if(happinessMetricsIsNotAvailable(projectIterationDetails))
            messages.add("Happiness Metrics is not complete.");
        if(retroFeedbackIsNotAvailable(projectIterationDetails))
            messages.add("Retro feedback is not complete.");

        return messages;
    }

    private boolean retroFeedbackIsNotAvailable(ProjectIterationDetails projectIterationDetails) {
        return projectIterationDetails.getRetroComments() == null || projectIterationDetails.getRetroComments().isEmpty();
    }

    private boolean happinessMetricsIsNotAvailable(ProjectIterationDetails projectIterationDetails) {
        return (projectIterationDetails.getEngagement() == 0.0 ||
                projectIterationDetails.getCommitment() == 0.0 ||
                projectIterationDetails.getPerceivedValue() == 0.0 ||
                projectIterationDetails.getRespectTrust() == 0.0);
    }

    private boolean employeeContractorInfoIsNotAvailable(ProjectIterationDetails projectIterationDetails) {
        return (projectIterationDetails.getNumberOfFTE() == 0 &&
                projectIterationDetails.getNumberOfContractors() == 0);
    }

    private boolean cycleTimeInfoIsNotAvailable(ProjectIterationDetails projectIterationDetails) {
        return (projectIterationDetails.getCycleTime() == 0);
    }

    private boolean demographicInfoIsNotAvailable(ProjectIterationDetails projectIterationDetails) {
        return (projectIterationDetails.getNumberOfOnshoreRes() == 0 &&
                projectIterationDetails.getNumberOfNearshoreRes() ==0 &&
                projectIterationDetails.getNumberOfOffshoreRes() == 0);
    }

}
