package cihad.learning.socialmediaapp.controllers;

import cihad.learning.socialmediaapp.entities.Post;
import cihad.learning.socialmediaapp.services.PostService;
import cihad.learning.socialmediaapp.services.requests.PostCreateRequest;
import cihad.learning.socialmediaapp.services.requests.PostUpdateRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAll(@RequestParam Optional<Long> userId) {
        return postService.getAll(userId);
    }

    @GetMapping("/{postId}")
    public Post getById(@PathVariable Long postId) {
        return postService.getById(postId);
    }

    @PostMapping
    public Post create(@RequestBody PostCreateRequest newPostRequest) {
        return postService.add(newPostRequest);
    }

    @PutMapping("/{postId}")
    public Post update(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        return postService.update(postId, postUpdateRequest);
    }

    @DeleteMapping("/{postId}")
    public void deleteById(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
