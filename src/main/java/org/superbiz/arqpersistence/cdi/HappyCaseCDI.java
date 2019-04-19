package org.superbiz.arqpersistence.cdi;

import org.superbiz.arqpersistence.model.MyEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class HappyCaseCDI implements HappyCase {

    @Inject
    EntityManager em;

    @Transactional
    public void update() {
        MyEntity myEntity = em.find(MyEntity.class, "Key 1");
        myEntity.setValue("Another Value 1");
    }
}
