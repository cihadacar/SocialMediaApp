package cihad.learning.socialmediaapp.repositories;

import cihad.learning.socialmediaapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
