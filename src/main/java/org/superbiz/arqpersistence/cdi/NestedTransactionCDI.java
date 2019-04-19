package org.superbiz.arqpersistence.cdi;

import org.superbiz.arqpersistence.model.MyEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class NestedTransactionCDI implements NestedTransaction {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void modifyInNewTransaction() {
        MyEntity myEntity = em.find(MyEntity.class, "Key 1");
        myEntity.setValue("Another Value 1");
    }
}
