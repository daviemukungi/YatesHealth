package com.oneshoppoint.yates.repository;

import com.oneshoppoint.yates.model.User;

import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */
public interface UserDao extends GenericDao<User> {
    User getByEmail(String email);
    User getByMedicId(Integer id);
    List<User> getByRegulatorToken(String token);
    List<User> getPendingMedic();
}
