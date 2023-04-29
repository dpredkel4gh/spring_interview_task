package com.yellow.test.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class CommonErrorDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("status")
    private int status;

    @JsonProperty("title")
    private String title;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("exception")
    private String exception;

    @JsonProperty("method")
    private String method;

    @JsonProperty("requestedPath")
    private String requestedPath;

}