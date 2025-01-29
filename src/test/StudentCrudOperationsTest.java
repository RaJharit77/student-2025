package test;

import dao.StudentCrudOperations;
import entity.Student;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static entity.Sex.MALE;
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
        throw new UnsupportedOperationException("TODO: not supported yet.");
    }

    private Student studentJohnDoe() {
        Student expectedStudent = new Student();
        expectedStudent.setId("student1_id");
        expectedStudent.setName("John Doe");
        expectedStudent.setSex(MALE);
        expectedStudent.setBirthDate(LocalDate.of(2000, 1, 1));
        return expectedStudent;
    }
}

