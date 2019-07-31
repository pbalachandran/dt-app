package com.corelogic.dt.service;

import com.corelogic.dt.domain.NodeResponse;
import com.corelogic.dt.domain.NodeViewModel;
import com.corelogic.dt.domain.ProjectNotFoundException;
import com.corelogic.dt.domain.ProjectNotFoundResponse;
import com.corelogic.dt.domain.ProjectResponse;
import com.corelogic.dt.domain.ProjectViewModel;
import com.corelogic.dt.domain.QuestionaireResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionaireServiceTest {

    private QuestionaireService subject;

    @Mock
    private RestTemplate mockRestTemplate;

    @Before
    public void setUp() {
        String questionaireMicroserviceURL = "http://localhost:9090/api/questionaire";
        subject = new QuestionaireService(
                questionaireMicroserviceURL,
                mockRestTemplate
        );
    }

    @Test
    public void retrieveProjectNames_returnsProjectNames() {
        QuestionaireResponse questionaireResponse = QuestionaireResponse
                .builder()
                .projectResponses(
                        Arrays.asList(
                                ProjectResponse.builder()
                                        .name("Paint Projects")
                                        .build(),
                                ProjectResponse.builder()
                                        .name("Home Remodel Projects")
                                        .build()
                        )
                )
                .build();

        when(mockRestTemplate.exchange("http://localhost:9090/api/questionaire/project/names",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<QuestionaireResponse>() {
                }))
                .thenReturn(ResponseEntity.ok(questionaireResponse));

        List<ProjectViewModel> actualProjectViewModels = subject.retrieveProjectNames();

        verify(mockRestTemplate).exchange("http://localhost:9090/api/questionaire/project/names",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<QuestionaireResponse>() {
                });

        List<ProjectViewModel> expected =
                Arrays.asList(
                        ProjectViewModel.builder()
                                .name("Paint Projects")
                                .build(),
                        ProjectViewModel.builder()
                                .name("Home Remodel Projects")
                                .build()
                );

        assertEquals(expected, actualProjectViewModels);
    }

    @Test
    public void retrieveProject_returnsProject() throws ProjectNotFoundException {
        QuestionaireResponse questionaireResponse = QuestionaireResponse.builder().nodeResponse(
                NodeResponse.builder()
                        .name("Paint Projects")
                        .children(Arrays.asList(
                                NodeResponse.builder()
                                        .name("Home Repaint Inside")
                                        .children(
                                                Arrays.asList(
                                                        NodeResponse
                                                                .builder()
                                                                .name("Paint-3000")
                                                                .children(Collections.EMPTY_LIST)
                                                                .build(),
                                                        NodeResponse
                                                                .builder()
                                                                .name("Paint-3000+")
                                                                .children(Collections.EMPTY_LIST)
                                                                .build()
                                                )).build())
                        ).build())
                .build();

        when(mockRestTemplate.exchange("http://localhost:9090/api/questionaire/project/Paint Projects",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<QuestionaireResponse>() {
                })).thenReturn(ResponseEntity.ok(questionaireResponse));

        NodeViewModel actualNodeViewModel = subject.retrieveProject("Paint Projects");

        verify(mockRestTemplate).exchange("http://localhost:9090/api/questionaire/project/Paint Projects",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<QuestionaireResponse>() {
                });

        NodeViewModel expected = NodeViewModel.builder()
                .name("Paint Projects")
                .children(Arrays.asList(
                        NodeViewModel.builder()
                                .name("Home Repaint Inside")
                                .children(
                                        Arrays.asList(
                                                NodeViewModel
                                                        .builder()
                                                        .name("Paint-3000")
                                                        .children(Collections.EMPTY_LIST)
                                                        .build(),
                                                NodeViewModel
                                                        .builder()
                                                        .name("Paint-3000+")
                                                        .children(Collections.EMPTY_LIST)
                                                        .build()
                                        )).build())
                ).build();

        assertEquals(expected, actualNodeViewModel);
    }

    @Test(expected = ProjectNotFoundException.class)
    public void retriveProject_whenProjectNotFound_throwsProjectNotFoundException() throws ProjectNotFoundException {
        when(mockRestTemplate.exchange("http://localhost:9090/api/questionaire/project/Landscape Projects",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<QuestionaireResponse>() {
                })).thenReturn(
                ResponseEntity.ok(
                        QuestionaireResponse.builder().projectNotFoundResponse(
                                ProjectNotFoundResponse
                                        .builder()
                                        .message("Could not find Landscape Projects")
                                        .build())
                                .build()
                )
        );

        subject.retrieveProject("Landscape Projects");
    }
}