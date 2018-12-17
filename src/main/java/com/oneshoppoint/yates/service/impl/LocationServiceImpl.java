package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.Location;
import com.oneshoppoint.yates.model.Image;
import com.oneshoppoint.yates.repository.GenericRecursiveDao;
import com.oneshoppoint.yates.service.LocationService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.LocationForm;
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
public class LocationServiceImpl implements LocationService {
    @Autowired
    GenericRecursiveDao<Location> locationDao;
    @Autowired
    CurrentUser currentUser;
    public void save (LocationForm locationForm) {
        if(locationDao.getByName(locationForm.getName()) != null) {
            throw new RestException(Status.ERROR,"The location "+locationForm.getName()+" already exists");
        }

        Location location = new Location();
        location.setCreatedBy(currentUser.getUser().getId());
        location.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        location.setName(locationForm.getName());
        location.setLabel(locationForm.getLabel());
        location.setEnabled(locationForm.getEnabled());
        if(locationForm.getParentId() != null) {
            Location child = location;
            location = locationDao.find(locationForm.getParentId());
            TreeSet<Location> children = new TreeSet<Location>();
            child.setRoot(false);
            children.add(child);
            location.setChildren(children);
        } else {
            Location oldRoot = locationDao.getRoot();
            if(oldRoot != null) {
                oldRoot.setRoot(false);
                locationDao.update(oldRoot);
            }

            location.setRoot(true);
        }

        locationDao.save(location);
    }

    public void update (LocationForm locationForm) {
        Location location = locationDao.find(locationForm.getId());

        if(location == null) {
            throw new RestException(Status.ERROR,"The location does not exist");
        }

        location.setUpdatedBy(currentUser.getUser().getId());
        location.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        location.setName(locationForm.getName());
        location.setLabel(locationForm.getLabel());
        location.setEnabled(locationForm.getEnabled());
        if(locationForm.getParentId() != null) {
            Location child = location;
            location = locationDao.find(locationForm.getParentId());
            TreeSet<Location> children = new TreeSet<Location>();
            children.add(child);
            location.setChildren(children);
            location.setRoot(false);
        } else {
            Location oldRoot = locationDao.getRoot();
            if(oldRoot != null) {
                oldRoot.setRoot(false);
                locationDao.update(oldRoot);
            }

            location.setRoot(true);
        }

        locationDao.update(location);
    }

    public Location getTree () {
        Location head = locationDao.getRoot();
        if(head != null) {
            filter(head);
        }
        return head;
    }

    public Location getById (Integer id) {
        return locationDao.find(id);
    }

    public void delete (Integer id) {
        Location location = locationDao.find(id);

        if(location == null) {
            throw new RestException(Status.ERROR,"The location does not exist");
        }
        markDelete(location);
        locationDao.delete(location);
    }

    private void markDelete (Location node) {
        node.setDeletedBy(currentUser.getUser().getId());
        node.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        Set<Location> children = node.getChildren();
        for(Location child : children) {
            markDelete(child);
        }
    }

    public List<Location> search (String search) {
        return locationDao.getAll();
    }

    private void filter (Location node) {
        Set<Location> children = node.getChildren();
        Set<Location> childrenFiltered = node.getChildren();

        for(Location child : children) {
            if(child.getDeletedOn() != null) {
                childrenFiltered.remove(child);
            }
        }

        node.setChildren(childrenFiltered);
        for(Location child : childrenFiltered) {
            filter(child);
        }
    }
}