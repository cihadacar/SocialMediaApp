package cihad.learning.socialmediaapp.services.responses;

import cihad.learning.socialmediaapp.entities.User;
import lombok.Data;

@Data
public class UserResponse {
    Long id;
    String userName;
    int avatarId;

    public UserResponse(User entity){
        this.id = entity.getId();
        this.userName = entity.getUserName();
        this.avatarId = entity.getAvatar();
    }
}
