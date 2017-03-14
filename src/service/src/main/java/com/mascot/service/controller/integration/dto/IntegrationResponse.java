package com.mascot.service.controller.integration.dto;

/**
 * Created by Nikolay on 14.03.2017.
 */
public class IntegrationResponse {
    public IntegrationResultType status;
    public String error;

    private IntegrationResponse(IntegrationResultType status) {
        this.status = status;
    }

    public static IntegrationResponse SUCCESS = new IntegrationResponse(IntegrationResultType.SUCCESS);

}
