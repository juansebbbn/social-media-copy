package com.juan.red_social_backend.content.internal.controllers;

import com.juan.red_social_backend.content.internal.dto.PostDto;
import com.juan.red_social_backend.content.internal.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/content/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/addPost")
    public ResponseEntity<?> addPost(PostDto postDto){
        log.info("INIT FUNCTION CONTROLLER: addPost()");

        if(postDto.getContent() == null){
            log.info("EXIT FUNCTION CONTROLLER WITH ERROR: addPost()");
            return ResponseEntity.badRequest().body("Post whitout content");
        }
        if(postDto.getProfileId() == null){
            log.info("EXIT FUNCTION CONTROLLER WITH ERROR: addPost()");
            return ResponseEntity.badRequest().body("Post whitout profile id, we need a reference");
        }

        postService.createPost(postDto);

        log.info("EXIT FUNCTION CONTROLLER: addPost()");

        return ResponseEntity.ok().body("Post added");
    }

    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable UUID postId){
        log.info("INIT FUNCTION CONTROLLER: deletePost()");

        if(postId == null){
            log.info("EXIT FUNCTION CONTROLLER WITH ERROR: deletePost()");
            return ResponseEntity.badRequest().body("Post id is null");
        }

        postService.deletePost(postId);

        log.info("EXIT FUNCTION CONTROLLER: deletePost()");

        return ResponseEntity.ok().body("Post deleted");
    }

    @GetMapping("/getPost/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable UUID postId){
        log.info("INIT FUNCTION CONTROLLER: getPostById()");

        if(postId == null){
            log.info("EXIT FUNCTION CONTROLLER WITH ERROR: getPostById()");
            return ResponseEntity.badRequest().body("Post id is null");
        }

        log.info("EXIT FUNCTION CONTROLLER: getPostById()");

        return ResponseEntity.ok().body(postService.getPostById(postId));
    }

    @GetMapping("/getAllPost/{profile_id}")
    public ResponseEntity<?> getAllPost(@PathVariable UUID profile_id, char order, Principal principal){
        log.info("INIT FUNCTION CONTROLLER: getAllPost()");

        if(profile_id == null){
            log.info("EXIT FUNCTION CONTROLLER WITH ERROR: getAllPost()");
            return ResponseEntity.badRequest().body("Profile id is null");
        }

        String my_username = principal.getName();

        log.info("EXIT FUNCTION CONTROLLER: getAllPost()");

        return ResponseEntity.ok().body(postService.getAllPost(profile_id, my_username, order));
    }

}
