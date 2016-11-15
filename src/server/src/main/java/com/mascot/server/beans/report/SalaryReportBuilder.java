package com.mascot.server.beans.report;

import com.mascot.server.model.*;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Nikolay on 15.11.2016.
 */
public class SalaryReportBuilder {
    private final Logger logger = Logger.getLogger(getClass());
    public List<SalaryReportItem> report(Supplier<List<Job>> jobsSupplier, Supplier<List<JobSubTypeCost>> jobSubTypeCostSupplier) {
        StringBuilder log = new StringBuilder();
        final List<Job> jobs = jobsSupplier.get();
        final List<JobSubTypeCost> jobSubTypeCosts = jobSubTypeCostSupplier.get();
        jobs.sort((o1, o2) -> o1.getCompleteDate().compareTo(o2.getCompleteDate()));
        log.append(addJobsLog(jobs));
        log.append("**************************************************\n");
        log.append("Start computing: \n");
        final BiFunction<JobSubType, Product, Optional<JobSubTypeCost>> costFinder = (subType, product) ->
                jobSubTypeCosts.stream().
                        filter(cost ->
                                cost.getJobSubType().getId().equals(subType.getId()) && cost.getProduct().getId().equals(product.getId()))
                        .findFirst();

        final Map<JobType, CostDetails> jobType2Details = new HashMap<>();
        jobs.forEach(job -> {
            log.append("job: ").append(getJobInfo(job)).append("\n");
            final JobType jobType = job.getJobType();
            jobType.getJobSubTypes().forEach(subType -> {
                final Optional<JobSubTypeCost> costOptional = costFinder.apply(subType, job.getProduct());
                log.append("\t - ").append(getCostInfo(costOptional, subType, job.getProduct())).append("\n");
                costOptional.ifPresent(jobSubTypeCost -> {
                    CostDetails costDetails = jobType2Details.get(jobType);
                    if (costDetails == null) {
                        jobType2Details.put(jobType, costDetails = new CostDetails());
                    }
                    final Double newCostValue = costDetails.changeCost(jobSubTypeCost.getCost(), job, subType);
                    log.append(logNewCostValue(jobType, newCostValue));
                });
            });
        });

        log.append(logDetails(jobType2Details));

        logger.info(log.toString());

        return jobType2Details.entrySet().stream().
                map(entry -> new SalaryReportItem(entry.getKey(), entry.getValue().cost)).
                collect(Collectors.toList());
    }

    private StringBuilder logNewCostValue(JobType jobType, Double newCostValue) {
        return new StringBuilder().append("Summary cost for '").
                append(jobType.getName()).
                append("' changed. New value: ").
                append(newCostValue).append("\n");
    }

    private StringBuilder logDetails(Map<JobType, CostDetails> jobSubTypeCosts) {
        StringBuilder result = new StringBuilder();
        jobSubTypeCosts.entrySet().forEach(entry -> {
            final CostDetails costDetails = entry.getValue();
            result.append("------- ").append(entry.getKey().getName()).append(" (cost = ").append(costDetails.cost).append(")  ----------\n");
            costDetails.details.forEach((job, jobDetails) -> {
                result.append("\t ").append(getJobInfo(job));
                jobDetails.forEach((subType, cost) -> {
                    result.append("\t\t - ").append(subType.getName()).append(" (cost = ").append(cost).append(")\n");
                });
            });
        });
        return result;
    }

    private class CostDetails {
        private Map<Job, Map<JobSubType, Double>> details = new HashMap<>();
        private Double cost = 0.0;

        Double changeCost(Double cost, Job job, JobSubType jobSubType) {
            Map<JobSubType, Double> jobSubTypes = details.get(job);
            if (jobSubTypes == null) {
                details.put(job, jobSubTypes = new HashMap<>());
            }
            if (jobSubTypes.containsKey(jobSubType)) {
                logger.error("For job " + job.getId() + " already contains cost for job sub type: " + jobSubType.getId());
            }
            jobSubTypes.put(jobSubType, cost);
            this.cost += cost;
            return this.cost;
        }
    }

    private StringBuilder getCostInfo(Optional<JobSubTypeCost> optional, JobSubType subType, Product product) {
        StringBuilder log = new StringBuilder();
        optional.ifPresent(o -> log.
                append(subType.getName()).
                append(":").
                append(product.getName()).
                append(": cost = ").
                append(optional.get().getCost())
        );
        return log;

    }

    private StringBuilder addJobsLog(List<Job> jobs) {
        StringBuilder log = new StringBuilder();
        log.append("Founded ").append(jobs.size()).append(" jobs:").append("\n");
        jobs.stream().forEachOrdered(job -> log.append(getJobInfo(job)));
        return log;

    }

    private StringBuilder getJobInfo(Job job) {
        return new StringBuilder().
                append("id: ").append(job.getId()).
                append(", number: ").append(job.getNumber()).
                append(", completeDate: ").append(job.getCompleteDate()).
                append(", creationDate: ").append(job.getCreationDate()).
                append(", jobType: ").append(job.getJobType().getName()).append(" (id=").append(job.getJobType().getId()).append(")").
                append(", product: ").append(job.getProduct().getName()).append(" (id=").append(job.getProduct().getId()).append(")").
                append("\n");
    }
}
