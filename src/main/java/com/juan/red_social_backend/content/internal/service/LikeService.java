package com.juan.red_social_backend.content.internal.service;

import com.juan.red_social_backend.content.internal.entites.Like;
import com.juan.red_social_backend.content.internal.entites.Post;
import com.juan.red_social_backend.content.internal.repositories.LikeRepository;
import com.juan.red_social_backend.content.internal.repositories.PostRepository;
import com.juan.red_social_backend.shared.exceptions.NotValidInput;
import com.juan.red_social_backend.social.api.ProfileApi;
import com.juan.red_social_backend.social.internal.entities.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final ProfileApi profileApi;
    private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, ProfileApi profileApi, PostRepository postRepository){
        this.likeRepository = likeRepository;
        this.profileApi = profileApi;
        this.postRepository = postRepository;
    }

    public void addLike(UUID postWillBeLiked, String username) {
        log.info("INIT FUNCTION SERVICE: addLike()");

        Profile prof = profileApi.getProfileByUsername(username);

        if(prof == null){
            log.info("EXIT FUNCTION SERVICE WITH ERROR: addLike()");
            throw new NotValidInput("Profile does not exist");
        }

        Post post = postRepository.getReferenceById(postWillBeLiked);

        Like like = new Like();

        like.setCreatedAt(LocalDateTime.now());
        like.setProfileId(prof.getId());
        like.setPostId(post.getId());

        log.info("EXIT FUNCTION SERVICE: addLike()");

        likeRepository.save(like);
    }

    public void deleteLike(UUID likeId) {
        log.info("INIT FUNCTION SERVICE: deleteLike()");

        log.info("EXIT FUNCTION SERVICE: deleteLike()");

        likeRepository.deleteById(likeId);
    }

    public int countLike(UUID post_id){
        log.info("INIT FUNCTION SERVICE: countLike()");

        List<Like> likes = likeRepository.findBypostId(post_id);

        log.info("EXIT FUNCTION SERVICE: countLike()");

        return likes.size();
    }
}
