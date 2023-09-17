package cihad.learning.socialmediaapp.services;

import cihad.learning.socialmediaapp.entities.Post;
import cihad.learning.socialmediaapp.entities.User;
import cihad.learning.socialmediaapp.repositories.PostRepository;
import cihad.learning.socialmediaapp.services.requests.PostCreateRequest;
import cihad.learning.socialmediaapp.services.requests.PostUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getAll(Optional<Long> userId) {
        if (userId.isPresent()) {
            return postRepository.findByUserId(userId.get());
        }
        return postRepository.findAll();
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
