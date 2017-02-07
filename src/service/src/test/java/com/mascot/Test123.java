package com.mascot;

import com.mascot.service.controller.common.ResultRecord;
import org.testng.annotations.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Николай on 07.02.2017.
 */
@Test
public class Test123 {
    public void test() {
        IntStream.range(1, 11).forEach(this::run1);
    }

    private ResultRecord run1(Integer i) {
        System.out.println("run " + i);
        return ResultRecord.success();
    }

}
