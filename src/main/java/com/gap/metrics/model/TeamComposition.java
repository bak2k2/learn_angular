package com.gap.metrics.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class TeamComposition {
    private double numberOfFTE;
    private double numberOfContractors;
    private double numberOfOffshoreResIndia;
    private double numberOfOffshoreResUk;
    private double numberOfOnshoreRes;
    private double numberOfNearshoreResChile;
    private double numberOfNearshoreResMexico;
    private double numberOfNearshoreResBrazil;

    public boolean IsAnyDemographicDataAvailable() {
        return (numberOfOnshoreRes > 0 ||
                numberOfOffshoreResIndia > 0 ||
                numberOfOffshoreResUk > 0 ||
                numberOfNearshoreResBrazil > 0 ||
                numberOfNearshoreResChile > 0 ||
                numberOfNearshoreResMexico > 0
        );
    }
}
