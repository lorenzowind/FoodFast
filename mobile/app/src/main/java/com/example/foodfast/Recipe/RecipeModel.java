package com.example.foodfast.Recipe;

public class RecipeModel {
    private String id;
    private String image_url;
    private String name;
    private String description;
    private String ingredients;
    private String steps;
    private String video_url;

    public RecipeModel(String id, String image_url, String name, String description, String ingredients, String steps, String video_url) {
        this.id = id;
        this.image_url = image_url;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.video_url = video_url;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }
}
