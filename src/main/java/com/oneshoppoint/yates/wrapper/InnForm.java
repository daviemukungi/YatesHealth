package com.oneshoppoint.yates.wrapper;


import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by robinson on 5/4/16.
 */
public class InnForm {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private List<Integer> dosageFormIds;
    @NotNull
    private Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getDosageFormIds() {
        return dosageFormIds;
    }

    public void setDosageFormIds(List<Integer> dosageFormIds) {
        this.dosageFormIds = dosageFormIds;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
