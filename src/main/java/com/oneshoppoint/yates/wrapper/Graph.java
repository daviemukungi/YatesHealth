package com.oneshoppoint.yates.wrapper;

import java.util.List;

/**
 * Created by robinson on 5/10/16.
 */
public class Graph {
    private List<String> labels;
    private List<Dataset> datasets;

    public void setLabels (List<String> labels) {
        this.labels = labels;
    }

    public List<String> getLabels () {
        return labels;
    }

    public void setDatasets (List<Dataset> datasets) {
        this.datasets = datasets;
    }

    public List<Dataset> getDatasets () {
        return this.datasets;
    }
}
