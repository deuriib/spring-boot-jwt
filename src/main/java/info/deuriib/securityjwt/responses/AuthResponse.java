package info.deuriib.securityjwt.responses;

import java.util.Date;

public record AuthResponse(String accessToken, Date expiresIn, String tokenType) {
    public AuthResponse(String accessToken, Date expiresIn) {
        this(accessToken, expiresIn, "Bearer");
    }
}
