package com.oneshoppoint.yates.repository.impl;

import com.oneshoppoint.yates.model.Patient;
import com.oneshoppoint.yates.repository.PatientDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by robinson on 4/12/16.
 */
@Repository
public class PatientDaoImpl extends GenericDaoImpl<Patient> implements PatientDao {
    @PersistenceContext
    private EntityManager entityManager;

    public PatientDaoImpl() {
        super(Patient.class);
    }


    public List<Patient> getByCreator (Integer id) {
        TypedQuery<Patient> query = entityManager.createQuery("SELECT p FROM Patient p WHERE p.createdBy = :id  AND  p.deletedBy IS NULL AND p.deletedOn IS NULL ",Patient.class);

        query.setParameter("id",id);
        List<Patient> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<Patient> search (String pattern) {
        TypedQuery<Patient> query = entityManager.createQuery("SELECT p FROM Patient p WHERE (p.firstname = :pattern OR p.lastname = :pattern)  AND  p.deletedBy IS NULL AND p.deletedOn IS NULL ",Patient.class);
        query.setParameter("pattern",pattern);
        List<Patient> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }
}
