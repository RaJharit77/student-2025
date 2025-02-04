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
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

// IMPORTANT : Note that this test can _NOT_ be run without passing database ENV variables
class StudentCrudOperationsTest {
    // Always rename the class to test to 'subject'
    StudentCrudOperations subject = new StudentCrudOperations();

    @Test
    void read_all_students_ok() {
        // Test for data and potential mock
        Student expectedStudent = studentJohnDoe();

        // Subject and the function to test
        List<Student> actualFemale = subject.getAll(1, 3);
        List<Student> actualMale = subject.getAll(2, 3);

        assertEquals(3, actualFemale.size());
        assertEquals(3, actualMale.size());
        assertTrue(actualFemale.stream()
                .allMatch(s -> s.getSex().equals(FEMALE)));
        assertTrue(actualMale.stream().allMatch(s -> s.getSex().equals(MALE)));
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
        assertEquals(List.of(studentToBeCreated), actual);

        studentToBeCreated.setName("Updated Dummy");
        studentToBeCreated.setBirthDate(LocalDate.of(2001, 1, 1));
        var updatedStudents = subject.saveAll(List.of(studentToBeCreated));

        assertEquals(List.of(studentToBeCreated), updatedStudents);
        var allStudents = subject.getAll(1, 3);
        assertTrue(allStudents.containsAll(updatedStudents));
    }

    // TODO : make the changes inside the CrudOperations and its implementation to handle this
    // Once test passed, set UnitTest corresponding
    @Test
    void read_students_filter_by_name_or_birthday_between_intervals_or_both() {
        var student1 = newStudent("student1_id", "John Doe", MALE, LocalDate.of(2000, 1, 1));
        var student2 = newStudent("student2_id", "Jane Doe", FEMALE, LocalDate.of(2001, 5, 15));
        subject.saveAll(List.of(student1, student2));

        var filteredByName = subject.filterStudents("John", null, null);
        assertEquals(1, filteredByName.size());
        assertEquals("John Doe", filteredByName.get(0).getName());

        var filteredByBirthday = subject.filterStudents(null, LocalDate.of(2000, 1, 1), LocalDate.of(2001, 12, 31));
        assertEquals(2, filteredByBirthday.size());

        var filteredByBoth = subject.filterStudents("Jane", LocalDate.of(2000, 1, 1), LocalDate.of(2001, 12, 31));
        assertEquals(1, filteredByBoth.size());
        assertEquals("Jane Doe", filteredByBoth.get(0).getName());
        assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not implemented yet");
        });
    }

    // TODO : make the changes inside the CrudOperations and its implementation to handle this
    // Once test passed, set UnitTest corresponding
    @Test
    void read_students_order_by_name_or_birthday_or_both() {
        var student1 = newStudent("student1_id", "John Doe", MALE, LocalDate.of(2000, 1, 1));
        var student2 = newStudent("student2_id", "Jane Doe", FEMALE, LocalDate.of(2001, 5, 15));
        subject.saveAll(List.of(student1, student2));

        var orderedByName = subject.orderStudents("name");
        assertEquals("Jane Doe", orderedByName.get(0).getName());
        assertEquals("John Doe", orderedByName.get(1).getName());

        var orderedByBirthday = subject.orderStudents("birth_date");
        assertEquals("John Doe", orderedByBirthday.get(0).getName());
        assertEquals("Jane Doe", orderedByBirthday.get(1).getName());

        var orderedByBoth = subject.orderStudents("name, birth_date");
        assertEquals("Jane Doe", orderedByBoth.get(0).getName());
        assertEquals("John Doe", orderedByBoth.get(1).getName());
        assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not implemented yet");
        });
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

