import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.schu.test.DbConnector;
import ru.schu.test.Student;
import ru.schu.test.StudentsDao;

import java.sql.SQLException;
import java.util.List;


class StudentsDaoTests {
    StudentsDao dao = new StudentsDao(DbConnector.getConnection());

    @Test
    void insertDeleteShowAllTest() throws SQLException {
        dao.truncate();
        List<Student> students = dao.getAll();
        Assertions.assertEquals(0, students.size());

        Student st1Expected = new Student().setName("Василий").setLastname("Васильев").setPatronymic("Васильевич")
                .setDateOfBirth("2000-03-21").setGroup("07-ГР-24");
        dao.add(st1Expected);
        students = dao.getAll();
        Assertions.assertEquals(1, students.size());
        assertStudentsEqual(st1Expected, students.get(0));

        Student st2Expected = new Student().setName("Фёдор").setLastname("Фёдоров").setPatronymic("Фёдорович")
                .setDateOfBirth("1990-11-06").setGroup("01-ГР-13");
        dao.add(st2Expected);
        students = dao.getAll();
        Assertions.assertEquals(2, students.size());
        assertStudentsEqual(st1Expected, students.get(0));
        assertStudentsEqual(st2Expected, students.get(1));

        Student st3Expected = new Student().setName("Сергей").setLastname("Чубуров").setPatronymic("Александрович")
                .setDateOfBirth("1989-06-06").setGroup("06-ИС-5");
        dao.add(st3Expected);
        students = dao.getAll();
        Assertions.assertEquals(3, students.size());
        assertStudentsEqual(st1Expected, students.get(0));
        assertStudentsEqual(st2Expected, students.get(1));
        assertStudentsEqual(st3Expected, students.get(2));

        dao.deleteById(students.get(1).getId());
        students = dao.getAll();
        Assertions.assertEquals(2, students.size());
        assertStudentsEqual(st1Expected, students.get(0));
        assertStudentsEqual(st3Expected, students.get(1));
    }

    private void assertStudentsEqual(Student exp, Student act) {
        Assertions.assertEquals(exp.getName(), act.getName());
        Assertions.assertEquals(exp.getLastname(), act.getLastname());
        Assertions.assertEquals(exp.getPatronymic(), act.getPatronymic());
        Assertions.assertEquals(exp.getDateOfBirth(), act.getDateOfBirth());
        Assertions.assertEquals(exp.getGroup(), act.getGroup());
    }
}
