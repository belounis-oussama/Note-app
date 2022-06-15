package com.example.note_app;

public class User_Model {
    public String nickname;
    public String email;
    public String password;
    public String pictureLink;


    public User_Model(String nickname, String email, String password, String pictureLink) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.pictureLink = pictureLink;
    }


    public User_Model() {
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    @Override
    public String toString() {
        return "User_Model{" +
                "nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", pictureLink='" + pictureLink + '\'' +
                '}';
    }
}
