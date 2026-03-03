package com.juan.red_social_backend.social.internal.controllers;

import com.juan.red_social_backend.social.internal.services.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/social/follow")
public class FollowController {

    private FollowService fs;

    public FollowController(FollowService followService){
        this.fs = followService;
    }

    @PostMapping("/follow_user/{username_followed}")
    public ResponseEntity<?> follow_profile(@PathVariable String username_followed, Principal principal){

        if(username_followed == null){
            return ResponseEntity.badRequest().body("Username of followed empty");
        }

        String my_username = principal.getName();

        fs.follow_profile(my_username, username_followed);

        return ResponseEntity.ok().body("Profile followed");
    }

    @PostMapping("/unfollow_profile/{username_followed}")
    public ResponseEntity<?> unfollow_profile(@PathVariable String username_followed, Principal principal){

        if(username_followed == null){
            return ResponseEntity.badRequest().body("Username of unfollowed empty");
        }

        String my_username = principal.getName();

        fs.unfollow_profile(my_username, username_followed);

        return ResponseEntity.ok().body("Profile unfollowed");
    }

}
