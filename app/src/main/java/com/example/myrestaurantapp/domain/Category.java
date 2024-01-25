package com.example.myrestaurantapp.domain;

public class Category {
   private String id;
   private String imagePath;



    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    private String name;

    public Category() {
    }
}
