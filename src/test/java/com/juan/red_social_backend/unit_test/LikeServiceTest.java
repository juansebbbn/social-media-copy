package com.juan.red_social_backend.unit_test;

import com.juan.red_social_backend.content.internal.entites.Like;
import com.juan.red_social_backend.content.internal.entites.Post;
import com.juan.red_social_backend.content.internal.repositories.LikeRepository;
import com.juan.red_social_backend.content.internal.repositories.PostRepository;
import com.juan.red_social_backend.content.internal.service.LikeService;
import com.juan.red_social_backend.social.api.ProfileApi;
import com.juan.red_social_backend.social.internal.entities.Profile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest {
    @Mock
    private LikeRepository likeRepository;

    @Mock
    private ProfileApi profileApi;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private LikeService likeService;

    @Test
    void create_comment_test(){
        Profile profile1 = new Profile(); profile1.setId(UUID.randomUUID()); profile1.setUsername("juan_seguidor");

        Post post1 = new Post(); post1.setId(UUID.randomUUID()); post1.setContent("BLA BLA BLA"); post1.setProfileId(profile1.getId()); post1.setCreatedAt(LocalDateTime.now());


        when(profileApi.getProfileByUsername(profile1.getUsername())).thenReturn(profile1);
        when(postRepository.getReferenceById(post1.getId())).thenReturn(post1);

        likeService.addLike(post1.getId(), profile1.getUsername());

        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    void delete_comment_test(){
        UUID id = UUID.randomUUID();

        likeService.deleteLike(id);

        verify(likeRepository, times(1)).deleteById(any(UUID.class));
    }
}
