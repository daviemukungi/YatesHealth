package com.oneshoppoint.yates.wrapper;

import com.oneshoppoint.yates.model.Image;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by robinson on 4/12/16.
 */
public class ProductImages {
    @NotNull
    private Integer id;
    @NotNull
    private Set<Image> images;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId () {
        return id;
    }

    public void setImages (Set<Image> images) {
        this.images = images;
    }

    public Set<Image> getImages () {
        return images;
    }
}
