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

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            synchronized (DbConnector.class) {
                Path currentPath = Paths.get("");
                DriverManager.registerDriver(new JDBC());
                String connString = "jdbc:sqlite:" + currentPath.toAbsolutePath().toString() + File.separator + SCHEMA_NAME +".db";
                connection = DriverManager.getConnection(connString);
            }
        }

        return connection;
    }
}
