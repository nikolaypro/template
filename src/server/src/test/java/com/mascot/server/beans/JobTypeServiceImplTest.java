package com.mascot.server.beans;

import com.mascot.TestModelFactory;
import com.mascot.server.model.JobType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nikolay on 24.10.2016.
 */
@Test
public class JobTypeServiceImplTest {
    public void testMove() {
        List<JobType> types = Arrays.asList(create(100, "Name-1", 0), create(101, "Name-2", 2), create(104, "Name-3", 5), create(106, "Name-4", 6));
        JobTypeServiceImpl.move(100L, types);
        checkOrder(sort(types), 100L, 101L, 104L, 106L);

        JobTypeServiceImpl.move(101L, types);
        checkOrder(sort(types), 101L, 100L, 104L, 106L);

        JobTypeServiceImpl.move(106L, types);
        checkOrder(sort(types), 101L, 100L, 106L, 104L);

        JobTypeServiceImpl.move(104L, types);
        checkOrder(sort(types), 101L, 100L, 104L, 106L);

        JobTypeServiceImpl.move(-1L, types);
        checkOrder(sort(types), 101L, 100L, 104L, 106L);
    }

    public List<JobType> sort(List<JobType> list) {
        Collections.sort(list, (e1, e2) -> e1.getOrder().compareTo(e2.getOrder()));
        return list;
    }

    private void checkOrder(List<JobType> types, long... ids) {
        for (int i=0; i < ids.length; i++) {
            Assert.assertEquals(types.get(i).getId().longValue(), ids[i]);
        }

    }

    private JobType create(long id, String name, int order) {
        return TestModelFactory.createJobType(id, name, order);
    }


}
