package com.mascot.server.beans;

import com.mascot.common.MailSender;
import com.mascot.common.MascotUtils;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Job;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.text.ParseException;
import java.time.*;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Nikolay on 08.11.2016.
 */
@Service(JobService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class JobServiceImpl extends AbstractMascotService implements JobService {
    @Override
    public BeanTableResult<Job> getList(int start, int count, Map<String, String> orderBy, Map<String, String> filter) {
        String where = "";
        final String completeDateFilterName = "completeDate";
        final Map<String, Object> params = new HashMap<>();
        final ZonedDateTime date;
        if (filter != null && filter.containsKey(completeDateFilterName)) {
            String dateStr = filter.get(completeDateFilterName);
            filter.remove(completeDateFilterName);
            date = ZonedDateTime.parse(dateStr);
            if (date != null) {
                where = "e.completeDate >= :startDate and e.completeDate <= :endDate";
                final Date startDate = isShowTail(filter) ?
                        MascotUtils.toDate(MascotUtils.getStartWeek(MascotUtils.getStartWeek(date).minusDays(1))):
                        MascotUtils.toDate(MascotUtils.getStartWeek(date));
                final Date toDate = MascotUtils.toDate(MascotUtils.getEndWeek(date));
                params.put("startDate", startDate);
                params.put("endDate", toDate);
                if (isShowTail(filter)) {
                    where = "(" + where + " or e.completeDate >= :prevWeekStartDate and e.completeDate < :startDate and e.jobType.order < (select max(j.order) from JobType j where j.deleted <> true)" +
                            ")";
                    final Date prevWeekStartDate = MascotUtils.toDate(MascotUtils.getStartWeek(MascotUtils.getStartWeek(date).minusDays(1)));
                    params.put("prevWeekStartDate", prevWeekStartDate);
                }

                where = " where " + where;

            }
        } else {
            date = null;
        }
        final BeanTableResult<Job> result = getResult("select distinct e from Job e " +
                        "left join fetch e.jobType jst " +
                        "left join fetch e.product" + where,
                "select count(distinct e) from Job e" + where, start, count, orderBy, params, filter
        );
        result.getRows().forEach(e -> e.setTail(isTail(e, date)));
        return result;
    }

    private Boolean isTail(Job e, ZonedDateTime date) {
        return date != null && MascotUtils.toDefaultZonedDateTime(e.getCompleteDate()).isBefore(MascotUtils.getStartWeek(date));
    }

    private boolean isShowTail(Map<String, String> filter) {
        final String showTailParamName = "showTail";
        if (filter != null && filter.containsKey(showTailParamName)) {
            String showTailStr = filter.get(showTailParamName);
            filter.remove(showTailParamName);
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
