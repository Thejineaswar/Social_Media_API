package com.example.socialmedia.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "FOLLOW",
        uniqueConstraints = {
  @UniqueConstraint(columnNames = {"username", "follower"})
})

public class Follow implements Serializable {
    public long getId() {
        return id;
    }


    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public SmUser getFollowed_user() {
        return followedUser;
    }

    public void setFollowed_user(SmUser followed_user) {
        this.followedUser = followed_user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String follower;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private SmUser followedUser;

}
