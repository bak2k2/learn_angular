package com.gap.metrics.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class ProjectIterationDetails{
    @Id
    private String id;
    private String projectId;
    private String iterationId;
    private double velocity;
    private double transition;
    private double cycleTime;
    private double numberOfFTE;
    private double numberOfContractors;
    private double numberOfOffshoreResIndia;
    private double numberOfOffshoreResUk;
    private double numberOfOnshoreRes;
    private double numberOfNearshoreResChile;
    private double numberOfNearshoreResMexico;
    private double numberOfNearshoreResBrazil;
    private double commitment;
    private double engagement;
    private double perceivedValue;
    private double respectTrust;
    private double numberOfCarryOver;
    private double numberOfBlockers;
    private String retroComments;

    public boolean IsAnyDemographicDataAvailable(){
        return (numberOfOnshoreRes > 0 ||
                numberOfOffshoreResIndia > 0 ||
                numberOfOffshoreResUk > 0 ||
                numberOfNearshoreResBrazil > 0 ||
                numberOfNearshoreResChile > 0 ||
                numberOfNearshoreResMexico > 0
        );
    }
}
