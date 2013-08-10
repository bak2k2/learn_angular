package com.gap.metrics.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Document
public class CarryoverBlockers {
    private List<String> iterationNames;
    private List<Double> carryOvers;
    private List<Double> blockers;
}
