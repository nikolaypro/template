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

/**
 * Created by Nikolay on 21.03.2017.
 */
@Test
public class ChangesFacadeTest extends AbstractDbTest {

    public void testNewUsers() {
        doTransaction(() -> {
            ChangesFacade<User> facade = new ChangesFacade<>(em, EntityType.USER);
            List<User> users = facade.find(EntityActionType.INSERT);
            Assert.assertEquals(users.size(), 0);
            Assert.assertEquals(getSentEntities().size(), 0);

            facade.done(users, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 0);

            // Not web user
            User user = TestModelFactory.createUser("user-1");
            em.persist(user);

            users = facade.find(EntityActionType.INSERT);
            Assert.assertEquals(users.size(), 0);
            Assert.assertEquals(getSentEntities().size(), 0);

            facade.done(users, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 0);

            // New web user
            user.setWeb(true);
            em.merge(user);

            users = facade.find(EntityActionType.INSERT);
            Assert.assertEquals(users.size(), 1);
            Assert.assertEquals(getSentEntities().size(), 0);

            facade.done(users, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 1);

            // Repeat call
            users = facade.find(EntityActionType.INSERT);
            Assert.assertEquals(users.size(), 1);
            Assert.assertEquals(getSentEntities().size(), 0);

            facade.done(users, EntityActionType.INSERT);
            Assert.assertEquals(getSentEntities().size(), 1);
        });
    }

    private List<SentEntity> getSentEntities() {
        return em.createQuery("select e from SentEntity e", SentEntity.class).getResultList();
    }

}
