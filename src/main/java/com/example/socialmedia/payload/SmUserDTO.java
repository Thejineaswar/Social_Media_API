package com.example.socialmedia.payload;

public class SmUserDTO {
    private String username;
    private String password;

    private Integer phone;

    public SmUserDTO() {
    }

    public SmUserDTO(String username, String password, Integer phone) {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

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
}
