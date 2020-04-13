package com.threadjava.models;

import com.threadjava.users.model.User;
import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_reaction")
public class PostReaction extends BaseEntity {

    @Column(name = "isLike")
    @Getter @Setter public Boolean isLike;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "users_id")
    @Getter @Setter public User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "posts_id")
    @Getter @Setter public Post post;
}
