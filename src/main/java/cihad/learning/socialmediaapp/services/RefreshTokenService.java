package cihad.learning.socialmediaapp.services;

import cihad.learning.socialmediaapp.entities.RefreshToken;
import cihad.learning.socialmediaapp.entities.User;
import cihad.learning.socialmediaapp.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService  {

    @Value("${refresh.token.expires.in}")
    Long expireSeconds;

    private RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository){
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createRefreshToken(User user){
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId());
        if (refreshToken == null){
            refreshToken = new RefreshToken();
            refreshToken.setUser(user);
        }
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    public boolean isRefreshExpired(RefreshToken refreshToken){
        return refreshToken.getExpiryDate().before(new Date());
    }

    public RefreshToken getByUser(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }
}
