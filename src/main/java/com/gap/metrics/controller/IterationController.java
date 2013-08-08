package com.gap.metrics.controller;

import com.gap.metrics.model.Iteration;
import com.gap.metrics.model.Project;
import com.gap.metrics.service.IterationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IterationController {

    @Autowired
    private IterationService iterationService;

    @RequestMapping(value = "/iterations", method = RequestMethod.GET)
    public ResponseEntity<?> projects(){
        return new ResponseEntity<List<Iteration>>(iterationService.listIterations(), HttpStatus.OK);
    }

    @RequestMapping(value = "/iteration/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable String id){
        return new ResponseEntity<Iteration>(iterationService.getIteration(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/iteration/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Iteration iteration){
        iterationService.updateIteration(iteration);
        return new ResponseEntity<Iteration>(iteration, HttpStatus.OK);
    }

    @RequestMapping(value = "/iteration", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Iteration iteration){
        Iteration iter = iterationService.addIteration(iteration);
        return new ResponseEntity<Iteration>(iter, HttpStatus.OK);
    }

    @RequestMapping(value = "/iteration/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        iterationService.deleteIteration(id);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
