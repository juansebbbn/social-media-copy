package com.juan.red_social_backend.social.internal.entities;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class FollowId implements Serializable {
    private UUID followerId;
    private UUID followingId;
}
