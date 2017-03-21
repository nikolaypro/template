package com.mascot;

import com.mascot.server.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

/**
 * Created by Николай on 15.11.2016.
 */
public class TestModelFactory {
    public static JobType createJobType(long id, String name, int order) {
        JobType result = new JobType();
        result.setId(id);
        result.setName(name);
        result.setOrder(order);
        return result;
    }

    public static Product createProduct(long id, String name) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        return product;
    }

    public static JobSubType createJobSubType(long id, String name) {
        JobSubType jobSubType = new JobSubType();
        jobSubType.setId(id);
        jobSubType.setName(name);
        return jobSubType;
    }

    public static void link(JobType jobType, JobSubType jobSubType) {
        jobSubType.setJobType(jobType);
        if (jobType.getJobSubTypes() == null) {
            jobType.setJobSubTypes(new HashSet<>());
        }
        jobType.getJobSubTypes().add(jobSubType);
    }

    public static JobSubTypeCost createCost(long id, JobSubType jobSubType, Product product, double cost) {
        JobSubTypeCost result = new JobSubTypeCost();
        result.setId(id);
        result.setCost(cost);
        result.setJobSubType(jobSubType);
        result.setProduct(product);
        return result;
    }

    public static User createUser(String nameLogin) {
        User result = new User();
        result.setFullName(nameLogin);
        result.setLogin(nameLogin);
        result.setLocale(Locale.ENGLISH);
        result.setPassword("1");
        result.setVersion(0L);
        return result;
    }

    public static Job createJob(long id, JobType type, Product product, Integer number) {
        Job job = new Job();
        job.setId(id);
        job.setProduct(product);
        job.setJobType(type);
        job.setNumber(number);
        job.setCompleteDate(new Date());
        job.setCreationDate(new Date());
        return job;
    }

}
