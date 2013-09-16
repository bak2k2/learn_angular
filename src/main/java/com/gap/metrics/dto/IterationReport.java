package com.gap.metrics.dto;

import com.gap.metrics.model.Iteration;
import com.gap.metrics.model.Project;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document
public class IterationReport {
    private Iteration iteration;
    private List<Report> projectReports = new ArrayList<Report>();
}
