package com.gap.metrics.service;

import com.gap.metrics.model.Iteration;
import com.gap.metrics.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class IterationService {

    @Autowired
    private MongoOperations mongoOperation;

    public void setMongoOperation(MongoOperations mongoOperation) {
        this.mongoOperation = mongoOperation;
    }

    public static final String COLLECTION_NAME = "iteration";

    public Iteration addIteration(Iteration iteration) {
        if (!mongoOperation.collectionExists(Iteration.class)) {
            mongoOperation.createCollection(Iteration.class);
        }

        iteration.setId(UUID.randomUUID().toString());
        mongoOperation.insert(iteration, COLLECTION_NAME);
        return iteration;
    }

    public List<Iteration> listIterations() {
        List<Iteration> iterations = mongoOperation.findAll(Iteration.class, COLLECTION_NAME);
        return iterations;
    }

    public void updateIteration(Iteration iteration) {
        mongoOperation.save(iteration, COLLECTION_NAME);
    }

    public Iteration getIteration(String iterationId) {
        return mongoOperation.findById(iterationId, Iteration.class, COLLECTION_NAME);
    }

    public void deleteIteration(String iterationId){
        Query query = new Query(Criteria.where("_id").is(iterationId));
        mongoOperation.findAndRemove(query, Iteration.class);
    }
}
