import dao.StudentCrudOperations;
import db.DataSource;
import entity.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class StudentManagementApplication {
    static Logger logger = Logger.getLogger(StudentManagementApplication.class.getName());

    public static void main(String[] args) throws SQLException {
        // Check database connection works properly !
        DataSource dataSource = new DataSource();
        Connection connection = dataSource.getConnection();
        logger.info(connection.toString());
        connection.close();

        // Check student getAll() works
        StudentCrudOperations studentCrudOperations = new StudentCrudOperations();
        List<Student> students = studentCrudOperations.getAll();
        logger.info(students.toString());
    }
}
