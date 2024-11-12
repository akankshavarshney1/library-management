package com.example.library_management.dto.Response;

import lombok.Data;

@Data
public class ResponseFormat {

    private int responseCode;
    private String responseMessage;
    private String status;
    private String comments;

    public ResponseFormat(int responseCode, String responseMessage, String status) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Response {" + "responseCode=" + responseCode + ", responseMessage=" + responseMessage + ", status="
                + status + ", comments=" + comments + "}";
    }

}

