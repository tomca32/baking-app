package io.tomislav.baking.bakingapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.parceler.Parcel;
import org.greenrobot.greendao.annotation.Generated;

@Entity
@Parcel(Parcel.Serialization.BEAN)
public class Ingredient {
    @Id
    @JsonIgnore
    private long id;

    private long recipeId;

    private int quantity;
    private String measure;
    private String ingredient;

    @Generated(hash = 1906667976)
    public Ingredient(long id, long recipeId, int quantity, String measure,
            String ingredient) {
        this.id = id;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    @Generated(hash = 1584798654)
    public Ingredient() {
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }
}
