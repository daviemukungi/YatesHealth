package com.oneshoppoint.yates.model;

import javax.persistence.*;
import java.util.*;

/**
 * Created by robinson on 4/6/16.
 */
@Entity
@Table(name="products")
public class Product extends Model {
    private String uuid;
    private String name;
    private Double price;
    private Double weight;
    private Double height;
    private Double length;
    private Double width;
    private String description;
    private Category category;
    private Inn inn;
    private Manufacturer manufacturer;
    private List<FeatureValue> featureValues;
    private Image primaryImage;
    private Set<Image> images;

    public Product () {
        images = new TreeSet<Image>();
    }

    public void setName (String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getName () {
        return name;
    }

    public void setUUID (String uuid) {
        this.uuid = uuid;
    }

    @Column(nullable = false)
    public String getUUID() {
        return uuid;
    }

    public void setWeight (Double weight) {
        this.weight = weight;
    }

    @Column(nullable = false)
    public Double getWeight() {
        return weight;
    }

    public void setHeight (Double height) {
        this.height = height;
    }

    @Column(nullable = false)
    public Double getHeight() {
        return height;
    }

    public void setLength (Double length) {
        this.length = length;
    }

    @Column(nullable = false)
    public Double getLength() {
        return length;
    }

    public void setWidth (Double width) {
        this.width = width;
    }

    @Column(nullable = false)
    public Double getWidth() {
        return width;
    }


    public void setPrice (Double price) {
        this.price = price;
    }

    @Column(nullable = false)
    public Double getPrice() {
        return price;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    @Column(nullable=false,length = 2000)
    public String getDescription () {
        return this.description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name="category_id")
    public Category getCategory () {
        return category;
    }

    public void setInn(Inn inn) {
        this.inn = inn;
    }

    @ManyToOne
    @JoinColumn(name="inn_id")
    public Inn getInn () {
        return inn;
    }

    public void setManufacturer (Manufacturer manufacturer ) {
        this.manufacturer = manufacturer;
    }

    @ManyToOne
    @JoinColumn(name="manufacturer_id")
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setFeatureValues(List<FeatureValue> featureValues) {
        this.featureValues = new ArrayList<FeatureValue>();
        if(featureValues != null) {
            this.featureValues.addAll(featureValues);
        }
    }

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="product_id")
    public List<FeatureValue> getFeatureValues() {
        return featureValues;
    }

    public void setPrimaryImage (Image primaryImage) {
        this.primaryImage = primaryImage;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="image_id")
    public Image getPrimaryImage () {
        return primaryImage;
    }

    public void setImages (Set<Image> images) {
        if(!(images instanceof TreeSet || images instanceof HashSet)) {
            this.images = new TreeSet<Image>();
            this.images.addAll(images);
        } else {
            this.images = images;
        }
    }

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="product_id")
    public Set<Image> getImages () {
        return images;
    }

    @Override
    public boolean equals (Object obj) {
        if(obj instanceof  Product) {
            Product other = (Product) obj;
            if(this.name.equalsIgnoreCase(other.getName()) &&
               this.category.equals(other.getCategory()) &&
               this.manufacturer.equals(other.getManufacturer())
              ) {
                for(FeatureValue featureValue : featureValues) {
                    List<FeatureValue> otherFeatureValues = other.getFeatureValues();
                    for(FeatureValue otherFeatureValue : otherFeatureValues)
                    if(!featureValue.equals(otherFeatureValue)) {
                        return false;
                    }
                }

                return true;
            }
        }
        return false;
    }
}
