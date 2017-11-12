package io.tomislav.baking.bakingapp.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ActiveRecipe {
    @Id
    private Long id = null;

    private long activeRecipeId;

    @Generated(hash = 290194764)
    public ActiveRecipe(Long id, long activeRecipeId) {
        this.id = id;
        this.activeRecipeId = activeRecipeId;
    }

    @Generated(hash = 1360570751)
    public ActiveRecipe() {
    }

    public long getActiveRecipeId() {
        return this.activeRecipeId;
    }

    public void setActiveRecipeId(long activeRecipeId) {
        this.activeRecipeId = activeRecipeId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
