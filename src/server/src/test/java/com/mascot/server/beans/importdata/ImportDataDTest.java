package com.mascot.server.beans.importdata;

import com.mascot.common.MascotAppContext;
import com.mascot.server.test.AbstractDbTest;
import com.mascot.server.test.TestDataBaseConfig;
import com.mascot.server.test.TestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

/**
 * Created by Nikolay on 11.01.2017.
 */
@Test
public class ImportDataDTest extends AbstractDbTest {
    @Test
    public void testSaveBank() throws Exception {
        System.out.println("success!!");
        em.createQuery("select e from Job e").getResultList();
    }

}
