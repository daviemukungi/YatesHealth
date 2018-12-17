package com.oneshoppoint.yates.repository.impl;

import com.oneshoppoint.yates.model.User;
import com.oneshoppoint.yates.repository.UserDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */
@Repository
public class UserDaoImpl extends  GenericDaoImpl<User> implements UserDao {
    @PersistenceContext
    EntityManager entityManager;

    public UserDaoImpl () {
        super(User.class);
    }

    public User getByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u JOIN u.address a WHERE a.email =:email  AND u.deletedBy IS NULL AND u.deletedOn IS NULL ",User.class);
        query.setParameter("email",email);
        List<User> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public User getByMedicId(Integer id) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u JOIN u.medic m WHERE m.id =:id  AND u.deletedBy IS NULL AND u.deletedOn IS NULL ",User.class);
        query.setParameter("id",id);
        List<User> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public List<User> getByRegulatorToken(String token) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u JOIN u.medic m JOIN m.medicType mT WHERE m.status = 'pending' AND mT.token =:token  AND u.deletedBy IS NULL AND u.deletedOn IS NULL ",User.class);
        query.setParameter("token",token);
        List<User> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<User> getPendingMedic() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u JOIN u.medic m WHERE m.status = 'pending' AND u.deletedBy IS NULL AND u.deletedOn IS NULL ",User.class);
        List<User> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }
}
