package com.oneshoppoint.yates.repository.impl;

import com.oneshoppoint.yates.model.Prescription;
import com.oneshoppoint.yates.repository.PrescriptionDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by robinson on 4/12/16.
 */
@Repository
public class PrescriptionDaoImpl extends GenericDaoImpl<Prescription> implements PrescriptionDao {
    @PersistenceContext
    private EntityManager entityManager;

    public PrescriptionDaoImpl() {
        super(Prescription.class);
    }


    public List<Prescription> getByPatientId (Integer id) {
        TypedQuery<Prescription> query = entityManager.createQuery("SELECT p FROM Prescription p JOIN p.patient pa WHERE pa.id = :id  AND  p.deletedBy IS NULL AND p.deletedOn IS NULL ",Prescription.class);

        query.setParameter("id",id);
        List<Prescription> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public Prescription getByCode (String code) {
        TypedQuery<Prescription> query = entityManager.createQuery("SELECT p FROM Prescription p WHERE p.code = :code  AND  p.deletedBy IS NULL AND p.deletedOn IS NULL ",Prescription.class);
        query.setParameter("code",code);
        List<Prescription> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }
}
