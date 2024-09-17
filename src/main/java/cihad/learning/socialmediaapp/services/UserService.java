package cihad.learning.socialmediaapp.services;

import cihad.learning.socialmediaapp.entities.Comment;
import cihad.learning.socialmediaapp.entities.Like;
import cihad.learning.socialmediaapp.entities.User;
import cihad.learning.socialmediaapp.repositories.CommentRepository;
import cihad.learning.socialmediaapp.repositories.LikeRepository;
import cihad.learning.socialmediaapp.repositories.PostRepository;
import cihad.learning.socialmediaapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private LikeRepository likeRepository;

    public UserService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, LikeRepository likeRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User add(User user) {
        return userRepository.save(user);
    }

    public User getById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    public User update(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setAvatar(newUser.getAvatar());

            userRepository.save(foundUser);
            return foundUser;
        }//custom exception
        else {
            return null;
        }
    }
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
    public User getByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public List<Object> getUserActivity(Long userId) {
        List<Long> postIds = postRepository.findTopByUserId(userId);
        if (postIds.isEmpty())
            return null;
        List<Object> comments = commentRepository.findUserCommentsByPostId(postIds);
        List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
        List<Object> result = new ArrayList<>();
        result.addAll(comments);
        result.addAll(likes);
        return result;
     }
}
