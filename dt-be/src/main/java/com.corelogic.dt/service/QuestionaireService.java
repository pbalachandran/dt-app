package com.corelogic.dt.service;

import com.corelogic.dt.domain.NodeResponse;
import com.corelogic.dt.domain.NodeViewModel;
import com.corelogic.dt.domain.ProjectNotFoundException;
import com.corelogic.dt.domain.ProjectNotFoundResponse;
import com.corelogic.dt.domain.ProjectViewModel;
import com.corelogic.dt.domain.QuestionaireResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionaireService {

    private String questionaireMicroserviceURL;

    private RestTemplate restTemplate;

    @Autowired
    public QuestionaireService(String questionaireMicroserviceURL, RestTemplate restTemplate) {
        this.questionaireMicroserviceURL = questionaireMicroserviceURL;
        this.restTemplate = restTemplate;
    }

    public List<ProjectViewModel> retrieveProjectNames() {
        QuestionaireResponse questionaireResponse = restTemplate.exchange(questionaireMicroserviceURL + "/project/names",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<QuestionaireResponse>() {
                }).getBody();

        return questionaireResponse.getProjectResponses()
                .stream()
                .map(projectResponse -> ProjectViewModel
                        .builder()
                        .name(projectResponse.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public NodeViewModel retrieveProject(String projectName) throws ProjectNotFoundException {
        QuestionaireResponse response = restTemplate.exchange(questionaireMicroserviceURL + "/project/" + projectName,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<QuestionaireResponse>() {
                }).getBody();

        if(response.getNodeResponse() != null) {
            return buildNodeViewModel(response.getNodeResponse());
        } else {
            ProjectNotFoundResponse projectNotFoundResponse = response.getProjectNotFoundResponse();
            throw new ProjectNotFoundException(projectNotFoundResponse.getMessage());
        }
    }

    private NodeViewModel buildNodeViewModel(NodeResponse nodeResponse) {
        return NodeViewModel
                .builder()
                .name(nodeResponse.getName())
                .children(
                        nodeResponse
                                .getChildren()
                                .stream()
                                .map(child -> buildNodeViewModel(child)).collect(Collectors.toList()
                        )
                )
                .build();
    }
}

