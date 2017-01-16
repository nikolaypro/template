package com.mascot.server.beans;

import com.mascot.common.MailSender;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.JobSubType;
import com.mascot.server.model.JobType;
import com.mascot.server.model.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by Nikolay on 25.10.2016.
 */
@Service(JobSubTypeService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class JobSubTypeServiceImpl extends AbstractMascotService implements JobSubTypeService {
    @Override
    public BeanTableResult<JobSubType> getList(int start, int count, Map<String, String> orderBy, Map<String, String> filter) {
        return getResult("select distinct e from JobSubType e left join fetch e.jobType",
                "select count(distinct e) from JobSubType e", start, count, orderBy, new HashMap<>(), filter, true, getFilterCorrector());
    }

    private BiFunction<String, String, Object> getFilterCorrector() {
        return (key, value) -> key.equals("useInSalaryReport") ? "e." + key + " = " + value : null;
    }

    @Override
    public void update(JobSubType entity) {
        super.update(entity);
    }

    @Override
    public boolean remove(Long id) {
        em.createQuery("update JobSubTypeCost e set e.deleted = true where e.jobSubType.id = :id").
                setParameter("id", id).
                executeUpdate();
        return markAsDeleted(JobSubType.class, id);
    }

    @Override
    public JobSubType find(Long id) {
        try {
            return (JobSubType) em.createQuery("select e from JobSubType e " +
                    "left join fetch e.jobType " +
                    "where e.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            MailSender.sendErrorAsync("NonUniqueResultException for jobSubType Id = " + id, e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public JobSubType findByName(String name) {
        try {
            return (JobSubType) em.createQuery("select e from JobSubType e where e.name = :name and e.deleted <> true")
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            MailSender.sendErrorAsync("NonUniqueResultException for jobSubType name = '" + name + "'", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<JobSubType> getAll() {
        return em.createQuery("select e from JobSubType e " +
                "left join fetch e.jobType " +
                "where e.deleted <> true " +
                "order by e.jobType.order desc, e.name").getResultList();
    }

}
