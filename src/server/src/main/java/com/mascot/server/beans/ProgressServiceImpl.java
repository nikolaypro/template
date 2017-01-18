package com.mascot.server.beans;

import com.mascot.server.model.Progress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Nikolay on 18.01.2017.
 */
@Service(ProgressService.NAME)
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ProgressServiceImpl extends AbstractMascotService implements ProgressService {

    @Override
    public Long start(String state) {
        final Progress progress = new Progress();
        progress.setState(state);
        progress.setValue(0);
        progress.setLastUpdate(new Date());
        em.persist(progress);
        em.flush();
        if (progress.getId() == null) {
            throw new IllegalStateException("Id must be not null");
        }
        return progress.getId();
    }

    @Override
    public void update(Long id, String state, int value) {
        final Progress progress = findProgress(id);
        if (progress == null) {
            return;
        }
        progress.setState(state);
        progress.setValue(value);
        progress.setLastUpdate(new Date());
        em.merge(progress);
    }

    @Override
    public void update(Long id, int value) {
        final Progress progress = findProgress(id);
        if (progress == null) {
            return;
        }
        progress.setValue(value);
        progress.setLastUpdate(new Date());
        em.merge(progress);
    }

    @Override
    public void inc(Long id, String state, int incValue) {
        final Progress progress = findProgress(id);
        if (progress == null) {
            return;
        }
        progress.setState(state);
        progress.setValue(progress.getValue() != null ? progress.getValue() + incValue : incValue);
        progress.setLastUpdate(new Date());
        em.merge(progress);
    }

    @Override
    public void inc(Long id, int incValue) {
        final Progress progress = findProgress(id);
        if (progress == null) {
            return;
        }
        progress.setValue(progress.getValue() != null ? progress.getValue() + incValue : incValue);
        progress.setLastUpdate(new Date());
        em.merge(progress);
    }

    @Override
    public Progress get(Long id) {
        return em.find(Progress.class, id);
    }

    @Override
    public void finish(Long id) {
        em.createQuery("delete from Progress where id = :id").setParameter("id", id).executeUpdate();
    }

    private Progress findProgress(Long id) {
        final Progress progress = em.find(Progress.class, id);
        if (progress == null) {
            logger.error("Not found progress with id = " + id);
        }
        return progress;
    }
}
