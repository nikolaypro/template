package com.mascot.server.beans.report;

import com.mascot.common.MascotUtils;
import com.mascot.server.model.*;
import org.apache.log4j.Logger;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Nikolay on 15.11.2016.
 */
public class SalaryReportBuilder {
    private final Logger logger = Logger.getLogger(getClass());
    private final Supplier<List<JobSubTypeCost>> jobSubTypeCostSupplier;
    private final Supplier<List<JobType>> jobTypeSupplier;

    public SalaryReportBuilder(Supplier<List<JobSubTypeCost>> jobSubTypeCostSupplier, Supplier<List<JobType>> jobTypeSupplier) {
        this.jobSubTypeCostSupplier = jobSubTypeCostSupplier;
        this.jobTypeSupplier = jobTypeSupplier;
    }

    public List<SalaryReportItem> report(Supplier<List<Job>> jobsSupplier, Supplier<List<Job>> tailJobSupplier) {
        StringBuilder log = new StringBuilder();
        final List<Job> jobs = jobsSupplier.get();
        final List<JobSubTypeCost> jobSubTypeCosts = jobSubTypeCostSupplier.get();
        final List<JobType> allJobTypes = jobTypeSupplier.get();
        final List<Job> tailJobs = new ArrayList<>(tailJobSupplier.get());
        allJobTypes.sort((e1, e2) -> e1.getOrder() > e2.getOrder() ? 1 : -1);
        jobs.sort(Comparator.comparing(Job::getCompleteDate));
        log.append(addJobsLog(jobs));
        log.append("\n **************************************************\n");
        log.append("Start computing:");
        final BiFunction<JobSubType, Product, Optional<JobSubTypeCost>> costFinder = (subType, product) ->
                jobSubTypeCosts.stream().
                        filter(cost ->
                                cost.getJobSubType().getId().equals(subType.getId()) && cost.getProduct().getId().equals(product.getId()))
                        .findFirst();

        final Map<JobType, CostDetails> jobType2Details = new HashMap<>();
        jobs.forEach(job -> {
            log.append("\n").append(getJobInfo(job));
            getCompletedJobTypes(job, allJobTypes, tailJobs, log).forEachOrdered(jobType -> {

                jobType.getJobSubTypes().forEach(subType -> {

                    final Optional<JobSubTypeCost> costOptional = costFinder.apply(subType, job.getProduct());
                    costOptional.ifPresent(e ->
                                    log.append("\n\t - ").append(getCostInfo(e, subType, job.getProduct()))
                    );
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
        });

        log.append(logDetails(jobType2Details));

        logger.info(log.toString());

        return jobType2Details.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey().getOrder())).
                map(entry -> new SalaryReportItem(entry.getKey(), entry.getValue().cost)).
                collect(Collectors.toList());
    }

    private Stream<JobType> getCompletedJobTypes(Job job, List<JobType> allJobTypes, List<Job> tailJobs, StringBuilder log) {
        final JobType jobType = job.getJobType();
        final Product product = job.getProduct();

        boolean jobTypeIsFinal = jobType.getOrder().equals(allJobTypes.get(allJobTypes.size() - 1).getOrder());

        final Optional<Job> tailJob = jobTypeIsFinal ?
                tailJobs.stream().filter(e -> e.getProduct().getId().equals(product.getId())).findFirst() :
                Optional.empty();
        tailJob.ifPresent(e -> logInfoAboutTailJob(log, e));
        final Stream<JobType> sorted = allJobTypes.stream().
                filter(e -> (!tailJob.isPresent() || e.getOrder() > tailJob.get().getJobType().getOrder()) && e.getOrder() <= jobType.getOrder()).
                sorted(Comparator.comparing(JobType::getOrder));
        tailJob.ifPresent(tailJobs::remove);
        return sorted;
    }

    private StringBuilder logInfoAboutTailJob(StringBuilder log, Job job) {
        return log.append("\n corrected by tail ").append(getJobInfo(job));
    }

    private StringBuilder logNewCostValue(JobType jobType, Double newCostValue) {
        return new StringBuilder().append("\tSummary cost for '").
                append(jobType.getName()).
                append("' changed. New value: ").
                append(newCostValue);
    }

    private StringBuilder logDetails(Map<JobType, CostDetails> jobSubTypeCosts) {
        StringBuilder result = new StringBuilder("\n******************* FINAL RESULT ***************************\n");
        jobSubTypeCosts.entrySet().stream().
                sorted(Comparator.comparing(e -> e.getKey().getOrder())).
                forEachOrdered(entry -> {
                    final CostDetails costDetails = entry.getValue();
                    result.append("- [").append(entry.getKey().getName()).append("] summary cost: ").append(costDetails.cost).append("  ----------\n");
                    costDetails.details.forEach((job, jobDetails) -> {
                        result.append("\t ").append(getJobInfo(job)).append("\n");
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

    private StringBuilder getCostInfo(JobSubTypeCost cost, JobSubType subType, Product product) {
        return new StringBuilder().
                append(subType.getName()).
                append("(type:").
                append(subType.getJobType().getName()).
                append("):").
                append(product.getName()).
                append(": cost = ").
                append(cost);

    }

    private StringBuilder addJobsLog(List<Job> jobs) {
        StringBuilder log = new StringBuilder();
        log.append("Founded ").append(jobs.size()).append(" jobs:").append("\n");
        jobs.stream().forEachOrdered(job -> log.append("\t").append(getJobInfo(job)));
        return log;

    }

    private StringBuilder getJobInfo(Job job) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new StringBuilder().
                append("job id: ").append(job.getId()).
                append(", number: ").append(job.getNumber()).
                append(", completeDate: ").append(MascotUtils.toDefaultZonedDateTime(job.getCompleteDate()).format(dateFormatter)).
                append(", creationDate: ").append(MascotUtils.toDefaultZonedDateTime(job.getCreationDate()).format(dateTimeFormatter)).
                append(", jobType: ").append(job.getJobType().getName()).append(" (id=").append(job.getJobType().getId()).append(")").
                append(", product: ").append(job.getProduct().getName()).append(" (id=").append(job.getProduct().getId()).append(")");
    }


}
