package com.gap.metrics.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Document
public class ProjectMetric {
    private List<Double> averageVelocities;
    private List<Double> averageCycleTimes;
    private List<String> iterationNames;
}
