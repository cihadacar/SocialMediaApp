package cihad.learning.socialmediaapp.services.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreateRequest {
    private Long id;
    private String title;
    private String text;
    private Long userId;
}
