package com.mascot.server.beans.report;

import com.mascot.TestModelFactory;
import com.mascot.server.DummyProgressManager;
import com.mascot.server.model.*;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private JobSubType jobSubType11;
    private JobSubType jobSubType12;
    private JobSubType jobSubType13;
    private JobSubType jobSubType21;
    private JobSubType jobSubType22;
    private JobSubType jobSubType31;
    private JobSubType jobSubType32;
    private JobSubType jobSubType33;
    private JobSubTypeCost jobSubTypeCost111;
    private JobSubTypeCost jobSubTypeCost112;
    private JobSubTypeCost jobSubTypeCost122;
    private JobSubTypeCost jobSubTypeCost123;
    private JobSubTypeCost jobSubTypeCost131;
    private JobSubTypeCost jobSubTypeCost132;
    private JobSubTypeCost jobSubTypeCost133;
    private JobSubTypeCost jobSubTypeCost211;
    private JobSubTypeCost jobSubTypeCost213;
    private JobSubTypeCost jobSubTypeCost221;
    private JobSubTypeCost jobSubTypeCost311;
    private JobSubTypeCost jobSubTypeCost312;
    private JobSubTypeCost jobSubTypeCost321;
    private JobSubTypeCost jobSubTypeCost322;
    private JobSubTypeCost jobSubTypeCost323;
    private JobSubTypeCost jobSubTypeCost333;

    private List<JobSubTypeCost> jobSubTypeCostList;
    private List<JobType> jobTypeList;

    @BeforeTest
    public void init() {
        jobSubTypeCostList = new ArrayList<>();
        jobTypeList = new ArrayList<>();

        product1 = createProduct(1, "product-1");
        product2 = createProduct(2, "product-2");
        product3 = createProduct(3, "product-3");

        jobType1 = createJobType(1, "jobType-1", 1);
        jobType2 = createJobType(2, "jobType-2", 2);
        jobType3 = createJobType(3, "jobType-3", 3);

        //jobType1
        jobSubType11 = createJobSubType(1, "subType-1");
        jobSubType12 = createJobSubType(2, "subType-2");
        jobSubType13 = createJobSubType(3, "subType-3");

        //jobType2
        jobSubType21 = createJobSubType(4, "subType-4");
        jobSubType22 = createJobSubType(5, "subType-5");

        //jobType3
        jobSubType31 = createJobSubType(6, "subType-6");
        jobSubType32 = createJobSubType(7, "subType-7");
        jobSubType33 = createJobSubType(8, "subType-8");

        TestModelFactory.link(jobType1, jobSubType11);
        TestModelFactory.link(jobType1, jobSubType12);
        TestModelFactory.link(jobType1, jobSubType13);

        TestModelFactory.link(jobType2, jobSubType21);
        TestModelFactory.link(jobType2, jobSubType22);

        TestModelFactory.link(jobType3, jobSubType31);
        TestModelFactory.link(jobType3, jobSubType32);
        TestModelFactory.link(jobType3, jobSubType33);

        //jobType1
        jobSubTypeCost111 = createJobSubTypeCost(1, jobSubType11, product1, 2.0);
        jobSubTypeCost112 = createJobSubTypeCost(2, jobSubType11, product2, 3.0);

        jobSubTypeCost122 = createJobSubTypeCost(3, jobSubType12, product2, 5.0);
        jobSubTypeCost123 = createJobSubTypeCost(4, jobSubType12, product3, 1.0);

        jobSubTypeCost131 = createJobSubTypeCost(5, jobSubType13, product1, 2.3);
        jobSubTypeCost132 = createJobSubTypeCost(6, jobSubType13, product2, 10.0);
        jobSubTypeCost133 = createJobSubTypeCost(7, jobSubType13, product3, 8.0);

        //jobType2
        jobSubTypeCost211 = createJobSubTypeCost(8, jobSubType21, product1, 5.0);
        jobSubTypeCost213 = createJobSubTypeCost(9, jobSubType21, product3, 6.0);

        jobSubTypeCost221 = createJobSubTypeCost(10, jobSubType22, product1, 7.0);

        //jobType3
        jobSubTypeCost311 = createJobSubTypeCost(11, jobSubType31, product1, 20.0);
        jobSubTypeCost312 = createJobSubTypeCost(12, jobSubType31, product2, 14.0);

        jobSubTypeCost321 = createJobSubTypeCost(13, jobSubType32, product1, 13.0);
        jobSubTypeCost322 = createJobSubTypeCost(14, jobSubType32, product2, 18.0);
        jobSubTypeCost323 = createJobSubTypeCost(15, jobSubType32, product3, 12.0);

        jobSubTypeCost333 = createJobSubTypeCost(16, jobSubType33, product3, 16.0);

    }

    public void testOneTypeOneJob() {
        Job job1 = TestModelFactory.createJob(1, jobType1, product1, 1);
        List<SalaryReportItem> report = createBuilder().report(() -> Collections.singletonList(job1), Arrays::asList);
        Assert.assertEquals(report.size(), 1);
        Assert.assertEquals(report.get(0).getJobType(), jobType1);
        double expectedCost = jobSubTypeCost111.getCost() + jobSubTypeCost131.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);

        Job job2 = TestModelFactory.createJob(1, jobType1, product2, 1);
        report = createBuilder().report(() -> Collections.singletonList(job2), Arrays::asList);
        Assert.assertEquals(report.size(), 1);
        Assert.assertEquals(report.get(0).getJobType(), jobType1);
        expectedCost = jobSubTypeCost112.getCost() + jobSubTypeCost122.getCost() + jobSubTypeCost132.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);

        Job job3 = TestModelFactory.createJob(1, jobType1, product3, 1);
        report = createBuilder().report(() -> Collections.singletonList(job3), Arrays::asList);
        Assert.assertEquals(report.size(), 1);
        Assert.assertEquals(report.get(0).getJobType(), jobType1);
        expectedCost = jobSubTypeCost123.getCost() + jobSubTypeCost133.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);
    }

    public void testOneTypeThreeJob() {
        Job job1 = TestModelFactory.createJob(1, jobType1, product1, 1);
        Job job2 = TestModelFactory.createJob(2, jobType1, product2, 2);
        Job job3 = TestModelFactory.createJob(3, jobType1, product3, 3);
        List<SalaryReportItem> report = createBuilder().report(() -> Arrays.asList(job1, job2, job3), () -> Arrays.asList());
        Assert.assertEquals(report.size(), 1);
        Assert.assertEquals(report.get(0).getJobType(), jobType1);
        double expectedCost = jobSubTypeCost111.getCost() + jobSubTypeCost131.getCost() + jobSubTypeCost112.getCost() + //Job1
                jobSubTypeCost122.getCost() + jobSubTypeCost132.getCost() + //Job2
                jobSubTypeCost123.getCost() + jobSubTypeCost133.getCost(); // Job3
        Assert.assertEquals(report.get(0).getCost(), expectedCost);


        // Check that tail jobs not corrects fina result because jobs has not final job type
        List<Job> tailsJobs = Arrays.asList(
                TestModelFactory.createJob(4, jobType1, product1, 4)
        );
        report = createBuilder().report(() -> Arrays.asList(job1, job2, job3), () -> tailsJobs);
        Assert.assertEquals(report.size(), 1);
        expectedCost = jobSubTypeCost111.getCost() + jobSubTypeCost131.getCost() + jobSubTypeCost112.getCost() + //Job1
                jobSubTypeCost122.getCost() + jobSubTypeCost132.getCost() + //Job2
                jobSubTypeCost123.getCost() + jobSubTypeCost133.getCost(); // Job3

        Assert.assertEquals(report.get(0).getCost(), expectedCost);
    }

    public void testTwoTypeOneJob() {
        Job job1 = TestModelFactory.createJob(1, jobType2, product1, 1);
        List<SalaryReportItem> report = createBuilder().report(() -> Collections.singletonList(job1), Arrays::asList);
        Assert.assertEquals(report.size(), 2);
        Assert.assertEquals(report.get(0).getJobType(), jobType1);
        double expectedCost = jobSubTypeCost111.getCost() + jobSubTypeCost131.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);

        Assert.assertEquals(report.get(1).getJobType(), jobType2);
        expectedCost = jobSubTypeCost211.getCost() + jobSubTypeCost221.getCost();
        Assert.assertEquals(report.get(1).getCost(), expectedCost);

        Job job2 = TestModelFactory.createJob(1, jobType2, product2, 1);
        report = createBuilder().report(() -> Collections.singletonList(job2), Arrays::asList);
        Assert.assertEquals(report.size(), 1);
        Assert.assertEquals(report.get(0).getJobType(), jobType1);
        expectedCost = jobSubTypeCost112.getCost() + jobSubTypeCost122.getCost() + jobSubTypeCost132.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);

        Job job3 = TestModelFactory.createJob(1, jobType2, product3, 1);
        report = createBuilder().report(() -> Collections.singletonList(job3), Arrays::asList);
        Assert.assertEquals(report.size(), 2);
        Assert.assertEquals(report.get(0).getJobType(), jobType1);
        expectedCost = jobSubTypeCost123.getCost() + jobSubTypeCost133.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);

        Assert.assertEquals(report.get(1).getJobType(), jobType2);
        expectedCost = jobSubTypeCost213.getCost();
        Assert.assertEquals(report.get(1).getCost(), expectedCost);
    }

    public void testTwoTypeThreeJob() {

    }

    public void testThreeTypeOneJob() {
        Job job1 = TestModelFactory.createJob(1, jobType3, product1, 1);
        List<SalaryReportItem> report = createBuilder().report(() -> Collections.singletonList(job1), Arrays::asList);
        Assert.assertEquals(report.size(), 3);
        Assert.assertEquals(report.get(0).getJobType(), jobType1);
        double expectedCost = jobSubTypeCost111.getCost() + jobSubTypeCost131.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);

        Assert.assertEquals(report.get(1).getJobType(), jobType2);
        expectedCost = jobSubTypeCost211.getCost() + jobSubTypeCost221.getCost();
        Assert.assertEquals(report.get(1).getCost(), expectedCost);

        Assert.assertEquals(report.get(2).getJobType(), jobType3);
        expectedCost = jobSubTypeCost311.getCost() + jobSubTypeCost321.getCost();
        Assert.assertEquals(report.get(2).getCost(), expectedCost);

        Job job2 = TestModelFactory.createJob(1, jobType3, product2, 1);
        report = createBuilder().report(() -> Collections.singletonList(job2), Arrays::asList);
        Assert.assertEquals(report.size(), 2);
        Assert.assertEquals(report.get(0).getJobType(), jobType1);
        expectedCost = jobSubTypeCost112.getCost() + jobSubTypeCost122.getCost() + jobSubTypeCost132.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);

        Assert.assertEquals(report.get(1).getJobType(), jobType3);
        expectedCost = jobSubTypeCost312.getCost() + jobSubTypeCost322.getCost();
        Assert.assertEquals(report.get(1).getCost(), expectedCost);

        Job job3 = TestModelFactory.createJob(1, jobType3, product3, 1);
        report = createBuilder().report(() -> Collections.singletonList(job3), Arrays::asList);
        Assert.assertEquals(report.size(), 3);
        Assert.assertEquals(report.get(0).getJobType(), jobType1);
        expectedCost = jobSubTypeCost123.getCost() + jobSubTypeCost133.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);

        Assert.assertEquals(report.get(1).getJobType(), jobType2);
        expectedCost = jobSubTypeCost213.getCost();
        Assert.assertEquals(report.get(1).getCost(), expectedCost);

        Assert.assertEquals(report.get(2).getJobType(), jobType3);
        expectedCost = jobSubTypeCost323.getCost() + jobSubTypeCost333.getCost();
        Assert.assertEquals(report.get(2).getCost(), expectedCost);
    }

    public void testThreeTypeOneJobWithTail() {
        Job job1 = TestModelFactory.createJob(1, jobType3, product1, 1);
        List<Job> tailsJobs = Arrays.asList(
                TestModelFactory.createJob(2, jobType1, product1, 1),
                TestModelFactory.createJob(3, jobType1, product2, 1),
                TestModelFactory.createJob(4, jobType2, product3, 1)
        );
        List<SalaryReportItem> report = createBuilder().report(() -> Collections.singletonList(job1), () -> tailsJobs);
        Assert.assertEquals(report.size(), 2);
        Assert.assertEquals(report.get(0).getJobType(), jobType2);
        double expectedCost = jobSubTypeCost211.getCost() + jobSubTypeCost221.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);

        Assert.assertEquals(report.get(1).getJobType(), jobType3);
        expectedCost = jobSubTypeCost311.getCost() + jobSubTypeCost321.getCost();
        Assert.assertEquals(report.get(1).getCost(), expectedCost);

        Job job2 = TestModelFactory.createJob(1, jobType3, product2, 1);
        report = createBuilder().report(() -> Collections.singletonList(job2), () -> tailsJobs);
        Assert.assertEquals(report.size(), 1);

        Assert.assertEquals(report.get(0).getJobType(), jobType3);
        expectedCost = jobSubTypeCost312.getCost() + jobSubTypeCost322.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);

        Job job3 = TestModelFactory.createJob(1, jobType3, product3, 1);
        report = createBuilder().report(() -> Collections.singletonList(job3), () -> tailsJobs);
        Assert.assertEquals(report.size(), 1);

        Assert.assertEquals(report.get(0).getJobType(), jobType3);
        expectedCost = jobSubTypeCost323.getCost() + jobSubTypeCost333.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);
    }

    public void testThreeTypeManyJobWithTail() {
        Job job1 = TestModelFactory.createJob(1, jobType3, product1, 1);
        Job job2 = TestModelFactory.createJob(2, jobType3, product1, 2);
        Job job3 = TestModelFactory.createJob(3, jobType3, product2, 3);
        Job job4 = TestModelFactory.createJob(4, jobType2, product2, 4);
        Job job5 = TestModelFactory.createJob(5, jobType3, product3, 5);
        List<Job> tailsJobs = Arrays.asList(
                TestModelFactory.createJob(6, jobType1, product1, 6),
                TestModelFactory.createJob(7, jobType1, product2, 7),
                TestModelFactory.createJob(8, jobType2, product3, 8)
        );
        List<SalaryReportItem> report = createBuilder().report(() -> Arrays.asList(job1, job2, job3, job4, job5), () -> tailsJobs);
        Assert.assertEquals(report.size(), 3);
        Assert.assertEquals(report.get(0).getJobType(), jobType1);
        double expectedCost = 0 + // job1 because tail
                jobSubTypeCost111.getCost() + jobSubTypeCost131.getCost() + //job2
                0 + //job3 because tail
                jobSubTypeCost112.getCost() + jobSubTypeCost122.getCost() + jobSubTypeCost132.getCost() + // job4
                0; // job5 because tail
        Assert.assertEquals(report.get(0).getCost(), expectedCost);

        Assert.assertEquals(report.get(1).getJobType(), jobType2);
        expectedCost = jobSubTypeCost211.getCost() + jobSubTypeCost221.getCost() +// job1
                jobSubTypeCost211.getCost() + jobSubTypeCost221.getCost() + //job2
                0 + //job3 because for type 2 not exists any costs
                0 + // job4
                0; // job5 because tail
        Assert.assertEquals(report.get(1).getCost(), expectedCost);

        Assert.assertEquals(report.get(2).getJobType(), jobType3);
        expectedCost = jobSubTypeCost311.getCost() + jobSubTypeCost321.getCost() + // job1
                jobSubTypeCost311.getCost() + jobSubTypeCost321.getCost() + //job2
                jobSubTypeCost312.getCost() + jobSubTypeCost322.getCost() + //job3
                0 + // job4 because not has job type 3
                jobSubTypeCost323.getCost() + jobSubTypeCost333.getCost(); // job5
        Assert.assertEquals(report.get(2).getCost(), expectedCost);

/*

        Assert.assertEquals(report.get(1).getJobType(), jobType3);
        expectedCost = jobSubTypeCost311.getCost() + jobSubTypeCost321.getCost();
        Assert.assertEquals(report.get(1).getCost(), expectedCost);


        report = createBuilder().report(() -> Collections.singletonList(job2), () -> tailsJobs);
        Assert.assertEquals(report.size(), 1);

        Assert.assertEquals(report.get(0).getJobType(), jobType3);
        expectedCost = jobSubTypeCost312.getCost() + jobSubTypeCost3222.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);


        report = createBuilder().report(() -> Collections.singletonList(job3), () -> tailsJobs);
        Assert.assertEquals(report.size(), 1);

        Assert.assertEquals(report.get(0).getJobType(), jobType3);
        expectedCost = jobSubTypeCost323.getCost() + jobSubTypeCost333.getCost();
        Assert.assertEquals(report.get(0).getCost(), expectedCost);
*/
    }

    private SalaryReportBuilder createBuilder() {
        return new SalaryReportBuilder(() -> jobSubTypeCostList, () -> jobTypeList, new DummyProgressManager());
    }

    private JobType createJobType(long id, String name, int order) {
        final JobType result = TestModelFactory.createJobType(id, name, order);
        jobTypeList.add(result);
        return result;
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
