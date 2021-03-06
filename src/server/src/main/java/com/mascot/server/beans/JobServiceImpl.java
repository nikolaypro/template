package com.mascot.server.beans;

import com.mascot.common.MailSender;
import com.mascot.common.MascotUtils;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Job;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nikolay on 08.11.2016.
 */
@Service(JobService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class JobServiceImpl extends AbstractMascotService implements JobService {
    private static final String COMPLETE_DATE_FILTER_NAME = "completeDate";
    public static final String SHOW_TAIL_PARAM_NAME = "showTail";
    private static final String NUMBER_NAME = "number";
    private static final String WHERE_STR = " where ";

    @Override
    public BeanTableResult<Job> getList(int start, int count, Map<String, String> orderBy, Map<String, String> filter) {
        final long startMethod = System.currentTimeMillis();
        try {
            String where = "";
            final Map<String, Object> params = new HashMap<>();
            final ZonedDateTime date = getFilterDate(filter);
            if (date != null) {
                filter.remove(COMPLETE_DATE_FILTER_NAME);
                where = "e.completeDate >= :startDate and e.completeDate <= :endDate";
                final Date startDate = MascotUtils.toDate(MascotUtils.getStartWeek(date));
                final Date toDate = MascotUtils.toDate(MascotUtils.getEndWeek(date));
                params.put("startDate", startDate);
                params.put("endDate", toDate);
                if (isShowTail(filter)) {
                    where = "(" + where + " or e.completeDate >= :prevWeekStartDate and e.completeDate < :startDate and e.jobType.order < (select max(j.order) from JobType j where j.deleted <> true)" +
                            ")";
                    final Date prevWeekStartDate = MascotUtils.toDate(MascotUtils.getStartWeek(MascotUtils.getStartWeek(date).minusDays(1)));
                    params.put("prevWeekStartDate", prevWeekStartDate);
                }

                where = WHERE_STR + where;
            }
            filter.remove(SHOW_TAIL_PARAM_NAME);

            // Set Number filter
            final Integer numberFilter = getNumberFilter(filter);
            if (numberFilter != null) {
                where += where.isEmpty() ? WHERE_STR : " and ";
                where += "e.number = :number";
                params.put("number", numberFilter);
                filter.remove(NUMBER_NAME);
            }

            return getResult("select distinct e from Job e " +
                            "left join fetch e.jobType jst " +
                            "left join fetch e.product" + where,
                    "select count(distinct e) from Job e" + where, start, count, orderBy, params, filter
            );
        } finally {
            logger.info("Get all job sub types duration: " + (System.currentTimeMillis() - startMethod) + " msec");
        }
    }

    public ZonedDateTime getFilterDate(Map<String, String> filter) {
        if (filter == null || !filter.containsKey(COMPLETE_DATE_FILTER_NAME)) {
            return null;
        }
        String dateStr = filter.get(COMPLETE_DATE_FILTER_NAME);
        return ZonedDateTime.parse(dateStr);
    }

    private Integer getNumberFilter(Map<String, String> filter) {
        if (filter != null && filter.containsKey(NUMBER_NAME)) {
            String numberStr = filter.get(NUMBER_NAME);
            try {
                return Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private boolean isShowTail(Map<String, String> filter) {
        if (filter != null && filter.containsKey(SHOW_TAIL_PARAM_NAME)) {
            String showTailStr = filter.get(SHOW_TAIL_PARAM_NAME);
            return Boolean.parseBoolean(showTailStr);
        }
        return false;
    }

    @Override
    public void update(Job entity) {
        super.update(entity);
    }

    @Override
    public boolean remove(Long id) {
        return remove(Job.class, id);
    }

    @Override
    public Job find(Long id) {
        try {
            return (Job) em.createQuery("select e from Job e " +
                    "left join fetch e.jobType jst " +
                    "left join fetch e.product " +
                    "where e.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            MailSender.sendErrorAsync("NonUniqueResultException for job id = " + id, e);
            throw new IllegalStateException(e);
        }
    }
}
