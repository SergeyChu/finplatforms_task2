package ru.schu.test;

import org.sqlite.JDBC;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private static final String SCHEMA_NAME = "task2";
    private static volatile Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DbConnector.class) {
                Path currentPath = Paths.get("");
                try {
                    DriverManager.registerDriver(new JDBC());
                    String connString = "jdbc:sqlite:" + currentPath.toAbsolutePath().toString() + File.separator + SCHEMA_NAME + ".db";
                    connection = DriverManager.getConnection(connString);
                } catch (SQLException e) {
                    throw new IllegalStateException("Unable to establish connection: " + e.getMessage(), e);
                }
            }
        }

        return connection;
    }
}
