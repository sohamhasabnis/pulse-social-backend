package com.pulse.common.dto;

import java.time.ZonedDateTime;

public class ErrorResponseDTO {
    private final ZonedDateTime timestamp;
    private final String status;
    private final String error;
    private final String message;
    private final String path;

    public ErrorResponseDTO(ZonedDateTime timestamp, String status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
