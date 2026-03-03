package com.juan.red_social_backend.content.internal.service;

import com.juan.red_social_backend.content.internal.dto.PostDto;
import com.juan.red_social_backend.content.internal.entites.Post;
import com.juan.red_social_backend.content.internal.repositories.PostRepository;
import com.juan.red_social_backend.shared.exceptions.NotEnoughPermissions;
import com.juan.red_social_backend.shared.exceptions.NotValidInput;
import com.juan.red_social_backend.social.api.FollowsApi;
import com.juan.red_social_backend.social.api.ProfileApi;
import com.juan.red_social_backend.social.internal.entities.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostService {
    private final ProfileApi profileApi;
    private final PostRepository postRepository;
    private final FollowsApi followsApi;

    public PostService(ProfileApi profileApi, PostRepository postRepository, FollowsApi followsApi){
        this.profileApi = profileApi;
        this.postRepository = postRepository;
        this.followsApi = followsApi;
    }

    public void createPost(PostDto postDto) {
        log.info("INIT FUNCTION SERVICE: createPost()");

        Profile prof = profileApi.getProfileWithThisId(postDto.getProfileId());

        if(prof == null){
            log.info("EXIT FUNCTION SERVICE WITH ERROR: createPost()");
            throw new NotValidInput("Profile does not exist");
        }

        Post post = new Post();

        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setProfileId(postDto.getProfileId());

        postRepository.save(post);

        log.info("EXIT FUNCTION SERVICE: createPost()");
    }

    public void deletePost(UUID postId){
        log.info("INIT FUNCTION SERVICE: deletePost()");

        postRepository.deleteById(postId);

        log.info("EXIT FUNCTION SERVICE: deletePost()");
    }

    public Post getPostById(UUID postId) {
        log.info("INIT FUNCTION SERVICE: getPostById()");

        log.info("EXIT FUNCTION SERVICE: getPostById()");
        return postRepository.getReferenceById(postId);
    }


    public List<Post> getAllPost(UUID profileId, String myUsername, char order){
        log.info("INIT FUNCTION SERVICE: getAllPost()");

        Profile prof = profileApi.getProfileWithThisId(profileId);
        Profile my_prof = profileApi.getProfileByUsername(myUsername);

        if(prof == null || my_prof == null){
            log.info("EXIT FUNCTION SERVICE WITH ERROR: getAllPost()");
            throw new NotValidInput("Profile does not exist");
        }

        if(prof.isPrivate_profile()){
            List<Profile> followers = followsApi.getAllFollows(prof.getUsername(), 'a');

            if(!followers.contains(my_prof)){
                log.info("EXIT FUNCTION SERVICE WITH ERROR: getAllPost()");
                throw new NotEnoughPermissions("Not enough permission");
            }

            log.info("EXIT FUNCTION SERVICE: getAllPost()");
            return postRepository.findByprofileId(prof.getId());
        }

        log.info("EXIT FUNCTION SERVICE: getAllPost()");
        return postRepository.findByprofileId(prof.getId());

    }
}
