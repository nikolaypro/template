package com.mascot.common;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Test
public class MascotUtilsTest {
    public void testBuildWhereByString() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "aaa");
        map.put("secondEntity.name", "bbb");
        String result = MascotUtils.buildWhereByString(map, "e", null);
        Assert.assertEquals(result, "where e.name like '%aaa%' and e.secondEntity.name like '%bbb%'");

    }

    public void testBuildWhereByStringWithFilterCorrector() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "aaa");
        map.put("secondEntity.name", "bbb");
        String result = MascotUtils.buildWhereByString(map, "e", (p, v) -> p.equals("name") ? "e.name = '" + v + "'" : null);
        Assert.assertEquals(result, "where e.name = 'aaa' and e.secondEntity.name like '%bbb%'");

    }

    public void test1() throws ParseException {
        StdDateFormat.instance.parse("2016-11-12T20:00:00.000Z");
    }
}