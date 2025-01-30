package dao;

import dao.mapper.SexMapper;
import db.DataSource;
import entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentCrudOperations implements CrudOperations<Student> {
    private final DataSource dataSource;
    private final SexMapper sexMapper;

    // Surcharged constructor when we want to pass existing args (obj)
    public StudentCrudOperations(DataSource dataSource, SexMapper sexMapper) {
        this.dataSource = dataSource;
        this.sexMapper = sexMapper;
    }

    // Default constructor when any args passed
    public StudentCrudOperations() {
        this.dataSource = new DataSource();
        this.sexMapper = new SexMapper();
    }

    @Override
    public List<Student> getAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = (PreparedStatement) connection.createStatement()) {
            try (ResultSet resultSet = pstmt.executeQuery("select s.id, s.name, s.birth_date, s.sex from student s where id")) {
                List<Student> students = new ArrayList<>();
                while (resultSet.next()) {
                    Student student = new Student();
                    student.setId(resultSet.getString("id"));
                    student.setName(resultSet.getString("name"));
                    student.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
                    student.setSex(sexMapper.mapFromResultSet(resultSet.getString("sex")));
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
