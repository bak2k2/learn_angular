package com.gap.metrics.builder;

import com.gap.metrics.dto.ProjectMetric;
import com.gap.metrics.model.Iteration;
import com.gap.metrics.model.ProjectIterationDetail;
import com.gap.metrics.model.TeamComposition;

import java.util.List;

public class ProjectMetricBuilder {

    private List<Iteration> iterations;
    private List<ProjectIterationDetail> projectIterationDetails;

    int numProjWithVelMoreThanZero = 0, numProjWithTrans = 0, numberOfProjects = 0;
    double totalCycleTime = 0, totalVelocity = 0, totalTransition = 0;
    double totalEmployees = 0, totalContractors = 0;


    public ProjectMetricBuilder withIterations(List<Iteration> iterations) {
        this.iterations = iterations;
        return this;
    }

    public ProjectMetricBuilder withDetails(List<ProjectIterationDetail> projectIterationDetails) {
        this.projectIterationDetails = projectIterationDetails;
        return this;
    }

    public ProjectMetric buildTransitionVelocityMetric() {
        ProjectMetric metric = new ProjectMetric();
        for(Iteration iteration : iterations){
            numProjWithVelMoreThanZero = 0;
            numProjWithTrans = 0;
            totalVelocity = 0;
            totalTransition = 0;
            for(ProjectIterationDetail detail : projectIterationDetails){
                if (detail.getIterationId().equals(iteration.getId())){
                    if (detail.getVelocity() > 0){
                        numProjWithVelMoreThanZero++;
                        totalVelocity += detail.getVelocity();
                    }
                    numProjWithTrans++;
                    totalTransition += detail.getTransition();
                }
            }
            if (numProjWithTrans > 0 || numProjWithVelMoreThanZero > 0){
                metric.getAverageVelocities().add(totalVelocity / numProjWithVelMoreThanZero);
                metric.getAverageTransitions().add(totalTransition / numProjWithTrans);
                metric.getIterationNames().add(iteration.getIterationNumber());
            }
        }
        return metric;
    }

    public ProjectMetric buildAverageCycleTimeMetric() {
        ProjectMetric metric = new ProjectMetric();
        for(Iteration iteration : iterations){
            numberOfProjects = 0;
            totalCycleTime = 0;
            for(ProjectIterationDetail detail : projectIterationDetails){
                if (detail.getIterationId().equals(iteration.getId()) && detail.getCycleTime() > 0){
                    numberOfProjects++;
                    totalCycleTime += detail.getCycleTime();
                }
            }
            if (numberOfProjects > 0){
                metric.getAverageCycleTimes().add(totalCycleTime / numberOfProjects);
                metric.getIterationNames().add(iteration.getIterationNumber());
            }
        }
        return metric;
    }

    public ProjectMetric buildEmployeeContractorMetric() {
        ProjectMetric metric = new ProjectMetric();
        for(Iteration iteration : iterations){
            numberOfProjects = 0;
            totalEmployees = 0;
            totalContractors = 0;
            for(ProjectIterationDetail detail : projectIterationDetails){
                TeamComposition teamComposition = detail.getTeamComposition();
                if (detail.getIterationId().equals(iteration.getId()) &&
                        (teamComposition.getNumberOfFTE() > 0 || teamComposition.getNumberOfContractors() > 0)){
                    numberOfProjects++;
                    totalEmployees += teamComposition.getNumberOfFTE();
                    totalContractors += teamComposition.getNumberOfContractors();
                }
            }
            if (numberOfProjects > 0){
                metric.getTotalNoEmployees().add(totalEmployees);
                metric.getTotalNoContractors().add(totalContractors);
                metric.getIterationNames().add(iteration.getIterationNumber());
            }
        }
        return metric;
    }
}
