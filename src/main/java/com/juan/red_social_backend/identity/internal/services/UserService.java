package com.juan.red_social_backend.identity.internal.services;

import com.juan.red_social_backend.identity.internal.dto.AuthDto;
import com.juan.red_social_backend.identity.internal.entities.User;
import com.juan.red_social_backend.identity.internal.repositories.UserRepository;
import com.juan.red_social_backend.shared.exceptions.NotValidInput;
import com.juan.red_social_backend.shared.exceptions.RegisterAlreadyExists;
import com.juan.red_social_backend.shared.exceptions.WrongCredentials;
import com.juan.red_social_backend.shared.jwtUtils.JwtService;
import com.juan.red_social_backend.social.api.ProfileApi;
import com.juan.red_social_backend.social.internal.entities.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class UserService {

    private final UserRepository ur;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ProfileApi profileApi;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, ProfileApi profileApi){
        this.ur = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.profileApi = profileApi;
    }

    public void register(AuthDto authDto) throws NotValidInput, RegisterAlreadyExists{
        if(authDto.getPassword().length() <= 6){
            throw new NotValidInput("Password size is less than 6 chars");
        }

        User user = ur.findByemail(authDto.getEmail());

        if(user != null){
            throw new RegisterAlreadyExists("User already exists");
        }

        User post_user = new User();

        post_user.setEmail(authDto.getEmail());

        String password = passwordEncoder.encode(authDto.getPassword());

        log.info("Encrypting password");

        post_user.setPassword(password);

        ur.save(post_user);

        user = ur.findByemail(authDto.getEmail());

        Profile profile = new Profile();

        profile.setUserId(user.getId());

        profile.setUsername(authDto.getUsername());

        profileApi.saveProfile(profile);

        log.info("User created with email: {}", authDto.getEmail());
    }

    public String login(AuthDto authDto) throws WrongCredentials{
        User user = ur.findByemail(authDto.getEmail());

        log.info("Comparing password");

        if(!passwordEncoder.matches(authDto.getPassword(), user.getPassword())){
            throw new WrongCredentials("Password does not match");
        }

        // once user is authenticated we have to look for profile that is related to this user.

        Profile prof = profileApi.getProfileWithThisId(user.getId());

        log.info("Creating token");

        return jwtService.generateToken(authDto.getEmail(), prof.getUsername());
    }
}
