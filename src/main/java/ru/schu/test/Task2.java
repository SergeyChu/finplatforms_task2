package ru.schu.test;

import ru.schu.test.enums.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Task2 {
    private static final Map<String, Command> commands = new LinkedHashMap<>();

    public static void main(String... args) throws SQLException, IOException {
        commands.put("l", Command.SHOW_ALL);
        commands.put("list", Command.SHOW_ALL);
        commands.put("listall", Command.SHOW_ALL);
        commands.put("a", Command.ADD_NEW);
        commands.put("add", Command.ADD_NEW);
        commands.put("addnew", Command.ADD_NEW);
        commands.put("d", Command.DELETE_BY_ID);
        commands.put("del", Command.DELETE_BY_ID);
        commands.put("delbyid", Command.DELETE_BY_ID);
        commands.put("q", Command.EXIT);
        commands.put("quit", Command.EXIT);
        commands.put("exit", Command.EXIT);


        StudentsDao studentsDao = new StudentsDao(DbConnector.getConnection());
        studentsDao.createTablesAndPopulateTestData();

        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String input;
        boolean terminate = false;
        do {
            input = r.readLine();

            if (!commands.containsKey(input)) {
                printHelp();
            } else {
                switch(commands.get(input)) {
                    case EXIT:
                        terminate = true;
                        break;
                    case SHOW_ALL:
                        printStudents(studentsDao.getAll());
                        break;
                    case ADD_NEW:
                        acceptInputsAndAddStudent(r, studentsDao);
                        break;
                    case DELETE_BY_ID:
                        deleteStudent(r, studentsDao);
                        break;
                }
            }
        } while (!terminate);

        r.close();
    }

    private static void printHelp() {
        Map<Command, String> groupedCommands =
                commands.entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.joining(", "))));

        System.out.println("Incorrect command, please refer to available commands below:");
        groupedCommands.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    private static void printStudents(List<Student> students) {
        System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s | %-25s%n", "id_student", "name", "lastname", "patronymic",
                "date_of_birth", "group");
        students.forEach(s -> System.out.printf("%-25s | %-25s | %-25s | %-25s | %-25s | %-25s%n", s.getId(), s.getName(),
                s.getLastname(), s.getPatronymic(), s.getDateOfBirth(), s.getGroup()));
    }

    private static void acceptInputsAndAddStudent(BufferedReader r, StudentsDao studentsDao) throws IOException, SQLException {
        Student tempStudent = new Student()
                .setName(getNonEmptyInput(r, "Please enter name"))
                .setPatronymic(getNonEmptyInput(r, "Please enter surname"))
                .setLastname(getNonEmptyInput(r, "Please enter patronymic"))
                .setDateOfBirth(getNonEmptyInput(r, "Please enter date of birth, preferred format yyyy-MM-dd"))
                .setGroup(getNonEmptyInput(r, "Please enter group"));
        studentsDao.add(tempStudent);
    }

    private static String getNonEmptyInput(BufferedReader r, String message) throws IOException {
        String result = "";
        do{
            System.out.println(message);
            result = r.readLine();
        } while ("".equals(result));
        return result;
    }

    private static void deleteStudent(BufferedReader r, StudentsDao studentsDao) throws SQLException, IOException {
        long result = -1L;
        do{
            System.out.println("Please enter Id to remove");
            try {
                result = Long.parseLong(r.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Id should be natural number");
            }
        } while (result < 0);

        studentsDao.deleteById(result);
    }
}
