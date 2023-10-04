package cihad.learning.socialmediaapp.services;

import cihad.learning.socialmediaapp.entities.User;
import cihad.learning.socialmediaapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
            foundUser.setUserName(newUser.getUserName());
            foundUser.setPassword(newUser.getPassword());
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
}
