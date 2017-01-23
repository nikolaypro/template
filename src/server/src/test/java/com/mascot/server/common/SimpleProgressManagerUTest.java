package com.mascot.server.common;

import com.mascot.server.DummyProgressManager;
import com.mascot.server.model.Progress;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * Created by Nikolay on 20.01.2017.
 */
@Test
public class SimpleProgressManagerUTest {
    public void testProgressUpdateInc() {
        SimpleProgressManager progress = new DummyProgressManager();
        Assert.assertNull(progress.getValue());
        Assert.assertNull(progress.getState());

        progress.update("state 1", 1.0);
        Assert.assertNotNull(progress.getValue());
        Assert.assertEquals(1.0, progress.getValue());

        progress.update("state 2", 2.0);
        Assert.assertNotNull(progress.getValue());
        Assert.assertEquals(2.0, progress.getValue());

        progress.update(3.3);
        Assert.assertNotNull(progress.getValue());
        Assert.assertEquals(3.3, progress.getValue(), 0.0001);
        Assert.assertNotNull(progress.getState());
        Assert.assertEquals("state 2", progress.getState());

        progress.inc("state 3", 3.4);
        Assert.assertNotNull(progress.getValue());
        Assert.assertEquals(6.7, progress.getValue(), 0.0001);
        Assert.assertNotNull(progress.getState());
        Assert.assertEquals("state 3", progress.getState());

        progress.update(5.6);
        Assert.assertNotNull(progress.getValue());
        Assert.assertEquals(5.6, progress.getValue(), 0.0001);
        Assert.assertNotNull(progress.getState());
        Assert.assertEquals("state 3", progress.getState());

        progress.flushChanges();
        Assert.assertNotNull(progress.getValue());
        Assert.assertEquals(5.6, progress.getValue(), 0.0001);
        Assert.assertNotNull(progress.getState());
        Assert.assertEquals("state 3", progress.getState());

        progress.inc("state 4", 2.5);
        Assert.assertNotNull(progress.getValue());
        Assert.assertEquals(8.1, progress.getValue(), 0.0001);
        Assert.assertNotNull(progress.getState());
        Assert.assertEquals("state 4", progress.getState());

    }
}
