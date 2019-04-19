package org.superbiz.arqpersistence.ejb;

import org.superbiz.arqpersistence.model.MyEntity;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class RollbackWithNestedEJB {

    @PersistenceContext
    EntityManager em;

    @Resource
    EJBContext ctx;

    @Inject
    NestedTransactionEJB nestedTransactionEJB;

    public void rollbackAndNested() {
        MyEntity entity1 = em.find(MyEntity.class, "Key 1");
        entity1.setValue("Rollback Value 1");
        MyEntity entity2 = em.find(MyEntity.class, "Key 2");
        entity2.setValue("Rollback Value 2");
        nestedTransactionEJB.modifyInNewTransaction();
        ctx.setRollbackOnly();
    }
}
