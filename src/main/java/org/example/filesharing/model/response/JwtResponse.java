package org.example.filesharing.model.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String accessLevel;

    public JwtResponse(String token, String accessLevel) {
        this.token = token;
        this.accessLevel = accessLevel;
    }
}
