package com.juan.red_social_backend.social.internal.services;

import com.juan.red_social_backend.shared.exceptions.NotValidInput;
import com.juan.red_social_backend.social.api.FollowsApi;
import com.juan.red_social_backend.social.internal.entities.Follow;
import com.juan.red_social_backend.social.internal.entities.FollowId;
import com.juan.red_social_backend.social.internal.entities.Profile;
import com.juan.red_social_backend.social.internal.repositories.FollowRepository;
import com.juan.red_social_backend.social.internal.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FollowService implements FollowsApi {

    private final ProfileRepository profile_rep;
    private final FollowRepository follow_rep;

    public FollowService(ProfileRepository profileRepository, FollowRepository follow_rep){
        this.profile_rep = profileRepository;
        this.follow_rep = follow_rep;
    }

    public void follow_profile(String usernameFollower, String usernameFollowed) throws NotValidInput{
        Profile profile_followed = profile_rep.findProfileByUsername(usernameFollowed);
        Profile profile_follower = profile_rep.findProfileByUsername(usernameFollower);

        if(profile_followed == null){
            throw new NotValidInput("You are trying to follow a null profile");
        }

        if(profile_follower == null){
            throw new NotValidInput("Non existing profile");
        }

        FollowId fid = new FollowId();

        fid.setFollowerId(profile_follower.getId());
        fid.setFollowingId(profile_followed.getId());

        Follow follow = new Follow();

        follow.setId(fid);
        follow.setCreatedAt(LocalDateTime.now());
        
        follow_rep.save(follow);
    }

    public void unfollow_profile(String usernameUnfollower, String usernameUnfollowed) {
        Profile profile_unfollowed = profile_rep.findProfileByUsername(usernameUnfollowed);
        Profile profile_unfollower = profile_rep.findProfileByUsername(usernameUnfollower);

        if(profile_unfollowed == null){
            throw new NotValidInput("You are trying to unfollow a null profile");
        }

        if(profile_unfollower == null){
            throw new NotValidInput("Non existing profile");
        }

        UUID unfollowed_id = profile_unfollowed.getId();
        UUID unfollower_id = profile_unfollower.getId();

        FollowId id = new FollowId();
        id.setFollowerId(unfollower_id);
        id.setFollowingId(unfollowed_id);

        follow_rep.deleteById(id);
    }


    @Override
    public List<Profile> getAllFollows(String username, char follows_or_followers) {
        Profile prof = profile_rep.findProfileByUsername(username);

        List<Profile> followers = new ArrayList<>();

        //a -> for followers, b -> follows

        // aca retorno los seguidos
        if(follows_or_followers == 'a'){
            List<Follow> followers_id = follow_rep.findById_FollowerId(prof.getId());

            for(Follow follow : followers_id){
               Profile profile = profile_rep.getReferenceById(follow.getId().getFollowingId());

               followers.add(profile);
            }

            return followers;
        }

        //aca retorno seguidores

        List<Follow> followers_id = follow_rep.findById_FollowingId(prof.getId());

        for(Follow follow : followers_id){
            Profile profile = profile_rep.getReferenceById(follow.getId().getFollowerId());

            followers.add(profile);
        }

        return followers;

    }

    @Override
    public int getHowManyFollows(String username, char follows_or_followers) {
        Profile prof = profile_rep.findProfileByUsername(username);

        //a -> for followers, b -> follows

        // aca retorno los seguidos
        if(follows_or_followers == 'a'){
            List<Follow> followers_id = follow_rep.findById_FollowerId(prof.getId());
            return followers_id.size();
        }

        //aca retorno seguidores
        List<Follow> followers_id = follow_rep.findById_FollowingId(prof.getId());
        return followers_id.size();
    }
}
