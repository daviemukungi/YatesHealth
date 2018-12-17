package com.oneshoppoint.yates.model;

import javax.annotation.Nonnull;
import javax.persistence.*;

/**
 * Created by robinson on 4/6/16.
 */
@Entity
@Table(name="images")
public class Image  implements Comparable<Image> {
    private String path;
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Integer getId () {
        return id;
    }

    public void setPath (String path) {
        this.path = path;
    }

    @Column(nullable = false)
    public String getPath () {
        return path;
    }

    public int compareTo (@Nonnull Image other) {
        return this.id.compareTo(other.getId());
    }
}
