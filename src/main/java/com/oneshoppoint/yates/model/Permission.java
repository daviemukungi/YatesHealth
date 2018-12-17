package com.oneshoppoint.yates.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Davie on 4/6/16.
 */
@Entity
@Table(name="permissions")
public class Permission extends Model {
    private String name;
    private String description;

    public void setName (String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getName () {
        return name;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public String getDescription () {
        return description;
    }
}
