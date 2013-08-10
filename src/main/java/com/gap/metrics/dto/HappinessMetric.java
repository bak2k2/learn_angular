package com.gap.metrics.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class HappinessMetric {
    private double commitment;
    private double engagement;
    private double perceivedValue;
    private double respectTrust;
}
