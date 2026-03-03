package com.juan.red_social_backend.unit_test;

import com.juan.red_social_backend.shared.exceptions.NotValidInput;
import com.juan.red_social_backend.social.internal.entities.Follow;
import com.juan.red_social_backend.social.internal.entities.FollowId;
import com.juan.red_social_backend.social.internal.entities.Profile;
import com.juan.red_social_backend.social.internal.repositories.FollowRepository;
import com.juan.red_social_backend.social.internal.repositories.ProfileRepository;
import com.juan.red_social_backend.social.internal.services.FollowService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private ProfileRepository profile_rep;

    @Mock
    private FollowRepository follow_rep;

    @InjectMocks
    private FollowService followService; // class that mocks are inyected

    @Test
    void follow_profile_Success() {
        //preparing data
        String followerUser = "juan";
        String followedUser = "pedro";

        Profile follower = new Profile();
        follower.setId(UUID.randomUUID());
        follower.setUsername(followerUser);

        Profile followed = new Profile();
        followed.setId(UUID.randomUUID());
        followed.setUsername(followedUser);

        //explaining how the rep should behave
        when(profile_rep.findProfileByUsername(followerUser)).thenReturn(follower);
        when(profile_rep.findProfileByUsername(followedUser)).thenReturn(followed);

        followService.follow_profile(followerUser, followedUser);

        //checking if at least the function .save was called
        verify(follow_rep, times(1)).save(any(Follow.class));
    }

    @Test
    void follow_profile_ThrowsException_WhenProfileNotFound() {
        //simulating that profile does not exist
        when(profile_rep.findProfileByUsername("nobody")).thenReturn(null);

        //checking that throws expected expection
        assertThrows(NotValidInput.class, () -> {
            followService.follow_profile("someone", "nobody");
        });
    }

    @Test
    void unfollow_profile_Success() {
        //preparing data
        String unfollowerName = "juan";
        String unfollowedName = "pedro";

        UUID unfollowerId = UUID.randomUUID();
        UUID unfollowedId = UUID.randomUUID();

        Profile unfollower = new Profile();
        unfollower.setId(unfollowerId);
        unfollower.setUsername(unfollowerName);

        Profile unfollowed = new Profile();
        unfollowed.setId(unfollowedId);
        unfollowed.setUsername(unfollowedName);

        // explaining how the rep (mocks) should behave
        when(profile_rep.findProfileByUsername(unfollowerName)).thenReturn(unfollower);
        when(profile_rep.findProfileByUsername(unfollowedName)).thenReturn(unfollowed);

        //exec action
        followService.unfollow_profile(unfollowerName, unfollowedName);

        //checking if at least the function .save was called and if object follow id was created (for delete)
        verify(follow_rep, times(1)).deleteById(argThat(id ->
                id.getFollowerId().equals(unfollowerId) &&
                        id.getFollowingId().equals(unfollowedId)
        ));
    }

    @Test
    void unfollow_profile_ThrowsException_WhenUnfollowedNotFound() {
        //context where profile that will be unfollowed does not exist
        String unfollowerName = "juan";
        String unfollowedName = "notexisting";

        when(profile_rep.findProfileByUsername(unfollowerName)).thenReturn(new Profile());
        when(profile_rep.findProfileByUsername(unfollowedName)).thenReturn(null);

        //checking if is the correct excep
        assertThrows(NotValidInput.class, () -> {
            followService.unfollow_profile(unfollowerName, unfollowedName);
        });

        //checking if system never tried to delete anything if a profile does not exist
        verify(follow_rep, never()).deleteById(any(FollowId.class));
    }

    @Test
    void getAllFollows_success(){
        //4 profiles with random data
        Profile p1 = new Profile(); p1.setId(UUID.randomUUID()); p1.setUsername("juan_seguidor");
        Profile p2 = new Profile(); p2.setId(UUID.randomUUID()); p2.setUsername("pedro_doc");
        Profile p3 = new Profile(); p3.setId(UUID.randomUUID()); p3.setUsername("maria_dev");
        Profile p4 = new Profile(); p4.setId(UUID.randomUUID()); p4.setUsername("lucas_tech");

        // relation: p1 follows a p2
        FollowId id1to2 = new FollowId();
        id1to2.setFollowerId(p1.getId());
        id1to2.setFollowingId(p2.getId());
        Follow follow1to2 = new Follow();
        follow1to2.setId(id1to2);

        // relation: p1 follows a p3
        FollowId id1to3 = new FollowId();
        id1to3.setFollowerId(p1.getId());
        id1to3.setFollowingId(p3.getId());
        Follow follow1to3 = new Follow();
        follow1to3.setId(id1to3);

        // relation: p1 follows a p4
        FollowId id1to4 = new FollowId();
        id1to4.setFollowerId(p1.getId());
        id1to4.setFollowingId(p4.getId());
        Follow follow1to4 = new Follow();
        follow1to4.setId(id1to4);

        // relation: p2 follows a p4
        FollowId id1to5 = new FollowId();
        id1to4.setFollowerId(p2.getId());
        id1to4.setFollowingId(p4.getId());
        Follow follow1to5 = new Follow();
        follow1to5.setId(id1to5);

        List<Follow> follows = new ArrayList<>();
        follows.add(follow1to2);
        follows.add(follow1to3);
        follows.add(follow1to4);

        when(follow_rep.findById_FollowerId(p1.getId())).thenReturn(follows);
        when(profile_rep.findProfileByUsername("juan_seguidor")).thenReturn(p1);
        when(profile_rep.getReferenceById(p2.getId())).thenReturn(p2);
        when(profile_rep.getReferenceById(p3.getId())).thenReturn(p3);
        when(profile_rep.getReferenceById(p4.getId())).thenReturn(p4);

        List<Profile> profiles = new ArrayList<>();
        profiles.add(p2);
        profiles.add(p3);
        profiles.add(p4);

        List<Profile> response = followService.getAllFollows("juan_seguidor", 'a');

        assertEquals(profiles, response);
    }

    @Test
    void howManyProfileSucces(){
        //4 profiles with random data
        Profile p1 = new Profile(); p1.setId(UUID.randomUUID()); p1.setUsername("juan_seguidor");
        Profile p2 = new Profile(); p2.setId(UUID.randomUUID()); p2.setUsername("pedro_doc");
        Profile p3 = new Profile(); p3.setId(UUID.randomUUID()); p3.setUsername("maria_dev");
        Profile p4 = new Profile(); p4.setId(UUID.randomUUID()); p4.setUsername("lucas_tech");

        // relation: p1 follows a p2
        FollowId id1to2 = new FollowId();
        id1to2.setFollowerId(p1.getId());
        id1to2.setFollowingId(p2.getId());
        Follow follow1to2 = new Follow();
        follow1to2.setId(id1to2);

        // relation: p1 follows a p3
        FollowId id1to3 = new FollowId();
        id1to3.setFollowerId(p1.getId());
        id1to3.setFollowingId(p3.getId());
        Follow follow1to3 = new Follow();
        follow1to3.setId(id1to3);

        // relation: p1 follows a p4
        FollowId id1to4 = new FollowId();
        id1to4.setFollowerId(p1.getId());
        id1to4.setFollowingId(p4.getId());
        Follow follow1to4 = new Follow();
        follow1to4.setId(id1to4);

        // relation: p2 follows a p4
        FollowId id1to5 = new FollowId();
        id1to4.setFollowerId(p2.getId());
        id1to4.setFollowingId(p4.getId());
        Follow follow1to5 = new Follow();
        follow1to5.setId(id1to5);

        List<Follow> follows = new ArrayList<>();
        follows.add(follow1to2);
        follows.add(follow1to3);
        follows.add(follow1to4);

        when(follow_rep.findById_FollowerId(p1.getId())).thenReturn(follows);
        when(profile_rep.findProfileByUsername("juan_seguidor")).thenReturn(p1);

        int size = followService.getHowManyFollows("juan_seguidor", 'a');

        assertEquals(size, follows.size());
    }
}