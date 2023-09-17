package cihad.learning.socialmediaapp.repositories;

import cihad.learning.socialmediaapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
