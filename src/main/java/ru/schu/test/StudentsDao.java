package ru.schu.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ru.schu.test.enums.StudentColumns.*;

public class StudentsDao {
    private final Connection connection;
    private static final String STUDENTS_TABLE = "students";
    private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO " + STUDENTS_TABLE
            + "(" + NAME + ", " + LASTNAME + ", " + PATRONYMIC + ", " + DATE_OF_BIRTH + ", `" + GROUP + "`)\n"
            + "VALUES( \"%1$s\", \"%2$s\", \"%3$s\", \"%4$s\", \"%5$s\");";
    private static final String SELECT_ALL_QUERY = "SELECT * from " + STUDENTS_TABLE;
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM " + STUDENTS_TABLE + " WHERE " + ID + "=%d";

    public StudentsDao(Connection connection) {
        this.connection = connection;
    }

    public void createTablesAndPopulateTestData() throws SQLException {

        if(this.connection == null) {
            throw new IllegalStateException("Connection is not established");
        }

        String dropQuery = "DROP TABLE IF EXISTS " + STUDENTS_TABLE;
        try(Statement dropTable = this.connection.createStatement()) {
            dropTable.executeUpdate(dropQuery);
        }

        String createQuery = "CREATE TABLE " + STUDENTS_TABLE + " (\n" +
                ID + " INTEGER NOT NULL UNIQUE,\n" +
                NAME + " TEXT NOT NULL,\n" +
                LASTNAME + " TEXT NOT NULL,\n" +
                PATRONYMIC + " TEXT NOT NULL,\n" +
                DATE_OF_BIRTH + " TEXT NOT NULL,\n" +
                "`" + GROUP + "` TEXT NOT NULL,\n" +
                "PRIMARY KEY(" + ID + " AUTOINCREMENT)\n" +
                ");";
        try(Statement createTable = this.connection.createStatement()) {
            createTable.executeUpdate(createQuery);
        }

        try(Statement insertStat = this.connection.createStatement()) {
            List<Student> studentsToInsert = new ArrayList<>();
            studentsToInsert.add(new Student().setName("Иван").setLastname("Иванов")
                    .setPatronymic("Иванович").setDateOfBirth("1995-04-07").setGroup("12-МС-14"));
            studentsToInsert.add(new Student().setName("Сидор").setLastname("Сидоров")
                    .setPatronymic("Сидорович").setDateOfBirth("1995-08-02").setGroup("12-МС-14"));
            studentsToInsert.add(new Student().setName("Пётр").setLastname("Петров")
                    .setPatronymic("Петрович").setDateOfBirth("1994-02-24").setGroup("11-МС-14"));
            studentsToInsert.forEach(s -> insertStudent(s, insertStat));
        }
    }

    private void insertStudent(Student s, Statement st) {
        String query = String.format(INSERT_QUERY_TEMPLATE, s.getName(), s.getLastname(), s.getPatronymic(),
                s.getDateOfBirth(), s.getGroup());

        try {
            st.executeUpdate(query);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public List<Student> getAll() throws SQLException {
        List<Student> result = new ArrayList<>();

        try(Statement stat = connection.createStatement()) {
            ResultSet fetchedData = stat.executeQuery(SELECT_ALL_QUERY);

            while (fetchedData.next()) {
                Student tempStudent = new Student()
                        .setId(fetchedData.getLong(String.valueOf(ID)))
                        .setName(fetchedData.getString(String.valueOf(NAME)))
                        .setLastname(fetchedData.getString(String.valueOf(LASTNAME)))
                        .setPatronymic(fetchedData.getString(String.valueOf(PATRONYMIC)))
                        .setDateOfBirth(fetchedData.getString(String.valueOf(DATE_OF_BIRTH)))
                        .setGroup(fetchedData.getString(String.valueOf(GROUP)));
                result.add(tempStudent);
            }
        }

        return result;
    }

    public void truncate() throws SQLException {
        try(Statement stat = connection.createStatement()) {
            stat.executeUpdate("DELETE FROM " + STUDENTS_TABLE);
        }
    }

    public void deleteById(Long id) throws SQLException {
        try(Statement stat = connection.createStatement()) {
            stat.executeUpdate(String.format(DELETE_BY_ID_QUERY, id));
        }
    }

    public void add(Student student) throws SQLException {
        try(Statement stat = connection.createStatement()) {
            this.insertStudent(student, stat);
        }
    }
}
