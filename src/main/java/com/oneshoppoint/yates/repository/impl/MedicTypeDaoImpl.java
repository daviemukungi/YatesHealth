package com.oneshoppoint.yates.repository.impl;

import com.oneshoppoint.yates.model.MedicType;
import com.oneshoppoint.yates.repository.MedicTypeDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */
@Repository
public class MedicTypeDaoImpl extends  GenericDaoImpl<MedicType> implements MedicTypeDao {
    @PersistenceContext
    EntityManager entityManager;

    public MedicTypeDaoImpl() {
        super(MedicType.class);
    }

    public MedicType getByToken(String token) {
        TypedQuery<MedicType> query = entityManager.createQuery("SELECT mT FROM MedicType mT WHERE mT.token = :token AND mT.deletedBy IS NULL AND mT.deletedOn IS NULL ",MedicType.class);
        query.setParameter("token",token);
        List<MedicType> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}
