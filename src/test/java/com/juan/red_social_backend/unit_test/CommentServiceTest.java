package com.juan.red_social_backend.unit_test;

import com.juan.red_social_backend.content.internal.dto.CommentDto;
import com.juan.red_social_backend.content.internal.entites.Comment;
import com.juan.red_social_backend.content.internal.entites.Post;
import com.juan.red_social_backend.content.internal.repositories.CommentRepository;
import com.juan.red_social_backend.content.internal.repositories.PostRepository;
import com.juan.red_social_backend.content.internal.service.CommentService;
import com.juan.red_social_backend.social.api.ProfileApi;
import com.juan.red_social_backend.social.internal.entities.Profile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private ProfileApi profileApi;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentService commentService; // class that mocks are inyected

    @Test
    void create_comment_test(){
        Profile profile1 = new Profile(); profile1.setId(UUID.randomUUID()); profile1.setUsername("juan_seguidor");

        Post post1 = new Post(); post1.setId(UUID.randomUUID()); post1.setContent("BLA BLA BLA"); post1.setProfileId(profile1.getId()); post1.setCreatedAt(LocalDateTime.now());

        CommentDto commentDto = new CommentDto(); commentDto.setContent("Mi comentario"); commentDto.setAuthorId(profile1.getId()); commentDto.setPostId(post1.getId());

        when(profileApi.getProfileWithThisId(profile1.getId())).thenReturn(profile1);
        when(postRepository.getReferenceById(post1.getId())).thenReturn(post1);

        commentService.createComment(commentDto);

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void delete_comment_test(){
        Profile profile1 = new Profile(); profile1.setId(UUID.randomUUID()); profile1.setUsername("juan_seguidor");

        Post post1 = new Post(); post1.setId(UUID.randomUUID()); post1.setContent("BLA BLA BLA"); post1.setProfileId(profile1.getId()); post1.setCreatedAt(LocalDateTime.now());

        Comment comment = new Comment(); comment.setId(UUID.randomUUID()); comment.setContent("My commment"); comment.setCreatedAt(LocalDateTime.now());
                                          comment.setAuthorId(profile1.getId()); comment.setPostId(post1.getId());

        commentService.deleteComment(comment.getId());

        verify(commentRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void count_comments_test(){
        Profile profile1 = new Profile(); profile1.setId(UUID.randomUUID()); profile1.setUsername("juan_seguidor");

        Post post1 = new Post(); post1.setId(UUID.randomUUID()); post1.setContent("BLA BLA BLA"); post1.setProfileId(profile1.getId()); post1.setCreatedAt(LocalDateTime.now());

        Comment comment1 = new Comment(); comment1.setId(UUID.randomUUID()); comment1.setContent("My commment"); comment1.setCreatedAt(LocalDateTime.now());
        comment1.setAuthorId(profile1.getId()); comment1.setPostId(post1.getId());

        Comment comment2 = new Comment(); comment2.setId(UUID.randomUUID()); comment2.setContent("My commment"); comment2.setCreatedAt(LocalDateTime.now());
        comment2.setAuthorId(profile1.getId()); comment2.setPostId(post1.getId());

        ArrayList<Comment> comments = new ArrayList<>();

        comments.add(comment1);
        comments.add(comment2);

        when(commentRepository.findBypostId(post1.getId())).thenReturn(comments);

        int res = commentService.countComments(post1.getId());

        assertEquals(res, comments.size());

    }

}
