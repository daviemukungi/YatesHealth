package com.oneshoppoint.yates.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * Created by robinson on 4/6/16.
 */
@MappedSuperclass
public class Model {
    private Integer id;
    private Integer createdBy;
    private Timestamp createdOn;
    private Integer updatedBy;
    private Timestamp updatedOn;
    private Integer deletedBy;
    private Timestamp deletedOn;
    private Boolean enabled;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Integer getId () {
        return id;
    }

    public void setCreatedBy (Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name="created_by",nullable = false)
    public Integer getCreatedBy () {
        return createdBy;
    }

    public void setCreatedOn (Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name="created_on",nullable = false)
    public Timestamp getCreatedOn () {
        return createdOn;
    }

    public void setUpdatedBy (Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Column(name="updated_by")
    public Integer getUpdatedBy () {
        return updatedBy;
    }

    public void setUpdatedOn (Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Column(name="updated_on")
    public Timestamp getUpdatedOn () {
        return updatedOn;
    }

    public void setDeletedBy (Integer deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Column(name="deleted_by")
    public Integer getDeletedBy () {
        return deletedBy;
    }

    public void setDeletedOn (Timestamp deletedOn) {
        this.deletedOn = deletedOn;
    }

    @Column(name="deleted_on")
    public Timestamp getDeletedOn () {
        return deletedOn;
    }

    public void setEnabled (Boolean enabled) {
        this.enabled = enabled;
    }

    @Column(nullable = false)
    public Boolean getEnabled () {
        return enabled;
    }

}
