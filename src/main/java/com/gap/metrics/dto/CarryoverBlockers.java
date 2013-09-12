package com.gap.metrics.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document
public class CarryoverBlockers {
    private List<String> iterationNames = new ArrayList<String>();
    private List<Double> carryOvers = new ArrayList<Double>();
    private List<Double> blockers = new ArrayList<Double>();
}
