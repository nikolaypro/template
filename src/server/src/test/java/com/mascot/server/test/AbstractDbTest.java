package com.mascot.server.test;

import com.mascot.common.MascotAppContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;

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

    @BeforeClass
    public void setUp() throws Exception {
        MascotAppContext.setApplicationContext(applicationContext);
    }

}
