package com.example.library_management.dto.Response;

import com.example.library_management.dto.Object.UserObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> implements Serializable {
    private Integer responseCode;
    private Integer errorCode;
    private String responseMessage;
    private String status;
    private T payload;
    private List<T> listPayload;
    private String comments;
    private Throwable throwable;

}

