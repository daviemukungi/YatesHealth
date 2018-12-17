package com.oneshoppoint.yates.wrapper;

import com.oneshoppoint.yates.model.Image;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public class ProductForm {
    private Integer id;
    @Size(min=3,max=100)
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private Double weight;
    @NotNull
    private Double width;
    @NotNull
    private Double height;
    @NotNull
    private Double length;
    private String description;
    private Integer categoryId;
    private Integer innId;
    @NotNull
    private Integer manufacturerId;
    private List<FeatureValueForm> featureValues;
    @NotNull
    private Image image;
    @NotNull
    private Boolean enabled;

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

    public void setPrice (Double price) {
        this.price = price;
    }

    public Double getPrice () {
        return price;
    }

    public void setWeight (Double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWidth (Double width) {
        this.width = width;
    }

    public Double getWidth() {
        return width;
    }

    public void setHeight (Double height) {
        this.height = height;
    }

    public Double getHeight() {
        return height;
    }

    public void setLength (Double length) {
        this.length = length;
    }

    public Double getLength() {
        return length;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getDescription () {
        return description;
    }

    public void setCategoryId (Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId () {
        return categoryId;
    }

    public void setInnId (Integer innId) {
        this.innId = innId;
    }

    public Integer getInnId () {
        return innId;
    }

    public void setManufacturerId (Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getManufacturerId () {
        return manufacturerId;
    }

    public void setFeatureValues(List<FeatureValueForm> featureValues) {
        this.featureValues = featureValues;
    }

    public List<FeatureValueForm> getFeatureValues() {
        return featureValues;
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

}
