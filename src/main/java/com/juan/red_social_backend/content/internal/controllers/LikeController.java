package com.juan.red_social_backend.content.internal.controllers;

import com.juan.red_social_backend.content.internal.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/content/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService){
        this.likeService = likeService;
    }

    @PostMapping("/addLike/{post_will_be_liked}")
    public ResponseEntity<?> addLike(@PathVariable UUID post_will_be_liked, Principal principal) {
        log.info("INIT FUNCTION CONTROLLER: addLike()");

        if(post_will_be_liked == null){
            log.info("EXIT FUNCTION CONTROLLER WITH ERROR: addLike()");
            return ResponseEntity.badRequest().body("Post id is null");
        }

        String username = principal.getName();

        likeService.addLike(post_will_be_liked, username);

        log.info("EXIT FUNCTION CONTROLLER: addLike()");

        return ResponseEntity.ok().body("Post liked");
    }

    @DeleteMapping("/deleteLike/{like_id}")
    public ResponseEntity<?> deleteLike(@PathVariable UUID like_id){
        log.info("INIT FUNCTION CONTROLLER: deleteLike()");

        if(like_id == null){
            log.info("EXIT FUNCTION CONTROLLER WITH ERROR: deleteLike()");
            return ResponseEntity.badRequest().body("Like id is null");
        }

        likeService.deleteLike(like_id);

        log.info("EXIT FUNCTION CONTROLLER: deleteLike()");

        return ResponseEntity.ok().body("Like deleted");
    }

    @GetMapping("/countLike/{post_id}")
    public ResponseEntity<?> countLike(@PathVariable UUID post_id){
        log.info("INIT FUNCTION CONTROLLER: countLike()");

        if(post_id == null){
            log.info("EXIT FUNCTION CONTROLLER WITH ERROR: countLike()");
            return ResponseEntity.badRequest().body("Post id is null");
        }

        int likes = likeService.countLike(post_id);

        log.info("EXIT FUNCTION CONTROLLER: countLike()");

        return ResponseEntity.ok().body("Likes: " + likes);
    }
}
