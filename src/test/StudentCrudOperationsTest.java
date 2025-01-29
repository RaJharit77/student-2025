package test;

import dao.StudentCrudOperations;
import entity.Sex;
import entity.Student;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static entity.Sex.FEMALE;
import static entity.Sex.MALE;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// IMPORTANT : Note that this test can _NOT_ be run without passing database ENV variables
class StudentCrudOperationsTest {
    // Always rename the class to test to 'subject'
    StudentCrudOperations subject = new StudentCrudOperations();

    @Test
    void read_all_students_ok() {
        // Test for data and potential mock
        Student expectedStudent = studentJohnDoe();

        // Subject and the function to test
        List<Student> actual = subject.getAll();

        // Assertions : verification to be made automatically
        assertTrue(actual.contains(expectedStudent));
    }

    @Test
    void read_student_by_id_ok() {
        var actual = subject.findById("student1_id");

        assertEquals(studentJohnDoe(), actual);
    }

    @Test
    void create_then_update_student_ok() {
        var studentToBeCreated = newStudent(randomUUID().toString(), "Dummy", FEMALE, LocalDate.of(2000, 1, 1));

        var actual = subject.saveAll(List.of(studentToBeCreated));
        //TODO: update created students with saveAll when saveAll handle update

        var allStudents = subject.getAll();
        assertEquals(List.of(studentToBeCreated), actual);
        assertTrue(allStudents.containsAll(actual));
    }

    private Student studentJohnDoe() {
        Student expectedStudent = new Student();
        expectedStudent.setId("student1_id");
        expectedStudent.setName("John Doe");
        expectedStudent.setSex(MALE);
        expectedStudent.setBirthDate(LocalDate.of(2000, 1, 1));
        return expectedStudent;
    }

    private Student newStudent(String id, String name, Sex sex, LocalDate birthDate) {
        Student newStudent = new Student();
        newStudent.setId(id);
        newStudent.setName(name);
        newStudent.setSex(sex);
        newStudent.setBirthDate(birthDate);
        return newStudent;
    }
}

