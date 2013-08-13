package com.gap.metrics.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document
public class OnOffNearshoreDetails {
    List<String> projectNames = new ArrayList<String>();
    List<Double> onShoreCount = new ArrayList<Double>();
    List<Double> offShoreCount = new ArrayList<Double>();
    List<Double> nearShoreCount = new ArrayList<Double>();
}
