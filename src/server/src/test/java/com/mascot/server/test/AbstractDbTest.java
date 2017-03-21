package com.mascot.server.test;

import com.mascot.common.MascotAppContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testng.annotations.BeforeClass;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Nikolay on 16.01.2017.
 */
//@DirtiesContext
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration

@ContextConfiguration(classes = TestDataBaseConfig.class)
public class AbstractDbTest extends AbstractTestNGSpringContextTests {
    @PersistenceContext(unitName = "templatePersistenceUnit")
    protected EntityManager em;

    protected TransactionTemplate transaction;

    @Inject
    private PlatformTransactionManager transactionManager;

    @BeforeClass
    public void setUp() throws Exception {
        MascotAppContext.setApplicationContext(applicationContext);
        transaction = new TransactionTemplate(transactionManager);
    }


    /*
    private <A> A doTransaction(A entity, Function<A, A> function) {
        return (A) transaction.execute(status -> function.apply(entity));
    }
*/

    protected void doTransaction(Runnable function) {
        transaction.execute(status -> {function.run(); return null;});
    }


}
