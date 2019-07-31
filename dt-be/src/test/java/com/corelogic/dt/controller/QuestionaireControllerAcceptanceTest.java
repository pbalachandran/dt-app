package com.corelogic.dt.controller;

import com.corelogic.dt.DTBackendApplication;
import com.corelogic.dt.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {DTBackendApplication.class})
public class QuestionaireControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer server;

    @Value("${questionaire.microservice.url}")
    private String questionaireMicroserviceURL;

    @Before
    public void setUp() {
        server = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void retrieveProjectNames_returnProjectNames() throws Exception {
        server.expect(requestTo(questionaireMicroserviceURL + "/project/names"))
                .andRespond(withSuccess(TestUtils.readFixture("/service-responses/dt-names.json"),
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/questionaire/project/names")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/dt-names.json")));
    }

    @Test
    public void retrieveProject_returnsProject() throws Exception {
        server.expect(requestTo(questionaireMicroserviceURL + "/project/Paint%20Projects"))
                .andRespond(withSuccess(TestUtils.readFixture("/service-responses/dt-small.json"),
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/questionaire/project/Paint Projects")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/dt-small.json")));
    }


    @Test
    public void retrieveProject_withProjectThatDoesNotExist_returnsProjectNotFound() throws Exception {
        server.expect(requestTo(questionaireMicroserviceURL + "/project/Landscape%20Projects"))
                .andRespond(withSuccess(TestUtils.readFixture("/service-responses/dt-notfound.json"),
                        MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/questionaire/project/Landscape Projects")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TestUtils.readFixture("/responses/dt-notfound.json")));
    }
}