package com.gap.metrics.controller;

import com.gap.metrics.dto.*;
import com.gap.metrics.model.Iteration;
import com.gap.metrics.model.Project;
import com.gap.metrics.model.ProjectIterationDetails;
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

import java.util.*;

@Controller
public class DashboardController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private IterationService iterationService;

    @RequestMapping(value = "/project/{projectId}/iteration/{iterationId}", method = RequestMethod.GET)
    public ResponseEntity<?> fetchIteration(@PathVariable String projectId, @PathVariable String iterationId){
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
        int numProjWithVelMoreThanZero = 0;
        int numProjWithTrans = 0;
        double totalVelocity = 0;
        double totalTransition = 0;

        for(Iteration iteration : iterations){
            numProjWithVelMoreThanZero = 0;
            numProjWithTrans = 0;
            totalVelocity = 0;
            totalTransition = 0;
            for(ProjectIterationDetails details : projectIterationDetails){
                if (details.getIterationId().equals(iteration.getId())){
                    if (details.getVelocity() > 0){
                        numProjWithVelMoreThanZero++;
                        totalVelocity += details.getVelocity();
                    }
                    numProjWithTrans++;
                    totalTransition += details.getTransition();
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
        List<ProjectIterationDetails> projectIterationDetails = projectService.findAllProjectIterationDetails("");
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        ProjectMetric metric = new ProjectMetric();
        int numberOfProjects = 0;
        double totalCycleTime = 0;

        for(Iteration iteration : iterations){
            numberOfProjects = 0;
            totalCycleTime = 0;
            for(ProjectIterationDetails details : projectIterationDetails){
                if (details.getIterationId().equals(iteration.getId()) && details.getCycleTime() > 0){
                    numberOfProjects++;
                    totalCycleTime += details.getCycleTime();
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
        List<ProjectIterationDetails> projectIterationDetails = projectService.findAllProjectIterationDetails("");
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        ProjectMetric metric = new ProjectMetric();
        int numberOfProjects = 0;
        double totalEmployees = 0, totalContractors = 0;

        for(Iteration iteration : iterations){
            numberOfProjects = 0;
            totalEmployees = 0;
            totalContractors = 0;
            for(ProjectIterationDetails details : projectIterationDetails){
                if (details.getIterationId().equals(iteration.getId()) &&
                        (details.getNumberOfFTE() > 0 || details.getNumberOfContractors() > 0)){
                    numberOfProjects++;
                    totalEmployees += details.getNumberOfFTE();
                    totalContractors += details.getNumberOfContractors();
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
                ProjectIterationDetails detail = projectService.getProjectIterationDetails(project.getId(), iteration.getId());
                if (detail != null && detail.IsAnyDemographicDataAvailable()){
                    details.getProjectNames().add(project.getProjectName());
                    details.getOnShoreCount().add(detail.getNumberOfOnshoreRes());
                    details.getOffShoreIndiaCount().add(detail.getNumberOfOffshoreResIndia());
                    details.getOffShoreUkCount().add(detail.getNumberOfOffshoreResUk());
                    details.getNearShoreBrazilCount().add(detail.getNumberOfNearshoreResBrazil());
                    details.getNearShoreMexicoCount().add(detail.getNumberOfNearshoreResMexico());
                    details.getNearShoreChileCount().add(detail.getNumberOfNearshoreResChile());
                }
            }
        }
        return new ResponseEntity<OnOffNearshoreDetails>(details, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/happinessmetrics", method = RequestMethod.GET)
    public ResponseEntity<?> happinessMetrics(){
        List<Project> projects = projectService.listProjects();
        HappinessMetric metric = new HappinessMetric();
        double commitment = 0, engagement = 0, perceivedValue = 0, respectTrust = 0;
        int numProjWithCommMoreThanZero = 0;
        int numProjWithEngMoreThanZero = 0;
        int numProjWithValMoreThanZero = 0;
        int numProjWithTrstMoreThanZero = 0;

        for(Project project : projects){
            Iteration iteration = project.getLastIteration();
            if (iteration != null){
                ProjectIterationDetails detail = projectService.getProjectIterationDetails(project.getId(), iteration.getId());
                if (detail != null){
                    if (detail.getCommitment() > 0){
                        numProjWithCommMoreThanZero++;
                        commitment += detail.getCommitment();
                    }
                    if (detail.getEngagement() > 0){
                        numProjWithEngMoreThanZero++;
                        engagement += detail.getEngagement();
                    }
                    if (detail.getPerceivedValue() > 0){
                        numProjWithValMoreThanZero++;
                        perceivedValue += detail.getPerceivedValue();
                    }
                    if (detail.getRespectTrust() > 0){
                        numProjWithTrstMoreThanZero++;
                        respectTrust += detail.getRespectTrust();
                    }
                }
            }
        }
        metric.setCommitment(Math.round(commitment/numProjWithCommMoreThanZero));
        metric.setEngagement(Math.round(engagement/numProjWithEngMoreThanZero));
        metric.setPerceivedValue(Math.round(perceivedValue/numProjWithValMoreThanZero));
        metric.setRespectTrust(Math.round(respectTrust/numProjWithTrstMoreThanZero));
        return new ResponseEntity<HappinessMetric>(metric, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/carryoverblockers", method = RequestMethod.GET)
    public ResponseEntity<?> carryoverBlockers(){
        List<ProjectIterationDetails> projectIterationDetails = projectService.findAllProjectIterationDetails("");
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        CarryoverBlockers metric = new CarryoverBlockers();
        int numberOfProjects = 0;
        double totalCarryOvers = 0, totalBlockers = 0;

        for(Iteration iteration : iterations){
            numberOfProjects = 0;
            totalCarryOvers = 0;
            totalBlockers = 0;
            for(ProjectIterationDetails details : projectIterationDetails){
                if (details.getIterationId().equals(iteration.getId())){
                    numberOfProjects++;
                    totalCarryOvers += details.getNumberOfCarryOver();
                    totalBlockers += details.getNumberOfBlockers();
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
        WordList wordList = new WordList();
        String retroComments = getRetroComments(projectService.listProjects());
        Map<String, Double> wordCounts = getWordCountsMap(retroComments);
        wordList.setWordCounts(getWordCounts(wordCounts));
        return new ResponseEntity<WordList>(wordList, HttpStatus.OK);
    }

    private List<WordCount> getWordCounts(Map<String, Double> wordCountMap) {
        List<WordCount> wordCounts = new ArrayList<WordCount>();
        Iterator iterator = wordCountMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry pairs = (Map.Entry)iterator.next();
            WordCount wc = new WordCount(pairs.getKey().toString(), (Double)pairs.getValue());
            wordCounts.add(wc);
        }
        return wordCounts;
    }

    private Map<String, Double> getWordCountsMap(String str) {
        String[] words = str.toLowerCase().split(" ");
        Map<String, Double> wordCounts = new HashMap<String, Double>();

        for (String word : words) {
            Double count = wordCounts.get(word);
            if (count == null) {
                count = 0.0;
            }
            if (word.length() > 3)
                wordCounts.put(word, count + 1);
        }
        return wordCounts;
    }

    private String getRetroComments(List<Project> projects) {
        StringBuilder retroComments = new StringBuilder();
        for(Project project : projects){
            Iteration iteration = project.getLastIteration();
            if (iteration != null){
                ProjectIterationDetails detail = projectService.getProjectIterationDetails(project.getId(), iteration.getId());
                if (detail != null && detail.getRetroComments() != null){
                    retroComments.append(" " +detail.getRetroComments());
                }
            }
        }
        return retroComments.toString();
    }
}
