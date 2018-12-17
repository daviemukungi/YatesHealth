package com.oneshoppoint.yates.model;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by robinson on 4/6/16.
 */
@Entity
@Table(name="categories")
public class Category extends Model implements Comparable<Category> {
    private String name;
    private String description;
    private Image image;
    private Set<Category> children;
    private Boolean root;

    public void setName (String name) {
        this.name = name;
    }

    @Column(nullable=false)
    public String getName () {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Lob
    @Column(nullable=false,length = 2000)
    public String getDescription () {
        return description;
    }

    public void setImage (Image image) {
        this.image = image;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="image_id")
    public Image getImage () {
        return image;
    }

    public void setChildren (Set<Category> children) {
        if(this.children == null || children == null) {
            this.children = new TreeSet<Category>();
        }

        if(children != null) {
            this.children.addAll(children);
        }
    }

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="parent_id")
    public Set<Category> getChildren () {
        return children;
    }

    public void setRoot (Boolean root) {
        this.root = root;
    }

    @Column(nullable = false)
    public Boolean getRoot () {
        return this.root;
    }

    @Override
    public boolean equals (Object obj) {
        if(obj instanceof Category) {
            Category other = (Category) obj;
            if(this.name.equalsIgnoreCase(other.getName())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode () {
        int hash = 5;
        hash = 73 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    public int compareTo (Category that) {
        return this.name.compareToIgnoreCase(that.getName());
    }
}
