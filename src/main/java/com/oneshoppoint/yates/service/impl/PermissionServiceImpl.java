package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.Permission;
import com.oneshoppoint.yates.repository.GenericDao;
import com.oneshoppoint.yates.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by robinson on 4/13/16.
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    GenericDao<Permission> permissionDao;

    public List<Permission> getAll () {
        return permissionDao.getAll();
    }
}
