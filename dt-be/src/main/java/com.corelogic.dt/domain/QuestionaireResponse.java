package com.corelogic.dt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionaireResponse {
    private List<ProjectResponse> projectResponses;
    private NodeResponse nodeResponse;
    private ProjectNotFoundResponse projectNotFoundResponse;
}
