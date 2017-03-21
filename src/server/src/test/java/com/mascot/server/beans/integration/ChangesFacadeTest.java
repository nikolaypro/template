package com.mascot.server.beans.integration;

import com.mascot.TestModelFactory;
import com.mascot.server.model.EntityActionType;
import com.mascot.server.model.EntityType;
import com.mascot.server.model.SentEntity;
import com.mascot.server.model.User;
import com.mascot.server.test.AbstractDbTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

/**
 * Created by Nikolay on 21.03.2017.
 */
@Test
public class ChangesFacadeTest extends AbstractDbTest {
    @Test
    public void testNewUsers() {
        doTransaction(() -> {
            ChangesFacade<User> facade = new ChangesFacade<>(em, EntityType.USER);
            List<User> users = facade.find(EntityActionType.INSERT);
            Assert.assertEquals(users.size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.REMOVE).size(), 0);
            Assert.assertEquals(getSentEntities().size(), 0);

            facade.done(users, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 0);

            // Not web user
            em.persist(TestModelFactory.createUser("user-1"));

            users = facade.find(EntityActionType.INSERT);
            Assert.assertEquals(users.size(), 0);
            Assert.assertEquals(getSentEntities().size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.REMOVE).size(), 0);

            facade.done(users, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 0);

            // New web user
            markAsWeb("user-1", true);

            users = facade.find(EntityActionType.INSERT);
            Assert.assertEquals(users.size(), 1);
            Assert.assertEquals(users.get(0).getFullName(), "user-1");
            Assert.assertEquals(getSentEntities().size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.REMOVE).size(), 0);

            facade.done(users, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 1);

            // Repeat call
            users = facade.find(EntityActionType.INSERT);
            Assert.assertEquals(users.size(), 0);
            Assert.assertEquals(getSentEntities().size(), 1);
            Assert.assertEquals(facade.find(EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.REMOVE).size(), 0);

            facade.done(users, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 1);


            // Create two users
            em.persist(TestModelFactory.createUser("user-2"));
            em.persist(TestModelFactory.createUser("user-3"));
            markAsWeb("user-2", true);
            markAsWeb("user-3", true);

            users = facade.find(EntityActionType.INSERT);
            Assert.assertEquals(users.size(), 2);
            Assert.assertEquals(users.stream().filter(e -> e.getFullName().equals("user-2")).count(), 1);
            Assert.assertEquals(users.stream().filter(e -> e.getFullName().equals("user-3")).count(), 1);
            Assert.assertEquals(users.stream().filter(e -> e.getFullName().equals("user-1")).count(), 0);
            Assert.assertEquals(getSentEntities().size(), 1);
            Assert.assertEquals(facade.find(EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.REMOVE).size(), 0);

            facade.done(users, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 3);

        });
    }


    @Test(dependsOnMethods = {"testNewUsers"})
    public void testModifiedUsers() {
        doTransaction(() -> {
            // initial: 3 new users sent: user-1, user-2, user-3
            ChangesFacade<User> facade = new ChangesFacade<>(em, EntityType.USER);

            // Check initial
            List<User> users = facade.find(EntityActionType.INSERT);
            Assert.assertEquals(users.size(), 0);
            Assert.assertEquals(getSentEntities().size(), 3);
            facade.done(users, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 3);
            Assert.assertEquals(facade.find(EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.REMOVE).size(), 0);

            // Update user-1
            updateUser("user-1");
            Assert.assertEquals(facade.find(EntityActionType.INSERT).size(), 0);
            users = facade.find(EntityActionType.UPDATE);
            Assert.assertEquals(users.size(), 1);
            Assert.assertEquals(users.stream().filter(e -> e.getFullName().equals("user-1")).count(), 1);
            Assert.assertEquals(getSentEntities().size(), 3);
            facade.done(users, EntityActionType.UPDATE);
            Assert.assertEquals(getSentEntities().size(), 3);
            Assert.assertEquals(facade.find(EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.REMOVE).size(), 0);

            Assert.assertEquals(facade.find(EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.REMOVE).size(), 0);


            // Update user-1, user-3
            updateUser("user-1");
            updateUser("user-3");

            Assert.assertEquals(facade.find(EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.REMOVE).size(), 0);
            users = facade.find(EntityActionType.UPDATE);
            Assert.assertEquals(users.size(), 2);
            Assert.assertEquals(users.stream().filter(e -> e.getFullName().equals("user-1")).count(), 1);
            Assert.assertEquals(users.stream().filter(e -> e.getFullName().equals("user-3")).count(), 1);
            Assert.assertEquals(getSentEntities().size(), 3);
            facade.done(users, EntityActionType.UPDATE);
            Assert.assertEquals(getSentEntities().size(), 3);

            Assert.assertEquals(facade.find(EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.REMOVE).size(), 0);
        });
    }

    @Test(dependsOnMethods = {"testModifiedUsers"})
    public void testRemovedUsers() {
        doTransaction(() -> {
            // initial: 3 new users sent: user-1, user-2, user-3
            ChangesFacade<User> facade = new ChangesFacade<>(em, EntityType.USER);
            ChangesFacade<Long> removeFacade = new ChangesFacade<>(em, EntityType.USER);

            // Check initial
            Assert.assertEquals(facade.find(EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.REMOVE).size(), 0);
            List<User> users = facade.find(EntityActionType.INSERT);
            Assert.assertEquals(users.size(), 0);
            Assert.assertEquals(getSentEntities().size(), 3);
            facade.done(users, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 3);

            final Long user1Id = em.createQuery("select id from User where login = 'user-1'", Long.class).getSingleResult();

            // Remove user-1
            removeUser("user-1");
            Assert.assertEquals(facade.find(EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.UPDATE).size(), 0);
            List<Long> removed = removeFacade.find(EntityActionType.REMOVE);
            Assert.assertEquals(removed.size(), 1);
            Assert.assertEquals(removed.stream().filter(e -> e.equals(user1Id)).count(), 1);
            Assert.assertEquals(getSentEntities().size(), 3);
            removeFacade.done(removed, EntityActionType.REMOVE);
            Assert.assertEquals(getSentEntities().size(), 2);

            Assert.assertEquals(facade.find(EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(removeFacade.find(EntityActionType.REMOVE).size(), 0);

/*
            // Update user-1, user-3
            updateUser("user-1");
            updateUser("user-3");

            Assert.assertEquals(facade.find(EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.REMOVE).size(), 0);
            users = facade.find(EntityActionType.UPDATE);
            Assert.assertEquals(users.size(), 2);
            Assert.assertEquals(users.stream().filter(e -> e.getFullName().equals("user-1")).count(), 1);
            Assert.assertEquals(users.stream().filter(e -> e.getFullName().equals("user-3")).count(), 1);
            Assert.assertEquals(getSentEntities().size(), 3);
            facade.done(users, EntityActionType.UPDATE);
            Assert.assertEquals(getSentEntities().size(), 3);

            Assert.assertEquals(facade.find(EntityActionType.INSERT).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.UPDATE).size(), 0);
            Assert.assertEquals(facade.find(EntityActionType.REMOVE).size(), 0);
*/
        });
    }

    private void markAsWeb(String s, boolean web) {
        em.createQuery("update User set web = :web where login = :login")
                .setParameter("web", web)
                .setParameter("login", s)
                .executeUpdate();
    }

    private void updateUser(String s) {
        final User user = em.createQuery("select u from User u where u.login = :login", User.class)
                .setParameter("login", s).getSingleResult();
        user.setPassword("changed" + UUID.randomUUID());
        em.merge(user);
    }

    private void removeUser(String s) {
/*
        final User user = em.createQuery("select u from User u where u.login = :login", User.class)
                .setParameter("login", s).getSingleResult();
        user.setPassword("changed" + UUID.randomUUID());
        em.merge(user);
*/
        em.createQuery("delete from User where login = :login").setParameter("login", s).executeUpdate();
    }

    private List<SentEntity> getSentEntities() {
        return em.createQuery("select e from SentEntity e", SentEntity.class).getResultList();
    }

}
