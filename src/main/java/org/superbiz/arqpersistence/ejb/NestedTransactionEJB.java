package org.superbiz.arqpersistence.ejb;

import org.superbiz.arqpersistence.model.MyEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class NestedTransactionEJB {

    @PersistenceContext
    EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void modifyInNewTransaction() {
        MyEntity myEntity = em.find(MyEntity.class, "Key 1");
        myEntity.setValue("Another Value 1");
    }
}
