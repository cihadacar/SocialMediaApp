package cihad.learning.socialmediaapp.repositories;

import cihad.learning.socialmediaapp.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

}
