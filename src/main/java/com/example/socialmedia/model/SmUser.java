package com.example.socialmedia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
//import javax.persistence.GenerationType;


@Entity
@Table(name = "AUTH_USERS")
public class SmUser implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
    @Id
    @Column
    private String username;
    @Column
    @JsonIgnore
    private String password;

    @Column
    private Integer phone;

//
    @OneToMany(mappedBy = "followedUser" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Follow> followers;
//
//    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Follow> following;

//    @ManyToMany
//    Set<Follow> followers;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
}
