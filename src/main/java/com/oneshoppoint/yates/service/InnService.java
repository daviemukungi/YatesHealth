package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.Inn;
import com.oneshoppoint.yates.wrapper.InnForm;

import java.util.List;

/**
 * Created by Davie on 4/11/16.
 */
public interface InnService {
    void save (InnForm innForm);
    List<Inn> getAll();
    Inn getById(Integer id);
    void update (InnForm innForm);
    void delete (List<Integer> ids);
}
