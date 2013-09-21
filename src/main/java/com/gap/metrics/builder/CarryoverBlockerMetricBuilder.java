package com.gap.metrics.builder;

import com.gap.metrics.dto.CarryoverBlockers;
import com.gap.metrics.model.Iteration;
import com.gap.metrics.model.ProjectIterationDetail;

import java.util.List;

public class CarryoverBlockerMetricBuilder {
    private List<Iteration> iterations;
    private List<ProjectIterationDetail> projectIterationDetails;

    int numberOfProjects = 0;
    double totalCarryOvers = 0, totalBlockers = 0;

    public CarryoverBlockerMetricBuilder withIterations(List<Iteration> iterations) {
        this.iterations = iterations;
        return this;
    }

    public CarryoverBlockerMetricBuilder withDetails(List<ProjectIterationDetail> projectIterationDetails) {
        this.projectIterationDetails = projectIterationDetails;
        return this;
    }

    public CarryoverBlockers build() {
        CarryoverBlockers metric = new CarryoverBlockers();

        for(Iteration iteration : iterations){
            numberOfProjects = 0;
            totalCarryOvers = 0;
            totalBlockers = 0;
            for(ProjectIterationDetail detail : projectIterationDetails){
                if (detail.getIterationId().equals(iteration.getId())){
                    numberOfProjects++;
                    totalCarryOvers += detail.getNumberOfCarryOver();
                    totalBlockers += detail.getNumberOfBlockers();
                }
            }
            if (numberOfProjects > 0){
                metric.getCarryOvers().add(totalCarryOvers / numberOfProjects);
                metric.getBlockers().add(totalBlockers / numberOfProjects);
                metric.getIterationNames().add(iteration.getIterationNumber());
            }
        }

        return metric;
    }
}
