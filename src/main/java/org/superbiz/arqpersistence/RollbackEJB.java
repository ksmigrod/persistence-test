package org.superbiz.arqpersistence;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class RollbackEJB {

    @Resource
    EJBContext ctx;

    @PersistenceContext
    EntityManager em;

    public void changeAndRollback() {
        MyEntity myEntity = em.find(MyEntity.class, "Key 1");
        myEntity.setValue("Another Value 1");
        ctx.setRollbackOnly();
    }
}
