package io.tomislav.baking.bakingapp.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.parceler.Parcel;
import org.greenrobot.greendao.annotation.Generated;

@Entity
@Parcel(Parcel.Serialization.BEAN)
public class Step {
    @Id(autoincrement = true)
    @JsonIgnore
    private Long id = null;

    @JsonProperty("id") private int order;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    private Long recipeId = null;


    @Generated(hash = 768245809)
    public Step(Long id, int order, String shortDescription, String description,
            String videoURL, String thumbnailURL, Long recipeId) {
        this.id = id;
        this.order = order;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
        this.recipeId = recipeId;
    }

    @Generated(hash = 561308863)
    public Step() {
    }


    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public Long getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }
}
