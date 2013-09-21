package com.gap.metrics.controller;

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

    @RequestMapping(value = "/project/{projectId}/iteration/{iterationId}", method = RequestMethod.GET)
    public ResponseEntity<?> fetchIteration(@PathVariable String projectId, @PathVariable String iterationId){
        ProjectIterationDetail details = projectService.getProjectIterationDetails(projectId, iterationId);
        return new ResponseEntity<ProjectIterationDetail>(details, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/{projectId}/iteration/{iterationId}", method = RequestMethod.POST)
    public ResponseEntity<?>  saveIteration(@RequestBody ProjectIterationDetail iterationDetails){
        return new ResponseEntity<ProjectIterationDetail>(projectService.updateProjectIterationDetails(iterationDetails), HttpStatus.OK);
    }

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
        ProjectMetric metric = new ProjectMetric();
        int numProjWithVelMoreThanZero = 0;
        int numProjWithTrans = 0;
        double totalVelocity = 0;
        double totalTransition = 0;

        for(Iteration iteration : iterations){
            numProjWithVelMoreThanZero = 0;
            numProjWithTrans = 0;
            totalVelocity = 0;
            totalTransition = 0;
            for(ProjectIterationDetail detail : projectIterationDetails){
                if (detail.getIterationId().equals(iteration.getId())){
                    if (detail.getVelocity() > 0){
                        numProjWithVelMoreThanZero++;
                        totalVelocity += detail.getVelocity();
                    }
                    numProjWithTrans++;
                    totalTransition += detail.getTransition();
                }
            }
            if (numProjWithTrans > 0 || numProjWithVelMoreThanZero > 0){
                metric.getAverageVelocities().add(totalVelocity / numProjWithVelMoreThanZero);
                metric.getAverageTransitions().add(totalTransition / numProjWithTrans);
                metric.getIterationNames().add(iteration.getIterationNumber());
            }
        }

        return new ResponseEntity<ProjectMetric>(metric, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/averagecycletimes", method = RequestMethod.GET)
    public ResponseEntity<?> averageCycleTimes(){
        List<ProjectIterationDetail> projectIterationDetails = projectService.findAllProjectIterationDetails("");
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        ProjectMetric metric = new ProjectMetric();
        int numberOfProjects = 0;
        double totalCycleTime = 0;

        for(Iteration iteration : iterations){
            numberOfProjects = 0;
            totalCycleTime = 0;
            for(ProjectIterationDetail detail : projectIterationDetails){
                if (detail.getIterationId().equals(iteration.getId()) && detail.getCycleTime() > 0){
                    numberOfProjects++;
                    totalCycleTime += detail.getCycleTime();
                }
            }
            if (numberOfProjects > 0){
                metric.getAverageCycleTimes().add(totalCycleTime / numberOfProjects);
                metric.getIterationNames().add(iteration.getIterationNumber());
            }
        }

        return new ResponseEntity<ProjectMetric>(metric, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/totalemployeecontractors", method = RequestMethod.GET)
    public ResponseEntity<?> averageEmployeesContractors(){
        List<ProjectIterationDetail> projectIterationDetails = projectService.findAllProjectIterationDetails("");
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        ProjectMetric metric = new ProjectMetric();
        int numberOfProjects = 0;
        double totalEmployees = 0, totalContractors = 0;

        for(Iteration iteration : iterations){
            numberOfProjects = 0;
            totalEmployees = 0;
            totalContractors = 0;
            for(ProjectIterationDetail detail : projectIterationDetails){
                TeamComposition teamComposition = detail.getTeamComposition();
                if (detail.getIterationId().equals(iteration.getId()) &&
                        (teamComposition.getNumberOfFTE() > 0 || teamComposition.getNumberOfContractors() > 0)){
                    numberOfProjects++;
                    totalEmployees += teamComposition.getNumberOfFTE();
                    totalContractors += teamComposition.getNumberOfContractors();
                }
            }
            if (numberOfProjects > 0){
                metric.getTotalNoEmployees().add(totalEmployees);
                metric.getTotalNoContractors().add(totalContractors);
                metric.getIterationNames().add(iteration.getIterationNumber());
            }
        }

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
