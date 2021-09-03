package com.example.clip.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PopulationResponse {

    private Integer createdUsers;
    private Integer createdPaymentsPerUser;

}
