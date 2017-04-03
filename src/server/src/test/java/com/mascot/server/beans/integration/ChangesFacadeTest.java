package com.mascot.server.beans.integration;

import com.mascot.TestModelFactory;
import com.mascot.server.model.EntityActionType;
import com.mascot.server.model.EntityType;
import com.mascot.server.model.SentEntity;
import com.mascot.server.model.User;
import com.mascot.server.test.AbstractDbTest;
import org.hibernate.Session;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

/**
 * Created by Nikolay on 21.03.2017.
 */
@Test
public class ChangesFacadeTest extends AbstractDbTest {

    private List<Long> find(EntityType entityType, EntityActionType actionType) {
        return new ChangesFacade(em, entityType, actionType).find();
    }

    private void done(List<Long> ids, EntityType entityType, EntityActionType actionType) {
        new ChangesFacade(em, entityType, actionType).done(ids);
    }

    @Test
    public void testNewUsers() {
        doTransaction(() -> {
            List<Long> newList = find(EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(newList.size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.REMOVE).size(), 0);
            Assert.assertEquals(getSentEntities().size(), 0);

            done(newList, EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 0);

            // Not web user
            em.persist(TestModelFactory.createUser("user-1"));

            newList = find(EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(newList.size(), 0);
            Assert.assertEquals(getSentEntities().size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.REMOVE).size(), 0);

            done(newList, EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 0);

            // New web user
            markAsWeb("user-1", true);

            newList = find(EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(newList.size(), 1);
            Assert.assertEquals(newList.get(0), getUserId("user-1"));
            Assert.assertEquals(getSentEntities().size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.REMOVE).size(), 0);

            done(newList, EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 1);

            // Repeat call
            newList = find(EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(newList.size(), 0);
            Assert.assertEquals(getSentEntities().size(), 1);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.REMOVE).size(), 0);

            done(newList, EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 1);


            // Create two users
            em.persist(TestModelFactory.createUser("user-2"));
            em.persist(TestModelFactory.createUser("user-3"));
            markAsWeb("user-2", true);
            markAsWeb("user-3", true);

            newList = find(EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(newList.size(), 2);
            Assert.assertEquals(newList.stream().filter(e -> e.equals(getUserId("user-2"))).count(), 1);
            Assert.assertEquals(newList.stream().filter(e -> e.equals(getUserId("user-3"))).count(), 1);
            Assert.assertEquals(newList.stream().filter(e -> e.equals(getUserId("user-1"))).count(), 0);
            Assert.assertEquals(getSentEntities().size(), 1);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.REMOVE).size(), 0);

            done(newList, EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 3);

        });
    }

    private Long getUserId(String s) {
        return (Long) em.createQuery("select e.id from User e where e.login = :name")
                .setParameter("name", s)
                .getSingleResult();
    }


    @Test(dependsOnMethods = {"testNewUsers"})
    public void testModifiedUsers() {
        doTransaction(() -> {
            // initial: 3 new users sent: user-1, user-2, user-3
            // Check initial
            List<Long> users = find(EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(users.size(), 0);
            Assert.assertEquals(getSentEntities().size(), 3);
            done(users, EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 3);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.REMOVE).size(), 0);

            // Update user-1
            updateUser("user-1");
            Assert.assertEquals(find(EntityType.USER, EntityActionType.INSERT).size(), 0);
            users = find(EntityType.USER, EntityActionType.UPDATE);
            Assert.assertEquals(users.size(), 1);
            Assert.assertEquals(users.stream().filter(e -> e.equals(getUserId("user-1"))).count(), 1);
            Assert.assertEquals(getSentEntities().size(), 3);
            done(users, EntityType.USER, EntityActionType.UPDATE);
            Assert.assertEquals(getSentEntities().size(), 3);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.REMOVE).size(), 0);

            Assert.assertEquals(find(EntityType.USER, EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.REMOVE).size(), 0);


            // Update user-1, user-3
            updateUser("user-1");
            updateUser("user-3");

            Assert.assertEquals(find(EntityType.USER, EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.REMOVE).size(), 0);
            users = find(EntityType.USER, EntityActionType.UPDATE);
            Assert.assertEquals(users.size(), 2);
            Assert.assertEquals(users.stream().filter(e -> e.equals(getUserId("user-1"))).count(), 1);
            Assert.assertEquals(users.stream().filter(e -> e.equals(getUserId("user-3"))).count(), 1);
            Assert.assertEquals(getSentEntities().size(), 3);
            done(users, EntityType.USER, EntityActionType.UPDATE);
            Assert.assertEquals(getSentEntities().size(), 3);

            Assert.assertEquals(find(EntityType.USER, EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.REMOVE).size(), 0);
        });
    }

    @Test(dependsOnMethods = {"testNewUsers", "testModifiedUsers"})
    public void testRemovedUsers() {
        doTransaction(() -> {
            // initial: 3 new users sent: user-1, user-2, user-3
            // Check initial
            Assert.assertEquals(find(EntityType.USER, EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.REMOVE).size(), 0);
            List<Long> users = find(EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(users.size(), 0);
            Assert.assertEquals(getSentEntities().size(), 3);
            done(users, EntityType.USER, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 3);

            final Long user1Id = em.createQuery("select id from User where login = 'user-1'", Long.class).getSingleResult();

            // Remove user-1
            removeUser("user-1");
            Assert.assertEquals(find(EntityType.USER, EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.UPDATE).size(), 0);
            List<Long> removed = find(EntityType.USER, EntityActionType.REMOVE);
            Assert.assertEquals(removed.size(), 1);
            Assert.assertEquals(removed.stream().filter(e -> e.equals(user1Id)).count(), 1);
            Assert.assertEquals(getSentEntities().size(), 3);
            done(removed, EntityType.USER, EntityActionType.REMOVE);
            Assert.assertEquals(getSentEntities().size(), 2);

            Assert.assertEquals(find(EntityType.USER, EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(find(EntityType.USER, EntityActionType.REMOVE).size(), 0);

        });
    }

    private void markAsWeb(String s, boolean web) {
        em.createQuery("update User set web = :web where login = :login")
                .setParameter("web", web)
                .setParameter("login", s)
                .executeUpdate();
        em.flush();
        em.clear();
        User user = (User) em.createQuery("select e from User e where e.login = :login").setParameter("login", s).getSingleResult();
        Assert.assertEquals(Boolean.TRUE.equals(user.getWeb()), web, "Not updated user, actual: " + user.getWeb());
    }


    private void updateUser(String s) {
        final User user = em.createQuery("select u from User u where u.login = :login", User.class)
                .setParameter("login", s).getSingleResult();
        user.setPassword("changed" + UUID.randomUUID());
        em.merge(user);
    }

    private void removeUser(String s) {
        em.createQuery("delete from User where login = :login").setParameter("login", s).executeUpdate();
//        em.flush();
//        em.clear();
        List list = em.createQuery("select e from User e where e.login = :login").setParameter("login", s).getResultList();
        Assert.assertEquals(list.size(), 0, "Not deleted user");
    }

    private List<SentEntity> getSentEntities() {
        return em.createQuery("select e from SentEntity e", SentEntity.class).getResultList();
    }

}
