package com.gap.metrics.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document
public class ProjectMetric {
    private List<Double> averageVelocities = new ArrayList<Double>();
    private List<Double> averageCycleTimes = new ArrayList<Double>();
    private List<Double> averageTransitions = new ArrayList<Double>();
    private List<String> iterationNames = new ArrayList<String>();
    private List<Double> totalNoEmployees = new ArrayList<Double>();
    private List<Double> totalNoContractors = new ArrayList<Double>();
}
