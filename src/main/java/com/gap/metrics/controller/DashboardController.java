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
                metric.getAverageVelocities().add(totalVelocity / numberOfProjects);
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
                if (details.getIterationId().equals(iteration.getId())){
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
                if (detail != null){
                    details.getProjectNames().add(project.getProjectName());
                    details.getOnShoreCount().add(detail.getNumberOfOnshoreRes());
                    details.getOffShoreCount().add(detail.getNumberOfOffshoreRes());
                    details.getNearShoreCount().add(detail.getNumberOfNearshoreRes());
                }
            }
        }
        return new ResponseEntity<OnOffNearshoreDetails>(details, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/happinessmetrics", method = RequestMethod.GET)
    public ResponseEntity<?> happinessMetrics(){
        List<Project> projects = projectService.listProjects();
        HappinessMetric metric = new HappinessMetric();
        double commitment = 0;
        double engagement = 0;
        double perceivedValue = 0;
        double respectTrust = 0;
        int numberOfProjects = 0;

        for(Project project : projects){
            Iteration iteration = project.getLastIteration();
            if (iteration != null){
                ProjectIterationDetails detail = projectService.getProjectIterationDetails(project.getId(), iteration.getId());
                if (detail != null){
                    numberOfProjects++;
                    commitment += detail.getCommitment();
                    engagement += detail.getEngagement();
                    perceivedValue += detail.getPerceivedValue();
                    respectTrust += detail.getRespectTrust();
                }
            }
        }
        metric.setCommitment(Math.ceil(commitment/numberOfProjects));
        metric.setEngagement(Math.ceil(engagement/numberOfProjects));
        metric.setPerceivedValue(Math.ceil(perceivedValue/numberOfProjects));
        metric.setRespectTrust(Math.ceil(respectTrust/numberOfProjects));
        return new ResponseEntity<HappinessMetric>(metric, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/carryoverblockers", method = RequestMethod.GET)
    public ResponseEntity<?> carryoverBlockers(){
        List<ProjectIterationDetails> projectIterationDetails = projectService.findAllProjectIterationDetails("");
        List<Iteration> iterations = iterationService.listIterations();
        Collections.sort(iterations);
        CarryoverBlockers metric = new CarryoverBlockers();
        int numberOfProjects = 0;
        double totalCarryOvers = 0;
        double totalBlockers = 0;

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
                metric.getIterationName().add(iteration.getIterationNumber());
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
        String[] words = str.toLowerCase().split("[^\\p{L}]+");
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
        String retroComments = "";
        for(Project project : projects){
            Iteration iteration = project.getLastIteration();
            if (iteration != null){
                ProjectIterationDetails detail = projectService.getProjectIterationDetails(project.getId(), iteration.getId());
                if (detail != null){
                    retroComments += detail.getRetroComments();
                }
            }
        }
        return retroComments;
    }
}
