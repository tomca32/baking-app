package io.tomislav.baking.bakingapp.models;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class Ingredient {
    private int quantity;
    private String measure;
    private String ingredient;

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
}
