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
}
