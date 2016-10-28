package com.mascot.server.beans;

import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.JobSubType;
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
import java.util.Map;

/**
 * Created by Nikolay on 25.10.2016.
 */
@Service(JobSubTypeService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class JobSubTypeServiceImpl extends AbstractMascotService implements JobSubTypeService {
    @Override
    public BeanTableResult<JobSubType> getList(int start, int count, Map<String, String> orderBy) {
        return getResult("select distinct e from JobSubType e left join fetch e.jobType",
                "select count(distinct e) from JobSubType e", start, count, orderBy, new HashMap<>());
    }

    @Override
    public void update(JobSubType entity) {
        super.update(entity);
    }

    @Override
    public boolean remove(Long id) {
        return remove(JobSubType.class, id);
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
            throw new IllegalStateException(e);
        }
    }

    @Override
    public JobSubType findByName(String name) {
        try {
            return (JobSubType) em.createQuery("select e from JobSubType e where e.name = :name")
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            throw new IllegalStateException(e);
        }
    }

}
