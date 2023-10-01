package cihad.learning.socialmediaapp.services;

import cihad.learning.socialmediaapp.entities.Like;
import cihad.learning.socialmediaapp.entities.Post;
import cihad.learning.socialmediaapp.entities.User;
import cihad.learning.socialmediaapp.repositories.LikeRepository;
import cihad.learning.socialmediaapp.services.requests.LikeCreateRequest;
import cihad.learning.socialmediaapp.services.responses.GetAllLikesResponse;
import cihad.learning.socialmediaapp.services.responses.GetAllPostsResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    @Lazy
    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<GetAllLikesResponse> getAllByParam(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()) {
            list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        }else if(postId.isPresent()) {
            list = likeRepository.findByPostId(postId.get());
        }else
            list = likeRepository.findAll();
        return list.stream().map(like -> new GetAllLikesResponse(like)).collect(Collectors.toList());
    }

    public Like getById(Long LikeId) {
        return likeRepository.findById(LikeId).orElse(null);
    }

    public Like add(LikeCreateRequest request) {
        User user = userService.getById(request.getUserId());
        Post post = postService.getById(request.getPostId());
        if(user != null && post != null) {
            Like likeToSave = new Like();
            likeToSave.setId(request.getId());
            likeToSave.setPost(post);
            likeToSave.setUser(user);
            return likeRepository.save(likeToSave);
        }else
            return null;
    }

    public void delete(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
