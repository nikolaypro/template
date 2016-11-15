package com.mascot.server.beans.report;

import com.mascot.TestModelFactory;
import com.mascot.server.model.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Николай on 15.11.2016.
 */
@Test
public class SalaryReportBuilderTest {


    private Product product1;
    private Product product2;
    private Product product3;
    private JobType jobType1;
    private JobType jobType2;
    private JobType jobType3;
    private JobSubType jobSubType1;
    private JobSubType jobSubType2;
    private JobSubType jobSubType3;
    private JobSubType jobSubType4;
    private JobSubType jobSubType5;
    private JobSubType jobSubType6;
    private JobSubType jobSubType7;
    private JobSubType jobSubType8;
    private JobSubTypeCost jobSubTypeCost11;
    private JobSubTypeCost jobSubTypeCost12;
    private JobSubTypeCost jobSubTypeCost22;
    private JobSubTypeCost jobSubTypeCost23;
    private JobSubTypeCost jobSubTypeCost31;
    private JobSubTypeCost jobSubTypeCost32;
    private JobSubTypeCost jobSubTypeCost33;
    private JobSubTypeCost jobSubTypeCost41;
    private JobSubTypeCost jobSubTypeCost43;
    private JobSubTypeCost jobSubTypeCost52;
    private JobSubTypeCost jobSubTypeCost61;
    private JobSubTypeCost jobSubTypeCost62;
    private JobSubTypeCost jobSubTypeCost71;
    private JobSubTypeCost jobSubTypeCost72;
    private JobSubTypeCost jobSubTypeCost73;
    private JobSubTypeCost jobSubTypeCost83;

    private List<JobSubTypeCost> jobSubTypeCostList;

    @BeforeTest
    public void init() {
        jobSubTypeCostList = new ArrayList<>();

        product1 = createProduct(1, "product-1");
        product2 = createProduct(2, "product-2");
        product3 = createProduct(3, "product-3");

        jobType1 = createJobType(1, "jobType-1", 1);
        jobType2 = createJobType(2, "jobType-2", 2);
        jobType3 = createJobType(3, "jobType-3", 3);

        jobSubType1 = createJobSubType(1, "subType-1");
        jobSubType2 = createJobSubType(2, "subType-2");
        jobSubType3 = createJobSubType(3, "subType-3");

        jobSubType4 = createJobSubType(4, "subType-4");
        jobSubType5 = createJobSubType(5, "subType-5");

        jobSubType6 = createJobSubType(6, "subType-6");
        jobSubType7 = createJobSubType(7, "subType-7");
        jobSubType8 = createJobSubType(8, "subType-8");

        TestModelFactory.link(jobType1, jobSubType1);
        TestModelFactory.link(jobType1, jobSubType2);
        TestModelFactory.link(jobType1, jobSubType3);

        TestModelFactory.link(jobType2, jobSubType4);
        TestModelFactory.link(jobType2, jobSubType5);

        TestModelFactory.link(jobType3, jobSubType6);
        TestModelFactory.link(jobType3, jobSubType7);
        TestModelFactory.link(jobType3, jobSubType8);

        jobSubTypeCost11 = createJobSubTypeCost(1, jobSubType1, product1, 2.0);
        jobSubTypeCost12 = createJobSubTypeCost(2, jobSubType1, product2, 3.0);

        jobSubTypeCost22 = createJobSubTypeCost(3, jobSubType2, product2, 5.0);
        jobSubTypeCost23 = createJobSubTypeCost(4, jobSubType2, product3, 1.0);

        jobSubTypeCost31 = createJobSubTypeCost(5, jobSubType3, product1, 2.3);
        jobSubTypeCost32 = createJobSubTypeCost(6, jobSubType3, product2, 10.0);
        jobSubTypeCost33 = createJobSubTypeCost(7, jobSubType3, product3, 8.0);

        jobSubTypeCost41 = createJobSubTypeCost(8, jobSubType4, product1, 5.0);
        jobSubTypeCost43 = createJobSubTypeCost(9, jobSubType4, product3, 6.0);

        jobSubTypeCost52 = createJobSubTypeCost(10, jobSubType5, product2, 7.0);

        jobSubTypeCost61 = createJobSubTypeCost(11, jobSubType6, product1, 20.0);
        jobSubTypeCost62 = createJobSubTypeCost(12, jobSubType6, product2, 14.0);

        jobSubTypeCost71 = createJobSubTypeCost(13, jobSubType7, product1, 13.0);
        jobSubTypeCost72 = createJobSubTypeCost(14, jobSubType7, product2, 18.0);
        jobSubTypeCost73 = createJobSubTypeCost(15, jobSubType7, product3, 12.0);

        jobSubTypeCost83 = createJobSubTypeCost(16, jobSubType8, product3, 16.0);

    }

    public void testBuild() {

        Job job = TestModelFactory.createJob(1, jobType1, product1, "number1");

        SalaryReportBuilder builder = new SalaryReportBuilder();
        List<SalaryReportItem> report = builder.report(() -> Arrays.asList(job), () -> jobSubTypeCostList);
        Assert.assertEquals(report.size(), 1);
        Assert.assertEquals(report.get(0).getJobType(), jobType1);
        double expectedCost = jobSubTypeCost11.getCost() + jobSubTypeCost31.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);

    }

    private JobType createJobType(long id, String name, int order) {
        return TestModelFactory.createJobType(id, name, order);
    }

    private JobSubType createJobSubType(long id, String name) {
        return TestModelFactory.createJobSubType(id, name);
    }

    private Product createProduct(long id, String name) {
        return TestModelFactory.createProduct(id, name);
    }

    private JobSubTypeCost createJobSubTypeCost(long id, JobSubType jobSubType, Product product, double cost) {
        JobSubTypeCost result = TestModelFactory.createCost(id, jobSubType, product, cost);
        jobSubTypeCostList.add(result);
        return result;
    }

}
