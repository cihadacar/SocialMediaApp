package cihad.learning.socialmediaapp.services.responses;

import cihad.learning.socialmediaapp.entities.Like;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllLikesResponse {
    private Long id;
    private Long userId;
    private Long postId;

    public GetAllLikesResponse(Like like) {
        this.id = like.getId();
        this.userId = like.getUser().getId();
        this.postId = like.getPost().getId();
    }
}
