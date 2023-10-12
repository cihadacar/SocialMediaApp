package cihad.learning.socialmediaapp.services.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
    String message;
    Long userId;
}
