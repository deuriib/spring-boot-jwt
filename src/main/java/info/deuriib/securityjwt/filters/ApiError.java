package info.deuriib.securityjwt.filters;

import org.springframework.http.HttpStatus;

public record ApiError(HttpStatus status, Throwable ex) {
    public String convertToJson() {
        return String.format("{\"status\": \"%s\", \"message\": \"%s\"}", status, ex.getMessage());
    }
}
