package com.juan.red_social_backend.content.internal.controllers;

import com.juan.red_social_backend.content.internal.dto.CommentDto;
import com.juan.red_social_backend.content.internal.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/content/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public ResponseEntity<?> addComent(@RequestBody CommentDto comment){
        log.info("INIT FUNCTION CONTROLLER: addComent()");

        if(comment.getAuthorId() == null || comment.getPostId() == null || comment.getContent() == null){
            log.info("EXIT FUNCTION CONTROLLER WITH ERROR: addComent()");
            return ResponseEntity.badRequest().body("Incorrect data");
        }

        commentService.createComment(comment);

        log.info("EXIT FUNCTION CONTROLLER: addComent()");

        return ResponseEntity.ok().body("Comment created");
    }

    @DeleteMapping("/removeComment/{idComment}")
    public ResponseEntity<?> removeComment(@PathVariable UUID idComment){
        log.info("INIT FUNCTION CONTROLLER: removeComment()");

        if(idComment == null){
            log.info("EXIT FUNCTION CONTROLLER WITH ERROR: removeComment()");
            return ResponseEntity.badRequest().body("Incorrect data");
        }

        commentService.deleteComment(idComment);

        log.info("EXIT FUNCTION CONTROLLER: removeComment()");

        return ResponseEntity.ok().body("Comment created");
    }

    @GetMapping("/countComments/{idPost}")
    public ResponseEntity<?> countComments(@PathVariable UUID idPost){
        log.info("INIT FUNCTION CONTROLLER: countComments()");

        if(idPost == null){
            log.info("EXIT FUNCTION CONTROLLER WITH ERROR: countComments()");
            return ResponseEntity.badRequest().body("Incorrect data");
        }

        int comments = commentService.countComments(idPost);

        return ResponseEntity.ok().body("Comments " + comments);

    }
}
