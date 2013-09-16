package com.gap.metrics.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class Project  implements Comparable<Project>{
    @Id
    private String id;
    private String projectName;
    private String projectDescription;
    private Iteration lastIteration;
    private String iterationManager;
    private String imEmailAddress;

    @Override
    public int compareTo(Project project) {
        return projectName.compareTo(project.projectName);
    }
}
