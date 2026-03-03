package com.juan.red_social_backend.unit_test;

import com.juan.red_social_backend.content.internal.dto.PostDto;
import com.juan.red_social_backend.content.internal.entites.Post;
import com.juan.red_social_backend.content.internal.repositories.PostRepository;
import com.juan.red_social_backend.content.internal.service.PostService;
import com.juan.red_social_backend.social.api.FollowsApi;
import com.juan.red_social_backend.social.api.ProfileApi;
import com.juan.red_social_backend.social.internal.entities.Follow;
import com.juan.red_social_backend.social.internal.entities.FollowId;
import com.juan.red_social_backend.social.internal.entities.Profile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private ProfileApi profileApi;

    @Mock
    private PostRepository postRepository;

    @Mock
    private FollowsApi followsApi;

    @InjectMocks
    private PostService postService;

    @Test
    void createPost_test(){
        Profile p1 = new Profile(); p1.setId(UUID.randomUUID()); p1.setUsername("juan_seguidor");
        PostDto psdto = new PostDto(); psdto.setContent("My content"); psdto.setProfileId(p1.getId());

        when(profileApi.getProfileWithThisId(psdto.getProfileId())).thenReturn(p1);

        postService.createPost(psdto);

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void getAllPost_test(){
        Profile p1 = new Profile(); p1.setId(UUID.randomUUID()); p1.setUsername("juan_seguidor"); // me
        Profile p2 = new Profile(); p2.setId(UUID.randomUUID()); p2.setUsername("pedro_doc"); p2.setPrivate_profile(true); // him

        //instanciar algunos post que apunten a p2
        Post pos1 = new Post(); pos1.setProfileId(p2.getId()); pos1.setId(UUID.randomUUID()); pos1.setContent("1");
        Post pos2 = new Post(); pos2.setProfileId(p2.getId()); pos2.setId(UUID.randomUUID()); pos2.setContent("2");
        Post pos3 = new Post(); pos3.setProfileId(p2.getId()); pos3.setId(UUID.randomUUID()); pos3.setContent("3");

        List<Post> posts = new ArrayList<>();

        posts.add(pos1);
        posts.add(pos2);
        posts.add(pos3);

        //crear lista de seguidores
        List<Profile> followers = new ArrayList<>();

        followers.add(p1);

        when(profileApi.getProfileWithThisId(p2.getId())).thenReturn(p2);
        when(profileApi.getProfileByUsername(p1.getUsername())).thenReturn(p1);
        when(followsApi.getAllFollows(p2.getUsername(), 'a')).thenReturn(followers);
        when(postRepository.findByprofileId(p2.getId())).thenReturn(posts);

        ArrayList<Post> res = (ArrayList<Post>) postService.getAllPost(p2.getId(), p1.getUsername(), 'x');

        assertEquals(res, posts);
    }
}
