package com.example.library_management.dto.Response;

import com.example.library_management.utils.Constants.ApplicationConstant;
import com.example.library_management.utils.ResponseCodeHandler;


public class GenericResponse <T> {


    public Response<T> createSuccessResponse( T responseObject ,String message, Integer responseCode){
        Response<T> response = new Response<>();
        response.setStatus(ApplicationConstant.SUCCESS);
        if(responseCode != null){
            response.setResponseCode(responseCode);
            response.setResponseMessage(message);
        }
        if(responseObject != null) {
            response.setPayload(responseObject);
        }
        return response;

    }

    public Response<T> createErrorResponse( T responseObject ,Integer errorCode){
        Response<T> response = new Response<>();
        response.setStatus(ApplicationConstant.FAILURE);
        if(errorCode != null){
            response.setErrorCode(errorCode);
            response.setResponseMessage(ResponseCodeHandler.getMessage(errorCode));
        }
        if(responseObject != null) {
            response.setPayload(responseObject);
        }
        return response;

    }



 }
