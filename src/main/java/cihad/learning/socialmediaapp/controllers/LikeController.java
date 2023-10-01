package cihad.learning.socialmediaapp.controllers;

import cihad.learning.socialmediaapp.entities.Like;
import cihad.learning.socialmediaapp.services.LikeService;
import cihad.learning.socialmediaapp.services.requests.LikeCreateRequest;
import cihad.learning.socialmediaapp.services.responses.GetAllLikesResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<GetAllLikesResponse> getAll(@RequestParam Optional<Long> userId,
                                            @RequestParam Optional<Long> postId) {
        return likeService.getAllByParam(userId, postId);
    }

    @PostMapping
    public Like create(@RequestBody LikeCreateRequest request) {
        return likeService.add(request);
    }

    @GetMapping("/{likeId}")
    public Like getById(@PathVariable Long likeId) {
        return likeService.getById(likeId);
    }

    @DeleteMapping("/{likeId}")
    public void delete(@PathVariable Long likeId) {
        likeService.delete(likeId);
    }
}
