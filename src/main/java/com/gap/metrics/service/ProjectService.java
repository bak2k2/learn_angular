package com.gap.metrics.service;

import java.util.List;
import java.util.UUID;

import com.gap.metrics.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ProjectService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static final String COLLECTION_NAME = "project";

    public void addProject(Project project) {
        if (!mongoTemplate.collectionExists(Project.class)) {
            mongoTemplate.createCollection(Project.class);
        }

        project.setId(UUID.randomUUID().toString());
        mongoTemplate.insert(project, COLLECTION_NAME);
    }

    public List<Project> listProjects() {
        return mongoTemplate.findAll(Project.class, COLLECTION_NAME);
    }

    public void deleteProject(Project project) {
        mongoTemplate.remove(project, COLLECTION_NAME);
    }

    public void updateProject(Project project) {
        mongoTemplate.insert(project, COLLECTION_NAME);
    }
}
