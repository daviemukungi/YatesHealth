package com.oneshoppoint.yates.wrapper;

import com.oneshoppoint.yates.model.Image;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by robinson on 4/9/16.
 */
public class CategoryForm {
    private Integer id;
    @Size(min=3,max=100)
    private String name;
    @NotBlank
    private String description;
    private Image image;
    private Boolean enabled;
    private Integer parentId;

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getId () {
        return id;
    }
    public void setName (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription () {
        return description;
    }

    public void setImage (Image image) {
        this.image = image;
    }

    public Image getImage () {
        return image;
    }

    public void setEnabled (Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled () {
        return enabled;
    }

    public void setParentId (Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getParentId () {
        return parentId;
    }

}
