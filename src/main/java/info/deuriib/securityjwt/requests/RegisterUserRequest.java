package info.deuriib.securityjwt.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterUserRequest(String username, String password) {
}
