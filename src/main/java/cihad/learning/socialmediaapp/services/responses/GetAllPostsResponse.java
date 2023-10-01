package cihad.learning.socialmediaapp.services.responses;

import cihad.learning.socialmediaapp.entities.Like;
import cihad.learning.socialmediaapp.entities.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllPostsResponse {
    Long id;
    Long userId;
    String userName;
    String title;
    String text;
    List<GetAllLikesResponse> postLikes;

    public GetAllPostsResponse(Post post, List<GetAllLikesResponse> likes) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.userName = post.getUser().getUserName();
        this.title = post.getTitle();
        this.text = post.getText();
        this.postLikes = likes;
    }
}
