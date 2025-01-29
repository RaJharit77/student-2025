package test;

import dao.StudentCrudOperations;
import dao.mapper.SexMapper;
import db.DataSource;
import entity.Sex;
import entity.Student;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static entity.Sex.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// IMPORTANT : Note that this test can be run without passing database ENV variables
class StudentCrudOperationsUnitTest {
    // Mock objects that could be declared inside methods
    DataSource dataSourceMock = mock(DataSource.class);
    SexMapper sexMapperMock = mock(SexMapper.class);
    // Class to test is always renamed into 'subject'
    StudentCrudOperations subject = new StudentCrudOperations(dataSourceMock, sexMapperMock);

    @Test
    void read_all_students_ok() throws SQLException {
        // Additional object mocked
        Connection databaseConnectionMock = mock(Connection.class);
        Statement statementMock = mock(Statement.class);
        ResultSet resultSetMock = mock(ResultSet.class);

        // Only expectedStudent here is not mocked because we want to test if it is correctly created
        Student expectedStudent = new Student();
        String studentId = "amazing_other_id";
        String studentName = "amazing_other_name";
        LocalDate studentBirthDate = LocalDate.of(1999, 6, 1);
        Sex studentSex = MALE;
        expectedStudent.setId(studentId);
        expectedStudent.setName(studentName);
        expectedStudent.setBirthDate(studentBirthDate);
        expectedStudent.setSex(studentSex);

        // Mocking objects behavior that means we decide here what value will return or not the method when called
        when(resultSetMock.next())
                .thenReturn(true) // First invocation
                .thenReturn(false); // Second invocation so then program get out of the while loop
        when(resultSetMock.getString("id")).thenReturn(studentId);
        when(resultSetMock.getString("name")).thenReturn(studentName);
        when(resultSetMock.getDate("birth_date")).thenReturn(Date.valueOf(studentBirthDate));
        when(resultSetMock.getString("sex")).thenReturn("MALE");
        when(statementMock.executeQuery(eq("select s.id, s.name, s.birth_date, s.sex from student s")))
                .thenReturn(resultSetMock);
        when(databaseConnectionMock.createStatement()).thenReturn(statementMock);
        when(dataSourceMock.getConnection()).thenReturn(databaseConnectionMock);
        when(sexMapperMock.mapFromResultSet(any())).thenReturn(studentSex); // args any() means any value (MALE or FEMALE) obtained will ALWAYS return MALE here

        // Always the method to be tested
        List<Student> actual = subject.getAll();

        // Always the verification
        assertEquals(List.of(expectedStudent), actual);
    }
}
