package com.gap.metrics.controller;

import com.gap.metrics.model.Project;
import com.gap.metrics.model.ProjectIterationDetail;
import com.gap.metrics.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ResponseEntity<?> projects(){
        return new ResponseEntity<List<Project>>(projectService.listProjects(), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable String id){
        return new ResponseEntity<Project>(projectService.getProject(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> save(@RequestBody Project project){
        projectService.updateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Project iteration){
        Project prj = projectService.addProject(iteration);
        return new ResponseEntity<Project>(prj, HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        projectService.deleteProject(id);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @RequestMapping(value = "/project/{projectId}/iteration/{iterationId}", method = RequestMethod.GET)
    public ResponseEntity<?> fetchIteration(@PathVariable String projectId, @PathVariable String iterationId){
        ProjectIterationDetail details = projectService.getProjectIterationDetails(projectId, iterationId);
        return new ResponseEntity<ProjectIterationDetail>(details, HttpStatus.OK);
    }

    @RequestMapping(value = "/project/{projectId}/iteration/{iterationId}", method = RequestMethod.POST)
    public ResponseEntity<?>  saveIteration(@RequestBody ProjectIterationDetail iterationDetails){
        return new ResponseEntity<ProjectIterationDetail>(projectService.updateProjectIterationDetails(iterationDetails), HttpStatus.OK);
    }
}
