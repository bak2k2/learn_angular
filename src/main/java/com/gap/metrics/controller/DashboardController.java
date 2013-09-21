package com.gap.metrics.controller;

import com.gap.metrics.builder.ProjectMetricBuilder;
import com.gap.metrics.dto.*;
import com.gap.metrics.model.*;
import com.gap.metrics.service.IterationService;
import com.gap.metrics.service.ProjectService;
import com.gap.metrics.service.RetroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class DashboardController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private IterationService iterationService;

    @Autowired
    private RetroService retroService;

    @RequestMapping(value ="/project/{projectId}/velocities", method = RequestMethod.GET)
    public ResponseEntity<?> velocities(@PathVariable String projectId){
        List<ProjectIterationDetail> projectIterationDetails = projectService.findAllProjectIterationDetails(projectId);
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        ArrayList<Double> velocities = new ArrayList<Double>();
        for(Iteration iteration : iterations){
            for(ProjectIterationDetail detail : projectIterationDetails){
                if (detail.getIterationId().equals(iteration.getId())){
                    velocities.add(detail.getVelocity());
                }
            }
        }
        return new ResponseEntity<ArrayList<Double>>(velocities, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/averagevelocities", method = RequestMethod.GET)
    public ResponseEntity<?> averageVelocities(){
        List<ProjectIterationDetail> projectIterationDetails = projectService.findAllProjectIterationDetails("");
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        ProjectMetric metric = new ProjectMetricBuilder().
                                    withIterations(iterations).
                                    withDetails(projectIterationDetails).
                                    buildTransitionVelocityMetric();

        return new ResponseEntity<ProjectMetric>(metric, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/averagecycletimes", method = RequestMethod.GET)
    public ResponseEntity<?> averageCycleTimes(){
        List<ProjectIterationDetail> projectIterationDetails = projectService.findAllProjectIterationDetails("");
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        ProjectMetric metric = new ProjectMetricBuilder().
                                    withIterations(iterations).
                                    withDetails(projectIterationDetails).
                                    buildAverageCycleTimeMetric();

        return new ResponseEntity<ProjectMetric>(metric, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/totalemployeecontractors", method = RequestMethod.GET)
    public ResponseEntity<?> averageEmployeesContractors(){
        List<ProjectIterationDetail> projectIterationDetails = projectService.findAllProjectIterationDetails("");
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        ProjectMetric metric = new ProjectMetricBuilder().
                                    withIterations(iterations).
                                    withDetails(projectIterationDetails).
                                    buildEmployeeContractorMetric();

        return new ResponseEntity<ProjectMetric>(metric, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/onoffnearshoredetails", method = RequestMethod.GET)
    public ResponseEntity<?> onOffNearShoreDetails(){
        List<Project> projects = projectService.listProjects();
        OnOffNearshoreDetails details = new OnOffNearshoreDetails();
        for(Project project : projects){
            Iteration iteration = project.getLastIteration();
            if (iteration != null){
                ProjectIterationDetail detail = projectService.getProjectIterationDetails(project.getId(), iteration.getId());
                if (detail != null && detail.IsAnyDemographicDataAvailable()){
                    TeamComposition teamComposition = detail.getTeamComposition();
                    details.getProjectNames().add(project.getProjectName());
                    populateTeamComposition(details, teamComposition);
                }
            }
        }

        return new ResponseEntity<OnOffNearshoreDetails>(details, HttpStatus.OK);
    }

    private void populateTeamComposition(OnOffNearshoreDetails details, TeamComposition teamComposition) {
        details.getOnShoreCount().add(teamComposition.getNumberOfOnshoreRes());
        details.getOffShoreIndiaCount().add(teamComposition.getNumberOfOffshoreResIndia());
        details.getOffShoreUkCount().add(teamComposition.getNumberOfOffshoreResUk());
        details.getNearShoreBrazilCount().add(teamComposition.getNumberOfNearshoreResBrazil());
        details.getNearShoreMexicoCount().add(teamComposition.getNumberOfNearshoreResMexico());
        details.getNearShoreChileCount().add(teamComposition.getNumberOfNearshoreResChile());
    }

    @RequestMapping(value = "/project/happinessmetrics", method = RequestMethod.GET)
    public ResponseEntity<?> happinessMetrics(){
        List<Project> projects = projectService.listProjects();
        HappinessMetrics happinessMetrics = new HappinessMetrics();
        for(Project project : projects){
            Iteration iteration = project.getLastIteration();
            if (iteration != null){
                ProjectIterationDetail detail = projectService.getProjectIterationDetails(project.getId(), iteration.getId());
                if (detail != null){
                    happinessMetrics.add(detail.getHappinessMetric());
                }
            }
        }

        return new ResponseEntity<HappinessMetric>(happinessMetrics.getAverageHappinessMetric(), HttpStatus.OK);
    }

    @RequestMapping(value = "/project/carryoverblockers", method = RequestMethod.GET)
    public ResponseEntity<?> carryoverBlockers(){
        List<ProjectIterationDetail> projectIterationDetails = projectService.findAllProjectIterationDetails("");
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        CarryoverBlockers metric = new CarryoverBlockers();
        int numberOfProjects = 0;
        double totalCarryOvers = 0, totalBlockers = 0;

        for(Iteration iteration : iterations){
            numberOfProjects = 0;
            totalCarryOvers = 0;
            totalBlockers = 0;
            for(ProjectIterationDetail detail : projectIterationDetails){
                if (detail.getIterationId().equals(iteration.getId())){
                    numberOfProjects++;
                    totalCarryOvers += detail.getNumberOfCarryOver();
                    totalBlockers += detail.getNumberOfBlockers();
                }
            }
            if (numberOfProjects > 0){
                metric.getCarryOvers().add(totalCarryOvers / numberOfProjects);
                metric.getBlockers().add(totalBlockers / numberOfProjects);
                metric.getIterationNames().add(iteration.getIterationNumber());
            }
        }

        return new ResponseEntity<CarryoverBlockers>(metric, HttpStatus.OK);
    }

    @RequestMapping( value = "/project/retro", method = RequestMethod.GET)
    public ResponseEntity<?> retro(){
        WordList wordList = retroService.getRetroWordList();
        return new ResponseEntity<WordList>(wordList, HttpStatus.OK);
    }
}
