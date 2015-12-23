package com.mascot.server.beans;

import com.mascot.server.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

/**
 * Created by Nikolay on 16.12.2015.
 */
@Service(UserService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
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
    public Collection<User> getUsers() {
        return em.createQuery("select e from User e left join fetch e.roles").getResultList();
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
