package org.superbiz.arqpersistence;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.superbiz.arqpersistence.cdi.HappyCase;
import org.superbiz.arqpersistence.cdi.HappyCaseCDI;
import org.superbiz.arqpersistence.model.MyEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(Arquillian.class)
public class CDIPersistenceTest {
    @Deployment
    public static WebArchive deploy() {       // <1>
        return ShrinkWrap.create(WebArchive.class, "CDIPersistenceTest.war")
                .addClasses(MyEntity.class, HappyCase.class, HappyCaseCDI.class)
                .addAsWebInfResource("test-persistence.xml", "persistence.xml");
    }

    @PersistenceContext(name = "myPU")                         // <2>
    private EntityManager em;

    @Inject
    HappyCase happyCase;

    @Before
    public void cleanCache() {
        em.getEntityManagerFactory().getCache().evictAll();
    }

    @Test
    @InSequence(1)
    @Transactional(TransactionMode.COMMIT)
    public void initEntityManager() {                          // <3>
        em.getMetamodel();
    }

    @Test
    @UsingDataSet("datasets/before_update.xml")
    @ShouldMatchDataSet("datasets/after_update.xml")
    @InSequence(2)
    @Transactional(TransactionMode.DISABLED)
    public void shouldUpdateEntityViaEJB() {
        happyCase.update();
    }

}
