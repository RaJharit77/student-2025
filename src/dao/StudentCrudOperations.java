package dao;

import db.DataSource;
import entity.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentCrudOperations implements CrudOperations<Student> {
    private final DataSource dataSource = new DataSource();

    @Override
    public List<Student> getAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("select a.id, a.name, a.birth_date from student a")) {
                List<Student> students = new ArrayList<>();
                while (resultSet.next()) {
                    Student student = new Student();
                    student.setId(resultSet.getString("id"));
                    student.setName(resultSet.getString("name"));
                    student.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
                    students.add(student);
                }
                return students;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Student findById(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Student> saveAll(List<Student> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
