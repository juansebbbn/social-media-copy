package com.juan.red_social_backend.social.internal.services;

import com.juan.red_social_backend.social.api.ProfileApi;
import com.juan.red_social_backend.social.internal.entities.Profile;
import com.juan.red_social_backend.social.internal.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileInfoService implements ProfileApi {
    private ProfileRepository pf;

    public ProfileInfoService(ProfileRepository profileRepository){
        this.pf = profileRepository;
    }

    @Override
    public Profile getProfileWithThisId(UUID id) {
        return this.pf.findProfileById_user(id);
    }

    @Override
    public Profile getProfileByUsername(String username) {
        return null;
    }

    @Override
    public Profile saveProfile(Profile profile) {
        return pf.save(profile);
    }
}
