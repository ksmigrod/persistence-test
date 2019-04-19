package org.superbiz.arqpersistence.cdi;

import org.superbiz.arqpersistence.model.MyEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.Transactional;

@ApplicationScoped
public class RollbackCaseCDI implements RollbackCase {

    @Inject
    EntityManager entityManager;

    @Inject
    TransactionSynchronizationRegistry transactionSynchronizationRegistry;

    @Override
    @Transactional
    public void changeAndRollback() {
        MyEntity myEntity = entityManager.find(MyEntity.class, "Key 1");
        myEntity.setValue("Should be rolled back.");
        transactionSynchronizationRegistry.setRollbackOnly();
    }
}
