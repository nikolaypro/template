package com.mascot.service.controller.integration;

import com.mascot.server.model.IntegrationActionType;
import com.mascot.server.model.IntegrationLog;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

/**
 * Created by Nikolay on 10.04.2017.
 */
public class IntegrationControllerTest {
    @Test
    public void testGroupNotHasStartAndEnd() {
        IntegrationLog log1 = createLog(1L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log2 = createLog(2L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log3 = createLog(3L, IntegrationActionType.SEND_REMOVE_USER);
        SortedMap<Integer, List<IntegrationLog>> map = IntegrationController.groupLog(Arrays.asList(log1, log2, log3));
        Assert.assertEquals(map.size(), 1);
        Assert.assertEquals(map.get(0).size(), 3);
        Assert.assertEquals(map.get(0).get(0).getId().longValue(), 1L);
        Assert.assertEquals(map.get(0).get(1).getId().longValue(), 2L);
        Assert.assertEquals(map.get(0).get(2).getId().longValue(), 3L);
    }

    @Test
    public void testGroupNotHasStartAndHasEnd() {
        IntegrationLog log1 = createLog(1L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log2 = createLog(2L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log3 = createLog(3L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log4 = createLog(4L, IntegrationActionType.END);
        SortedMap<Integer, List<IntegrationLog>> map = IntegrationController.groupLog(Arrays.asList(log1, log2, log3, log4));
        Assert.assertEquals(map.size(), 1);
        Assert.assertEquals(map.get(0).size(), 4);
        Assert.assertEquals(map.get(0).get(0).getId().longValue(), 1L);
        Assert.assertEquals(map.get(0).get(1).getId().longValue(), 2L);
        Assert.assertEquals(map.get(0).get(2).getId().longValue(), 3L);
        Assert.assertEquals(map.get(0).get(3).getId().longValue(), 4L);
    }

    @Test
    public void testGroupNotHasStartAndHasEndTwoGroup() {
        IntegrationLog log1 = createLog(1L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log2 = createLog(2L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log3 = createLog(3L, IntegrationActionType.END);
        IntegrationLog log4 = createLog(4L, IntegrationActionType.SEND_REMOVE_USER);
        SortedMap<Integer, List<IntegrationLog>> map = IntegrationController.groupLog(Arrays.asList(log1, log2, log3, log4));
        Assert.assertEquals(map.size(), 2);
        Assert.assertEquals(map.get(0).size(), 3);
        Assert.assertEquals(map.get(1).size(), 1);
        Assert.assertEquals(map.get(0).get(0).getId().longValue(), 1L);
        Assert.assertEquals(map.get(0).get(1).getId().longValue(), 2L);
        Assert.assertEquals(map.get(0).get(2).getId().longValue(), 3L);
        Assert.assertEquals(map.get(1).get(0).getId().longValue(), 4L);
    }

    @Test
    public void testGroupHasStartAndNotHasEnd() {
        IntegrationLog log1 = createLog(1L, IntegrationActionType.START);
        IntegrationLog log2 = createLog(2L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log3 = createLog(3L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log4 = createLog(4L, IntegrationActionType.SEND_REMOVE_USER);
        SortedMap<Integer, List<IntegrationLog>> map = IntegrationController.groupLog(Arrays.asList(log1, log2, log3, log4));
        Assert.assertEquals(map.size(), 1);
        Assert.assertEquals(map.get(0).size(), 4);
        Assert.assertEquals(map.get(0).get(0).getId().longValue(), 1L);
        Assert.assertEquals(map.get(0).get(1).getId().longValue(), 2L);
        Assert.assertEquals(map.get(0).get(2).getId().longValue(), 3L);
        Assert.assertEquals(map.get(0).get(3).getId().longValue(), 4L);
    }

    @Test
    public void testGroupHasStartAndEnd() {
        IntegrationLog log1 = createLog(1L, IntegrationActionType.START);
        IntegrationLog log2 = createLog(2L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log3 = createLog(3L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log4 = createLog(4L, IntegrationActionType.END);
        SortedMap<Integer, List<IntegrationLog>> map = IntegrationController.groupLog(Arrays.asList(log1, log2, log3, log4));
        Assert.assertEquals(map.size(), 1);
        Assert.assertEquals(map.get(0).size(), 4);
        Assert.assertEquals(map.get(0).get(0).getId().longValue(), 1L);
        Assert.assertEquals(map.get(0).get(1).getId().longValue(), 2L);
        Assert.assertEquals(map.get(0).get(2).getId().longValue(), 3L);
        Assert.assertEquals(map.get(0).get(3).getId().longValue(), 4L);
    }

    @Test
    public void testGroupHasStartAndEndTwoGroup() {
        IntegrationLog log1 = createLog(1L, IntegrationActionType.START);
        IntegrationLog log2 = createLog(2L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log3 = createLog(3L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log4 = createLog(4L, IntegrationActionType.END);
        IntegrationLog log5 = createLog(5L, IntegrationActionType.START);
        IntegrationLog log6 = createLog(6L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log7 = createLog(7L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log8 = createLog(8L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log9 = createLog(9L, IntegrationActionType.END);
        SortedMap<Integer, List<IntegrationLog>> map = IntegrationController.groupLog(Arrays.asList(log1, log2, log3, log4, log5, log6, log7, log8, log9));
        Assert.assertEquals(map.size(), 2);

        Assert.assertEquals(map.get(0).size(), 4);
        Assert.assertEquals(map.get(0).get(0).getId().longValue(), 1L);
        Assert.assertEquals(map.get(0).get(1).getId().longValue(), 2L);
        Assert.assertEquals(map.get(0).get(2).getId().longValue(), 3L);
        Assert.assertEquals(map.get(0).get(3).getId().longValue(), 4L);

        Assert.assertEquals(map.get(1).size(), 5);
        Assert.assertEquals(map.get(1).get(0).getId().longValue(), 5L);
        Assert.assertEquals(map.get(1).get(1).getId().longValue(), 6L);
        Assert.assertEquals(map.get(1).get(2).getId().longValue(), 7L);
        Assert.assertEquals(map.get(1).get(3).getId().longValue(), 8L);
        Assert.assertEquals(map.get(1).get(4).getId().longValue(), 9L);
    }

    @Test
    public void testGroupHasStartAndEndOneGroupBecauseEmpty() {
        IntegrationLog log1 = createLog(1L, IntegrationActionType.START);
        IntegrationLog log2 = createLog(2L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log3 = createLog(3L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log4 = createLog(4L, IntegrationActionType.END);
        IntegrationLog log5 = createLog(5L, IntegrationActionType.START);
        IntegrationLog log6 = createEmptyLog(6L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log7 = createEmptyLog(7L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log8 = createEmptyLog(8L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log9 = createLog(9L, IntegrationActionType.END);
        SortedMap<Integer, List<IntegrationLog>> map = IntegrationController.groupLog(Arrays.asList(log1, log2, log3, log4, log5, log6, log7, log8, log9));
        Assert.assertEquals(map.size(), 1);

        Assert.assertEquals(map.get(0).size(), 4);
        Assert.assertEquals(map.get(0).get(0).getId().longValue(), 1L);
        Assert.assertEquals(map.get(0).get(1).getId().longValue(), 2L);
        Assert.assertEquals(map.get(0).get(2).getId().longValue(), 3L);
        Assert.assertEquals(map.get(0).get(3).getId().longValue(), 4L);
    }

    @Test
    public void testGroupHasStartAndEndOneGroupBecauseEmpty2() {
        IntegrationLog log1 = createLog(1L, IntegrationActionType.START);
        IntegrationLog log2 = createEmptyLog(2L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log3 = createLog(3L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log4 = createLog(4L, IntegrationActionType.END);
        IntegrationLog log5 = createLog(5L, IntegrationActionType.START);
        IntegrationLog log6 = createEmptyLog(6L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log7 = createEmptyLog(7L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log8 = createEmptyLog(8L, IntegrationActionType.SEND_REMOVE_USER);
        IntegrationLog log9 = createLog(9L, IntegrationActionType.END);
        SortedMap<Integer, List<IntegrationLog>> map = IntegrationController.groupLog(Arrays.asList(log1, log2, log3, log4, log5, log6, log7, log8, log9));
        Assert.assertEquals(map.size(), 1);

        Assert.assertEquals(map.get(0).size(), 3);
        Assert.assertEquals(map.get(0).get(0).getId().longValue(), 1L);
        Assert.assertEquals(map.get(0).get(1).getId().longValue(), 3L);
        Assert.assertEquals(map.get(0).get(2).getId().longValue(), 4L);

    }

    private IntegrationLog createLog(Long id, IntegrationActionType type) {
        IntegrationLog result = new IntegrationLog();
        result.setActionType(type);
        result.setId(id);
        result.setCount((IntegrationActionType.START.equals(type) || IntegrationActionType.END.equals(type)) ? null : 1);
        return result;
    }

    private IntegrationLog createEmptyLog(Long id, IntegrationActionType type) {
        IntegrationLog result = createLog(id, type);
        result.setCount(null);
        return result;
    }
}
