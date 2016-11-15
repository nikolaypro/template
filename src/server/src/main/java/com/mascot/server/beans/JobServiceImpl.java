package com.mascot.server.beans;

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
        if (filter != null && filter.containsKey(completeDateFilterName)) {
            String dateStr = filter.get(completeDateFilterName);
            filter.remove(completeDateFilterName);
            ZonedDateTime date = ZonedDateTime.parse(dateStr);
            if (date != null) {
                where = " where e.completeDate >= :startDate and e.completeDate <= :endDate";
                params.put("startDate", MascotUtils.toDate(MascotUtils.getStartWeek(date)));
                params.put("endDate", MascotUtils.toDate(MascotUtils.getEndWeek(date)));
            }
        }
        return getResult("select distinct e from Job e " +
                        "left join fetch e.jobType jst " +
                        "left join fetch e.product" + where,
                "select count(distinct e) from Job e" + where, start, count, orderBy, params, filter);
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
            throw new IllegalStateException(e);
        }
    }
}
