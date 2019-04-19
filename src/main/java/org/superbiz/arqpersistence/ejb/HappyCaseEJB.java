package org.superbiz.arqpersistence.ejb;

import org.superbiz.arqpersistence.model.MyEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class HappyCaseEJB {

    @PersistenceContext
    EntityManager em;

    public void update() {
        MyEntity myEntity = em.find(MyEntity.class, "Key 1");
        myEntity.setValue("Another Value 1");
    }
}
