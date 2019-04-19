package org.superbiz.arqpersistence.cdi;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.transaction.TransactionScoped;
import javax.transaction.TransactionSynchronizationRegistry;

@ApplicationScoped
public class TransactionSynchronizationRegistryProducer {

    @Resource(lookup = "comp/TransactionSynchronizationRegistry")
    TransactionSynchronizationRegistry transactionSynchronizationRegistry;

    @Produces
    @RequestScoped
    TransactionSynchronizationRegistry getTransactionSynchronizationRegistry() {
        return transactionSynchronizationRegistry;
    }
}
