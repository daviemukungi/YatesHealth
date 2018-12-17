package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.Category;
import com.oneshoppoint.yates.wrapper.CategoryForm;

import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public interface CategoryService {
    void save (CategoryForm categoryForm);
    List<Category> getAll ();
    Category getById (Integer id);
    void update (CategoryForm categoryForm);
    void delete (Integer id);
    List<Category> search (String search);
}
