package com.gap.metrics.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class HappinessMetric {
    private double commitment = 0;
    private double engagement = 0;
    private double perceivedValue = 0;
    private double respectTrust = 0;
}
