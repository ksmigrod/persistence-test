package org.superbiz.arqpersistence.cdi;

import org.superbiz.arqpersistence.model.MyEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.Transactional;

@ApplicationScoped
public class RollbackWithNestedCDI implements RollbackWithNested {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    TransactionSynchronizationRegistry transactionSynchronizationRegistry;

    @Inject
    NestedTransaction nestedTransaction;

    @Override
    @Transactional
    public void rollbackAndNested() {
        MyEntity entity1 = entityManager.find(MyEntity.class, "Key 1");
        entity1.setValue("Rollback Value 1");
        MyEntity entity2 = entityManager.find(MyEntity.class, "Key 2");
        entity2.setValue("Rollback Value 2");
        nestedTransaction.modifyInNewTransaction();
        transactionSynchronizationRegistry.setRollbackOnly();
    }
}
