package com.gap.metrics.controller;

import com.gap.metrics.model.Iteration;
import com.gap.metrics.model.Project;
import com.gap.metrics.model.ProjectIterationDetails;
import com.gap.metrics.dto.ProjectMetric;
import com.gap.metrics.service.IterationService;
import com.gap.metrics.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private IterationService iterationService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ResponseEntity<?> projects(){
        return new ResponseEntity<List<Project>>(projectService.listProjects(), HttpStatus.OK);
    }

    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable String id){
        return new ResponseEntity<Project>(projectService.getProject(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/project/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Project project){
        projectService.updateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Project iteration){
        Project prj = projectService.addProject(iteration);
        return new ResponseEntity<Project>(prj, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        projectService.deleteProject(id);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @RequestMapping(value = "/project/{projectId}/iteration/{iterationId}", method = RequestMethod.GET)
    public ResponseEntity<?>  fetchIteration(@PathVariable String projectId, @PathVariable String iterationId){
        ProjectIterationDetails details = projectService.getProjectIterationDetails(projectId, iterationId);
        return new ResponseEntity<ProjectIterationDetails>(details, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/{projectId}/iteration/{iterationId}", method = RequestMethod.POST)
    public ResponseEntity<?>  saveIteration(@RequestBody ProjectIterationDetails iterationDetails){
        return new ResponseEntity<ProjectIterationDetails>(projectService.updateProjectIterationDetails(iterationDetails), HttpStatus.OK);
    }

    @RequestMapping(value ="/project/{projectId}/velocities", method = RequestMethod.GET)
    public ResponseEntity<?> velocities(@PathVariable String projectId){
        List<ProjectIterationDetails> projectIterationDetails = projectService.findAllProjectIterationDetails(projectId);
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        ArrayList<Double> velocities = new ArrayList<Double>();
        for(Iteration iteration : iterations){
            for(ProjectIterationDetails details : projectIterationDetails){
                if (details.getIterationId().equals(iteration.getId())){
                    Double velocity = details.getVelocity();
                    velocities.add(velocity);
                }
            }
        }
        return new ResponseEntity<ArrayList<Double>>(velocities, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/averagevelocities", method = RequestMethod.GET)
    public ResponseEntity<?> averageVelocities(){
        List<ProjectIterationDetails> projectIterationDetails = projectService.findAllProjectIterationDetails("");
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        ProjectMetric metric = new ProjectMetric();
        List<Double> averageVelocities = new ArrayList<Double>();
        List<String> iterationNames = new ArrayList<String>();
        int numberOfProjects = 0;
        double totalVelocity = 0;

        for(Iteration iteration : iterations){
            numberOfProjects = 0;
            totalVelocity = 0;
            for(ProjectIterationDetails details : projectIterationDetails){
                if (details.getIterationId().equals(iteration.getId())){
                    numberOfProjects++;
                    totalVelocity += details.getVelocity();
                }
            }
            if (numberOfProjects > 0){
                averageVelocities.add(totalVelocity/numberOfProjects);
                iterationNames.add(iteration.getIterationNumber());
            }
        }

        metric.setAverageVelocities(averageVelocities);
        metric.setIterationNames(iterationNames);
        return new ResponseEntity<ProjectMetric>(metric, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/averagecycletimes", method = RequestMethod.GET)
    public ResponseEntity<?> averageCycleTimes(){
        List<ProjectIterationDetails> projectIterationDetails = projectService.findAllProjectIterationDetails("");
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        ProjectMetric metric = new ProjectMetric();
        List<Double> averageCycleTime = new ArrayList<Double>();
        List<String> iterationNames = new ArrayList<String>();
        int numberOfProjects = 0;
        double totalCycleTime = 0;

        for(Iteration iteration : iterations){
            numberOfProjects = 0;
            totalCycleTime = 0;
            for(ProjectIterationDetails details : projectIterationDetails){
                if (details.getIterationId().equals(iteration.getId())){
                    numberOfProjects++;
                    totalCycleTime += details.getCycleTime();
                }
            }
            if (numberOfProjects > 0){
                averageCycleTime.add(totalCycleTime/numberOfProjects);
                iterationNames.add(iteration.getIterationNumber());
            }
        }

        metric.setAverageCycleTimes(averageCycleTime);
        metric.setIterationNames(iterationNames);
        return new ResponseEntity<ProjectMetric>(metric, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/averageemployeecontractors", method = RequestMethod.GET)
    public ResponseEntity<?> averageEmployeesContractors(){
        List<ProjectIterationDetails> projectIterationDetails = projectService.findAllProjectIterationDetails("");
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        ProjectMetric metric = new ProjectMetric();
        List<Double> averageEmployees = new ArrayList<Double>();
        List<Double> averageContractors = new ArrayList<Double>();
        List<String> iterationNames = new ArrayList<String>();
        int numberOfProjects = 0;
        double totalEmployees = 0;
        double totalContractors = 0;

        for(Iteration iteration : iterations){
            numberOfProjects = 0;
            totalEmployees = 0;
            totalContractors = 0;
            for(ProjectIterationDetails details : projectIterationDetails){
                if (details.getIterationId().equals(iteration.getId())){
                    numberOfProjects++;
                    totalEmployees += details.getNumberOfFTE();
                    totalContractors += details.getNumberOfContractors();
                }
            }
            if (numberOfProjects > 0){
                averageEmployees.add(totalEmployees/numberOfProjects);
                averageContractors.add(totalContractors/numberOfProjects);
                iterationNames.add(iteration.getIterationNumber());
            }
        }

        metric.setAverageNoEmployees(averageEmployees);
        metric.setAverageNoContractors(averageContractors);
        metric.setIterationNames(iterationNames);
        return new ResponseEntity<ProjectMetric>(metric, HttpStatus.OK);
    }
}
