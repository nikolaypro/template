package com.mascot.server.beans;

import com.mascot.common.MascotUtils;
import com.mascot.server.model.Role;
import com.mascot.server.model.User;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * Created by Nikolay on 16.12.2015.
 */
@Service(UserService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
    private final Logger logger = Logger.getLogger(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public User loadUserByLogin(String login) {
        try {
            return (User) em.createQuery("select e from User e left join fetch e.roles where e.login = :login")
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Collection<User> getUsers(int start, int count, Map<String, String> orderBy) {
        final String orderByStr = MascotUtils.buildOrderByString(orderBy, "e");
        return em.createQuery("select distinct e from User e left join fetch e.roles " + orderByStr).
                setMaxResults(count).
                setFirstResult(start).
                getResultList();
    }


    @Override
    public Role getRole(String roleName) {
        try {
            return (Role) em.createQuery("select e from Role e where e.name = :name").setParameter("name", roleName).getSingleResult();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    @Override
    public boolean removeUser(Long userId) {
        final User reference = em.getReference(User.class, userId);
        em.remove(reference);
        return true;
    }

    @Override
    public User findUser(Long userId) {
        try {
            return (User) em.createQuery("select e from User e left join fetch e.roles where e.id = :id")
                    .setParameter("id", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public int getUsersCount() {
        return ((Long) em.createQuery("select count(e) from User e ")
                .getSingleResult()).intValue();
    }

    @Override
    public User getCurrentUser() {
        final org.springframework.security.core.userdetails.User principal = getPrincipal();
        if (principal == null) return null;
        return loadUserByLogin(principal.getUsername());
    }

    @Override
    public Long getCurrentUserId() {
        final org.springframework.security.core.userdetails.User principal = getPrincipal();
        if (principal == null) return null;
        final String login = principal.getUsername();
        final List list = em.createQuery("select e.id from User e where e.login = :login")
                .setParameter("login", login)
                .getResultList();
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Not found user with login: '" + login);
        }
        return (Long) list.get(0);

    }

    private org.springframework.security.core.userdetails.User getPrincipal() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        return (org.springframework.security.core.userdetails.User) auth.getPrincipal();
    }


}
