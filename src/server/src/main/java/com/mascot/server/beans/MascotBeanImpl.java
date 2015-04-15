package com.mascot.server.beans;

import com.mascot.server.model.TemplateEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * Created by Nikolay on 25.11.2014.
 */
@Service(MascotBean.NAME)
//@Named(MascotBean.NAME)
//@Scope("singleton")
/*
        1. Singleton, ���� ��������� �� ���������, ��� �� �������� �� ���������
        2. Prototype, ����� ����������� ����� ��� ��� ������ ��������� � ��������
        3. Session, ��������� ���� ��� � �� ������
        4. Request, ��������� ���� ��� �� http-������
        5. Global session, ���� ��� �� ���������� ������ ��� ����������� ���������
*/
//@ManagedBean(MascotBean.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class MascotBeanImpl implements MascotBean, Serializable {
    private int value = 0;

    @PersistenceContext
    private EntityManager em;

    @Override
    public int getValue2() {
        em.createQuery("select e from TemplateEntity e").getResultList();
        final TemplateEntity e = new TemplateEntity();
        e.setName("Created 1");
        em.persist(e);
        return value;
    }

    @Override
    public void setValue2(int value) {
        this.value = value;
    }
}
