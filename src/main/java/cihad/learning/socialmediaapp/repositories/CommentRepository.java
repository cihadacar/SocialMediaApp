package cihad.learning.socialmediaapp.repositories;

import cihad.learning.socialmediaapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
