package com.juan.red_social_backend.social.api;

import com.juan.red_social_backend.social.internal.entities.Profile;

import java.util.List;

public interface FollowsApi {
    public List<Profile> getAllFollows(String username, char follows_or_followers);
    public int getHowManyFollows(String username, char follows_or_followers);
}
