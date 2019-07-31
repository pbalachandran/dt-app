package com.corelogic.dt.controller;


import com.corelogic.dt.domain.NodeViewModel;
import com.corelogic.dt.domain.ProjectNotFoundException;
import com.corelogic.dt.domain.ProjectNotFoundResponse;
import com.corelogic.dt.domain.ProjectViewModel;
import com.corelogic.dt.service.QuestionaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questionaire")
public class QuestionaireController {

    private QuestionaireService questionaireService;

    @Autowired
    public QuestionaireController(QuestionaireService questionaireService) {
        this.questionaireService = questionaireService;
    }

    @GetMapping(value = "/project/names", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectViewModel>> retrieveProjectNames() {
        return ResponseEntity.ok(questionaireService.retrieveProjectNames());
    }

    @GetMapping(value = "/project/{projectName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NodeViewModel> retrieveProject(@PathVariable String projectName) throws ProjectNotFoundException {
        return ResponseEntity.ok(questionaireService.retrieveProject(projectName));
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ProjectNotFoundResponse> projectNotFound(ProjectNotFoundException exception) {
        return ResponseEntity.ok(ProjectNotFoundResponse.builder().message(exception.getMessage()).build());
    }
}