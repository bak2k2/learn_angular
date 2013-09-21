package com.gap.metrics.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class ProjectIterationDetail {
    @Id
    private String id;
    private String projectId;
    private String iterationId;
    private double velocity;
    private double transition;
    private double cycleTime;
    private double numberOfCarryOver;
    private double numberOfBlockers;
    private String retroComments;
    private TeamComposition teamComposition = new TeamComposition();
    private HappinessMetric happinessMetric = new HappinessMetric();

    public boolean IsAnyDemographicDataAvailable(){
        return teamComposition.IsAnyDemographicDataAvailable();
    }
}
