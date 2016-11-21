package com.mascot.server.beans;

import com.mascot.common.MailSender;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Role;
import com.mascot.server.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nikolay on 16.12.2015.
 */
@Service(UserService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl extends AbstractMascotService implements UserService{
    @Override
    public User loadUserByLogin(String login) {
        try {
            return (User) em.createQuery("select e from User e left join fetch e.roles where e.login = :login")
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            MailSender.sendErrorAsync("NonUniqueResultException for user login = '" + login + "'", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public BeanTableResult<User> getList(int start, int count, Map<String, String> orderBy) {
        return getResult("select distinct e from User e left join fetch e.roles",
                "select count(distinct e) from User e", start, count, orderBy, new HashMap<>(), new HashMap<>());

/*
        final String orderByStr = MascotUtils.buildOrderByString(orderBy, "e");
        return em.createQuery("select distinct e from User e left join fetch e.roles " + orderByStr).
                setMaxResults(count).
                setFirstResult(start).
                getResultList();
*/
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
    public void update(User user) {
        super.update(user);
    }

    @Override
    public boolean remove(Long userId) {
        return remove(User.class, userId);
/*
        final User reference = em.getReference(User.class, userId);
        em.remove(reference);
        return true;
*/
    }

    @Override
    public User find(Long userId) {
        try {
            return (User) em.createQuery("select e from User e left join fetch e.roles where e.id = :id")
                    .setParameter("id", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            MailSender.sendErrorAsync("NonUniqueResultException for user id = '" + userId + "'", e);
            throw new IllegalStateException(e);
        }
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
