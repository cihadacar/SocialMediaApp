package cihad.learning.socialmediaapp.services;

import cihad.learning.socialmediaapp.entities.Like;
import cihad.learning.socialmediaapp.entities.Post;
import cihad.learning.socialmediaapp.entities.User;
import cihad.learning.socialmediaapp.repositories.PostRepository;
import cihad.learning.socialmediaapp.services.requests.PostCreateRequest;
import cihad.learning.socialmediaapp.services.requests.PostUpdateRequest;
import cihad.learning.socialmediaapp.services.responses.GetAllLikesResponse;
import cihad.learning.socialmediaapp.services.responses.GetAllPostsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserService userService;
    private LikeService likeService;

    @Lazy
    public PostService(PostRepository postRepository, UserService userService, LikeService likeService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.likeService = likeService;
    }
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }

    public List<GetAllPostsResponse> getAll(Optional<Long> userId) {
        List<Post> postList;
        if (userId.isPresent()) {
            postList = postRepository.findByUserId(userId.get());
        } else
            postList = postRepository.findAll();
        return postList.stream().map(p -> {
            List<GetAllLikesResponse> likes = likeService.getAllByParam(Optional.ofNullable(null), Optional.of(p.getId()));
            return new GetAllPostsResponse(p, likes);
        }).collect(Collectors.toList());
    }

    public Post getById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post add(PostCreateRequest newPostRequest) {
        User user = userService.getById(newPostRequest.getUserId());
        if (user == null) {
            return null;
        }
        Post postToSave = new Post();
        postToSave.setId(newPostRequest.getId());
        postToSave.setTitle(newPostRequest.getTitle());
        postToSave.setText(newPostRequest.getText());
        postToSave.setUser(user);
        return postRepository.save(postToSave);
    }

    public Post update(Long postId, PostUpdateRequest postUpdateRequest) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            Post postToUpdate = post.get();
            postToUpdate.setTitle(postUpdateRequest.getTitle());
            postToUpdate.setText(postUpdateRequest.getText());
            postRepository.save(postToUpdate);
            return postToUpdate;
        }
        return null;
    }

    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }
}
