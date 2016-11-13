package com.mascot.server.beans;

import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.JobType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Nikolay on 24.10.2016.
 */
@Service(JobTypeService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class JobTypeServiceImpl extends AbstractMascotService implements JobTypeService {

    @Override
    public BeanTableResult<JobType> getList(int start, int count, Map<String, String> orderBy) {
        return getResult("select distinct e from JobType e",
                "select count(distinct e) from JobType e", start, count, orderBy, new HashMap<>(), new HashMap<>());
    }

    @Override
    public void update(JobType entity) {
        if (entity.getId() == null) {
            entity.setOrder(getMaxOrder() + 1);
        }
        super.update(entity);
    }

    private int getMaxOrder() {
        final Integer max = (Integer) em.createQuery("select max(e.order) from JobType e").getSingleResult();
        return max != null ? max : 0;
    }

    @Override
    public boolean remove(Long id) {
        return remove(JobType.class, id);
    }

    @Override
    public JobType find(Long id) {
        try {
            return (JobType) em.createQuery("select e from JobType e where e.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public JobType findByName(String name) {
        try {
            return (JobType) em.createQuery("select e from JobType e where e.name = :name")
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void moveUp(Long id) {
        move(id, true);
    }

    @Override
    public void moveDown(Long id) {
        move(id, false);
    }

    private void move(Long id, boolean up) {
        final List<JobType> types = em.createQuery("select e from JobType e order by e.order " + (!up ? "desc" : "")).getResultList();
        move(id, types);
    }

    static void move(Long id, List<JobType> types) {
        final JobType[] prev = new JobType[]{null};
        types.stream().forEachOrdered(e -> {
            if (e.getId().equals(id) && prev[0] != null) {
                Integer prevOrder = prev[0].getOrder();
                prev[0].setOrder(e.getOrder());
                e.setOrder(prevOrder);
            }
            prev[0] = e;
        });
    }

    @Override
    public List<JobType> getAll() {
//        gtyjty
/*
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        return em.createQuery("select e from JobType e").getResultList();
    }
}
