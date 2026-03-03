package com.juan.red_social_backend.content.internal.service;

import com.juan.red_social_backend.content.internal.dto.CommentDto;
import com.juan.red_social_backend.content.internal.entites.Comment;
import com.juan.red_social_backend.content.internal.entites.Post;
import com.juan.red_social_backend.content.internal.repositories.CommentRepository;
import com.juan.red_social_backend.content.internal.repositories.PostRepository;
import com.juan.red_social_backend.shared.exceptions.NotValidInput;
import com.juan.red_social_backend.social.api.ProfileApi;
import com.juan.red_social_backend.social.internal.entities.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CommentService {
    private final ProfileApi profileApi;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(ProfileApi profileApi, CommentRepository commentRepo, PostRepository postRepository){
        this.profileApi = profileApi;
        this.commentRepository = commentRepo;
        this.postRepository = postRepository;
    }

    public void createComment(CommentDto commentDto) {
        log.info("INIT FUNCTION SERVICE: createComment()");

        Profile prof = profileApi.getProfileWithThisId(commentDto.getAuthorId());
        Post post = postRepository.getReferenceById(commentDto.getPostId());

        if(prof == null){
            log.info("ERROR: Profile not exist");
            log.info("EXIT FUNCTION SERVICE WITH ERROR: createComment()");
            throw new NotValidInput("Profile not exist");
        }

        log.info("VALID: Adding comment");

        Comment comment = new Comment();

        comment.setAuthorId(commentDto.getAuthorId());
        comment.setPostId(commentDto.getPostId());
        comment.setContent(commentDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);

        log.info("VALID: Comment saved");

        log.info("EXIT FUNCTION SERVICE: createComment()");
    }

    public void deleteComment(UUID idComment) {
        log.info("INIT FUNCTION SERVICE: deleteComment()");

        log.info("VALID: Deleting comment");

        commentRepository.deleteById(idComment);

        log.info("VALID: Comment deleted");

        log.info("EXIT FUNCTION SERVICE: deleteComment()");
    }

    public int countComments(UUID idPost) {
        log.info("INIT FUNCTION SERVICE: countComments()");

        List<Comment> comments = commentRepository.findBypostId(idPost);

        log.info("EXIT FUNCTION SERVICE: countComments()");

        return comments.size();
    }
}
