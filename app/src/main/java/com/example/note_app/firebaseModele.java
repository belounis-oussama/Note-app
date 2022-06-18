package com.example.note_app;

public class firebaseModele {

    private String title;
    private String content;

    public firebaseModele(String title, String content) {
        this.title = title;
        this.content = content;
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

    @Override
    public String toString() {
        return "firebaseModele{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
