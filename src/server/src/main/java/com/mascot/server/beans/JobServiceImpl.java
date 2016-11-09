package com.mascot.server.beans;

import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Job;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nikolay on 08.11.2016.
 */
@Service(JobService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class JobServiceImpl extends AbstractMascotService implements JobService {
    @Override
    public BeanTableResult<Job> getList(int start, int count, Map<String, String> orderBy) {
        return getResult("select distinct e from Job e " +
                        "left join fetch e.jobType jst " +
                        "left join fetch e.product",
                "select count(distinct e) from Job e", start, count, orderBy, new HashMap<>());
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
