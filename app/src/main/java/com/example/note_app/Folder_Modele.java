package com.example.note_app;

public class Folder_Modele {

    public String name;
    public String color;

    public Folder_Modele(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Folder_Modele() {
    }

    @Override
    public String toString() {
        return "Folder_Modele{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
