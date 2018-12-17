package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.Category;
import com.oneshoppoint.yates.model.Image;
import com.oneshoppoint.yates.repository.GenericRecursiveDao;
import com.oneshoppoint.yates.service.CategoryService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.CategoryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by robinson on 4/8/16.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    GenericRecursiveDao<Category> categoryDao;
    @Autowired
    CurrentUser currentUser;
    public void save (CategoryForm categoryForm) {
        if(categoryDao.getByName(categoryForm.getName()) != null) {
            throw new RestException(Status.ERROR,"The category "+categoryForm.getName()+" already exists");
        }

        Category category = new Category();
        category.setCreatedBy(currentUser.getUser().getId());
        category.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        category.setName(categoryForm.getName());
        category.setDescription(categoryForm.getDescription());
        Image img = categoryForm.getImage();
        category.setImage(img);
        category.setEnabled(categoryForm.getEnabled());
        if(categoryForm.getParentId() != null) {
            Category child = category;
            category = categoryDao.find(categoryForm.getParentId());
            TreeSet<Category> children = new TreeSet<Category>();
            child.setRoot(false);
            children.add(child);
            category.setChildren(children);
        } else {
            category.setRoot(true);
        }

        categoryDao.save(category);
    }

    public void update (CategoryForm categoryForm) {
        Category category = categoryDao.find(categoryForm.getId());

        if(category == null) {
            throw new RestException(Status.ERROR,"The category does not exist");
        }

        category.setUpdatedBy(currentUser.getUser().getId());
        category.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        category.setName(categoryForm.getName());
        category.setDescription(categoryForm.getDescription());
        Image img = categoryForm.getImage();
        if(img != null) {
            category.setImage(img);
        }
        category.setEnabled(categoryForm.getEnabled());
        if(categoryForm.getParentId() != null) {
            Category child = category;
            category = categoryDao.find(categoryForm.getParentId());
            TreeSet<Category> children = new TreeSet<Category>();
            children.add(child);
            category.setChildren(children);
        } else {
            category.setRoot(true);
        }

        categoryDao.update(category);
    }

    public List<Category> getAll () {
        List<Category> roots = categoryDao.getAll();
        if(roots != null){
            for (Category root : roots) {
                if(root != null) {
                    filter(root);
                }
            }
        }
        return roots;
    }

    public Category getById (Integer id) {
        return categoryDao.find(id);
    }

    public void delete (Integer id) {
        Category category = categoryDao.find(id);

        if(category == null) {
            throw new RestException(Status.ERROR,"The category does not exist");
        }

        markDelete(category);

        categoryDao.delete(category);
    }

    private void markDelete (Category node) {
        node.setDeletedBy(currentUser.getUser().getId());
        node.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        Set<Category> children = node.getChildren();
        for(Category child : children) {
            markDelete(child);
        }
    }

    private void filter (Category node) {
        Set<Category> children = node.getChildren();
        Set<Category> childrenFiltered = node.getChildren();

        for(Category child : children) {
            if(child.getDeletedOn() != null) {
                childrenFiltered.remove(child);
            }
        }

        node.setChildren(childrenFiltered);
        for(Category child : childrenFiltered) {
            filter(child);
        }
    }

    public List<Category> search (String search) {
        return categoryDao.getAll();
    }
}