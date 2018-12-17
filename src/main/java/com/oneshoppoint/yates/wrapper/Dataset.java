package com.oneshoppoint.yates.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by robinson on 5/10/16.
 */
public class Dataset {
    private String label;
    private List<Double> data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String backgroundColor = "rgba(55,71,79,0.4)";

    public void setLabel (String label) {
        this.label  = label;
    }

    public String getLabel () {
        return label;
    }

    public void setData (List<Double> data) {
        this.data = data;
    }

    public List<Double> getData () {
        return data;
    }

    public void setBackgroundColor (String backgroundColor) {
        this.backgroundColor =  backgroundColor;
    }

    public String getBackgroundColor () {
        return backgroundColor;
    }

    public void generateBackground () {
        backgroundColor = "rgba("+(Math.round(Math.random()* 127) + 127)+","+(Math.round(Math.random()* 127) + 127)+","+(Math.round(Math.random()* 127) + 127)+",1)";
    }
}
