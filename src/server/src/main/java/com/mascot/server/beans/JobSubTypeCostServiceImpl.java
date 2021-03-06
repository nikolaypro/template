package com.mascot.server.beans;

import com.mascot.common.MailSender;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.JobSubTypeCost;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Николай on 31.10.2016.
 */
@Service(JobSubTypeCostService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class JobSubTypeCostServiceImpl extends AbstractMascotService implements JobSubTypeCostService {
    @Override
    public BeanTableResult<JobSubTypeCost> getList(int start, int count, Map<String, String> orderBy, Map<String, String> filter) {
        return getResult("select distinct e from JobSubTypeCost e " +
                        "left join fetch e.jobSubType jst " +
                        "left join fetch jst.jobType " +
                        "left join fetch e.product",
                "select count(distinct e) from JobSubTypeCost e", start, count, orderBy, new HashMap<>(), filter, true, null);
    }

    @Override
    public void update(JobSubTypeCost entity) {
        super.update(entity);
    }

    @Override
    public boolean remove(Long id) {
        return markAsDeleted(JobSubTypeCost.class, id);
    }

    @Override
    public JobSubTypeCost find(Long id) {
        try {
            return (JobSubTypeCost) em.createQuery("select e from JobSubTypeCost e " +
                    "left join fetch e.jobSubType jst " +
                    "left join fetch jst.jobType " +
                    "left join fetch e.product " +
                    "where e.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public JobSubTypeCost findCost(Long jobSubTypeId, Long productId) {
        try {
            return (JobSubTypeCost) em.createQuery("select e from JobSubTypeCost e " +
                    "where e.jobSubType.id = :jobSubTypeId and e.product.id = :productId and e.deleted <> true")
                    .setParameter("jobSubTypeId", jobSubTypeId)
                    .setParameter("productId", productId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            MailSender.sendErrorAsync("NonUniqueResultException for jobSubTypeId = " + jobSubTypeId + ", productId = " + productId, e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<JobSubTypeCost> getAll() {
        return em.createQuery("select distinct e from JobSubTypeCost e " +
                "left join fetch e.jobSubType jst " +
                "left join fetch jst.jobType " +
                "left join fetch e.product " +
                "where e.deleted <> true").
                getResultList();
    }

    @Override
    public List<JobSubTypeCost> getAllWithDeleted(boolean useInSalaryReport) {
        final long start = System.currentTimeMillis();
        try {
            return em.createQuery("select distinct e from JobSubTypeCost e " +
                    "left join fetch e.jobSubType jst " +
                    "left join fetch jst.jobType " +
                    "left join fetch e.product " +
                    (useInSalaryReport ? "where jst.useInSalaryReport = true" : "")).
                    getResultList();
        } finally {
            logger.info("Get all job sub type costs duration: " + (System.currentTimeMillis() - start) + " msec");
        }
    }
}
