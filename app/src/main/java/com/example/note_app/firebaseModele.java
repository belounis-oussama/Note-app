package com.example.note_app;

public class firebaseModele {

    private String title;
    private String content;
    private String starred;

    public firebaseModele(String title, String content, String starred) {
        this.title = title;
        this.content = content;
        this.starred = starred;
    }

    public firebaseModele() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStarred() {
        return starred;
    }

    public void setStarred(String starred) {
        this.starred = starred;
    }

    @Override
    public String toString() {
        return "firebaseModele{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", starred='" + starred + '\'' +
                '}';
    }
}
