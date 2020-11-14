package com.example.foodfast.Category;

public class CategoryModel {
    private String id;
    private String image_url;
    private String name;

    public CategoryModel(String id, String image_url, String name) {
        this.id = id;
        this.image_url = image_url;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
