package cihad.learning.socialmediaapp.services;

import cihad.learning.socialmediaapp.entities.Comment;
import cihad.learning.socialmediaapp.entities.Post;
import cihad.learning.socialmediaapp.entities.User;
import cihad.learning.socialmediaapp.repositories.CommentRepository;
import cihad.learning.socialmediaapp.services.requests.CommentCreateRequest;
import cihad.learning.socialmediaapp.services.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getAllByParam(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent()) {
            return commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            return commentRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            return commentRepository.findByPostId(postId.get());
        } else {
            return commentRepository.findAll();
        }
    }

    public Comment getById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment add(CommentCreateRequest newCommentRequest) {
        User user = userService.getById(newCommentRequest.getUserId());
        Post post = postService.getById(newCommentRequest.getPostId());
        if (user != null && post != null) {
            Comment commentToSave = new Comment();
            commentToSave.setId(newCommentRequest.getId());
            commentToSave.setText(newCommentRequest.getText());
            commentToSave.setPost(post);
            commentToSave.setUser(user);
            commentToSave.setCreateDate(new Date());
            return commentRepository.save(commentToSave);
        } else {
            return null;
        }
    }

    public Comment update(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            Comment commentToUpdate = comment.get();
            commentToUpdate.setText(commentUpdateRequest.getText());
            return commentRepository.save(commentToUpdate);
        } else {
            return null;
        }
    }

    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
