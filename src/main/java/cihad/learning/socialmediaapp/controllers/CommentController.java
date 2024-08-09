package cihad.learning.socialmediaapp.controllers;

import cihad.learning.socialmediaapp.entities.Comment;
import cihad.learning.socialmediaapp.services.CommentService;
import cihad.learning.socialmediaapp.services.requests.CommentCreateRequest;
import cihad.learning.socialmediaapp.services.requests.CommentUpdateRequest;
import cihad.learning.socialmediaapp.services.responses.CommentResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponse> getAll(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
        return commentService.getAllByParam(userId, postId);
    }

    @GetMapping("/{commentId}")
    public Comment getById(@PathVariable Long commentId) {
        return commentService.getById(commentId);
    }

    @PostMapping
    public Comment create(@RequestBody CommentCreateRequest newCommentRequest) {
        return commentService.add(newCommentRequest);
    }

    @PutMapping("/{commentId}")
    public Comment update(@PathVariable Long commentId, @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return commentService.update(commentId, commentUpdateRequest);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }

}
