package cihad.learning.socialmediaapp.repositories;

import cihad.learning.socialmediaapp.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Ref;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByUserId(Long userId);
}
