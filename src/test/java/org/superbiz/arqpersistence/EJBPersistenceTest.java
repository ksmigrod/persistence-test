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
import org.superbiz.arqpersistence.ejb.HappyCaseEJB;
import org.superbiz.arqpersistence.ejb.NestedTransactionEJB;
import org.superbiz.arqpersistence.ejb.RollbackEJB;
import org.superbiz.arqpersistence.ejb.RollbackWithNestedEJB;
import org.superbiz.arqpersistence.model.MyEntity;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(Arquillian.class)
public class EJBPersistenceTest {
    @Deployment
    public static WebArchive deploy() throws Exception {       // <1>
        return ShrinkWrap.create(WebArchive.class, "EJBPersistenceTest.war")
                .addClasses(MyEntity.class, HappyCaseEJB.class,
                        RollbackEJB.class, RollbackWithNestedEJB.class, NestedTransactionEJB.class)
                .addAsWebInfResource("test-persistence.xml", "persistence.xml");
    }

    @PersistenceContext(name = "myPU")                         // <2>
    private EntityManager em;

    @Inject
    HappyCaseEJB happyCaseEJB;

    @Inject
    RollbackEJB rollbackEJB;

    @Inject
    RollbackWithNestedEJB rollbackWithNestedEJB;

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
    public void shouldUpdateEntityViaEJB() throws Exception {
        happyCaseEJB.update();
    }

    @Test
    @UsingDataSet("datasets/before_update.xml")
    @ShouldMatchDataSet("datasets/before_update.xml")
    @InSequence(3)
    @Transactional(TransactionMode.DISABLED)
    public void shouldNotUpdateViaRollbackEJB() throws Exception {
        rollbackEJB.changeAndRollback();
    }

    @Test
    @UsingDataSet("datasets/before_update.xml")
    @ShouldMatchDataSet("datasets/after_update.xml")
    @InSequence(4)
    @Transactional(TransactionMode.DISABLED)
    public void shouldUpdateEntityViaNestedTransactionEJB() throws Exception {
        rollbackWithNestedEJB.rollbackAndNested();
    }

}
