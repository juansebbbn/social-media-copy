package com.juan.red_social_backend.social.api;

import com.juan.red_social_backend.social.internal.entities.Profile;

import java.util.UUID;

public interface ProfileApi{
    public Profile getProfileWithThisId(UUID id);
    public Profile getProfileByUsername(String username);
    public Profile saveProfile(Profile profile);
}
