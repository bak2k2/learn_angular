package com.gap.metrics.controller;

import com.gap.metrics.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private com.gap.metrics.service.ProjectService personService;

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public ResponseEntity<?> Sample(){
        return new ResponseEntity<List<Project>>(personService.listProjects(), HttpStatus.OK);
    }
}
