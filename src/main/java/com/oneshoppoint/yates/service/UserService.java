package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.User;
import com.oneshoppoint.yates.wrapper.UserForm;
import com.oneshoppoint.yates.wrapper.UserPassword;

import java.util.List;

/**
 * Created by Davie on 4/9/16.
 */
public interface UserService {
    void save(UserForm userForm);
    List<User> getAll();
    User getById(Integer id);
    User getByMedicId(Integer id);
    void confirm (Integer userId,String status);
    List<User> getByRegulatorToken (String token);
    List<User> getPendingMedic();
    void update(UserForm userForm);
    void delete(Integer id);
    List<User> search(String search);
    void updatePassword(UserPassword userPassword);
}
