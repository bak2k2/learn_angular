package com.gap.metrics.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Document
public class OnOffNearshoreDetails {
    List<String> projectNames;
    List<Double> onShoreCount;
    List<Double> offShoreCount;
    List<Double> nearShoreCount;
}
