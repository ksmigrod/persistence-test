package org.superbiz.arqpersistence.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Produces
    @Default
    @RequestScoped
    public EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void disposeEntityManager(@Disposes @Default EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
