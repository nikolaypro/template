package com.mascot.server.test;

import com.mascot.common.MascotAppContext;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.resource.FileSystemResourceAccessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManager;
import java.sql.*;

/**
 * Created by Nikolay on 11.01.2017.
 */
public class TestDatabase {
    public void recreateDatabase() throws Exception {
        createDB();
        createTables();
        System.out.println("Recreate database");
    }

    private void createTables() {
        final EntityManager em = MascotAppContext.createEm();
/*
        final SpringLiquibase bean = MascotAppContext.getBean(SpringLiquibase.class);
        Liquibase
        Liquibase l = new Liquibase()
*/

/*
        java.sql.Connection c = YOUR_CONNECTION;
        Liquibase liquibase = null;
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c))
            liquibase = new Liquibase(YOUR_CHANGELOG, new FileSystemResourceAccessor(), database);
            liquibase.update("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        } finally {
            if (c != null) {
                try {
                    c.rollback();
                    c.close();
                } catch (SQLException e) {
                    //nothing to do
                }
            }
        }
*/

    }

    public void createDB() throws SQLException {
        createDb(MascotAppContext.getEnvironment());
    }

    public void createDb(Environment env) throws SQLException {
        final String serverURL = env.getProperty(TestDataBaseConfig.PROP_DATABASE_URL);
        final String user = env.getProperty(TestDataBaseConfig.PROP_DATABASE_USERNAME);
        final String password = env.getProperty(TestDataBaseConfig.PROP_DATABASE_PASSWORD);
        final String dbName = env.getProperty(TestDataBaseConfig.PROP_DATABASE_NAME);

        final Connection connection = DriverManager.getConnection(serverURL, user, password);
        createDb(dbName, connection);
    }

    public void createDb(String dbName, Connection connection) throws SQLException {
        try {
            _dropDBIfExists(connection, dbName);

            final PreparedStatement statement = connection.prepareStatement("create database " + dbName + "");
            try {
                statement.execute();
            } finally {
                statement.close();
            }
        } finally {
//            connection.commit();
            connection.close();
        }
    }

    private void _dropDBIfExists(Connection connection, String databaseName) throws SQLException {
        if (!isDBExists(connection, databaseName)) {
            return;
        }

//        final PreparedStatement statement = connection.prepareStatement("drop database \"" + databaseName + "\"");
        final PreparedStatement statement = connection.prepareStatement("DROP DATABASE " + databaseName + "");
        try {
            statement.execute();
        } finally {
            statement.close();
        }
    }

    private boolean isDBExists(Connection connection, String databaseName) throws SQLException {
        final ResultSet catalogs = connection.getMetaData().getCatalogs();
        try {
            while (catalogs.next()) {
                final String eachName = catalogs.getString(1);
                if (databaseName.equals(eachName)) {
                    return true;
                }
            }
            return false;
        } finally {
            catalogs.close();
        }
    }

}
