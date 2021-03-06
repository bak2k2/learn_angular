package com.gap.metrics.service;

import java.util.List;
import java.util.UUID;

import com.gap.metrics.model.Project;
import com.gap.metrics.model.ProjectIterationDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class ProjectService {

    @Autowired
    private MongoOperations mongoOperation;

    public void setMongoOperation(MongoOperations mongoOperation) {
        this.mongoOperation = mongoOperation;
    }

    public static final String COLLECTION_NAME = "project";
    public static final String ITERATION_DETAILS_COLLECTION_NAME = "projectIterationDetails";

    public Project addProject(Project project) {
        if (!mongoOperation.collectionExists(Project.class)) {
            mongoOperation.createCollection(Project.class);
        }

        project.setId(UUID.randomUUID().toString());
        mongoOperation.insert(project, COLLECTION_NAME);
        return project;
    }

    public List<Project> listProjects() {
        List<Project> projects = mongoOperation.findAll(Project.class, COLLECTION_NAME);
        return projects;
    }

    public void deleteProject(String projectId) {
        Query query = new Query(Criteria.where("_id").is(projectId));
        mongoOperation.findAndRemove(query, Project.class);
    }

    public void updateProject(Project project) {
        mongoOperation.save(project, COLLECTION_NAME);
    }

    public Project getProject(String projectId) {
        return mongoOperation.findById(projectId, Project.class, COLLECTION_NAME);
    }

    public ProjectIterationDetail getProjectIterationDetails(String projectId, String iterationId){
        Query query = new Query(Criteria.where("projectId").is(projectId));
        query.addCriteria(Criteria.where("iterationId").is(iterationId));
        ProjectIterationDetail details = mongoOperation.findOne(query, ProjectIterationDetail.class, ITERATION_DETAILS_COLLECTION_NAME);
        if (details == null){
            details = new ProjectIterationDetail();
            details.setId(UUID.randomUUID().toString());
            details.setProjectId(projectId);
            details.setIterationId(iterationId);
        }
        return details;
    }

    public ProjectIterationDetail updateProjectIterationDetails(ProjectIterationDetail iterationDetails){
        mongoOperation.save(iterationDetails, ITERATION_DETAILS_COLLECTION_NAME);
        return iterationDetails;
    }

    public List<ProjectIterationDetail> findAllProjectIterationDetails(String projectId){
        Query query = new Query();
        if (!projectId.isEmpty())
            query.addCriteria(Criteria.where("projectId").is(projectId));
        List<ProjectIterationDetail> details = mongoOperation.find(query, ProjectIterationDetail.class, ITERATION_DETAILS_COLLECTION_NAME);
        return details;
    }
}
