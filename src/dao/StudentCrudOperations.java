package dao;

import dao.mapper.SexMapper;
import db.DataSource;
import entity.Student;

import java.sql.*;
import java.time.LocalDate;
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
    public List<Student> getAll(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("page must be greater than 0 but actual is " + page);
        }
        String sql = "select s.id, s.name, s.birth_date, s.sex from student s  order by s.sex desc limit ? offset ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, size);
            statement.setInt(2, size * (page - 1));
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Student> students = new ArrayList<>();
                while (resultSet.next() == true) {
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
    public Student findById(String id) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("select s.id, s.name, s.birth_date, s.sex from student s where id = '" + id + "'")) {
                Student student = new Student();
                while (resultSet.next()) {
                    student.setId(resultSet.getString("id"));
                    student.setName(resultSet.getString("name"));
                    student.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
                    student.setSex(sexMapper.mapFromResultSet(resultSet.getString("sex")));
                }
                return student;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: only create is handle now, update must be implemented
    @Override
    public List<Student> saveAll(List<Student> entities) {
        List<Student> newStudents = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            entities.forEach(entityToSave -> {
                try {
                    statement.executeUpdate(
                            "insert into student values ('" + entityToSave.getId() + "',"
                                    + " '" + entityToSave.getName() + "',"
                                    + "'" + entityToSave.getSex().toString() + "',"
                                    + "'" + entityToSave.getBirthDate().toString() + "')");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                newStudents.add(findById(entityToSave.getId()));
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newStudents;
    }
    public List<Student> filterStudents(String name, LocalDate startDate, LocalDate endDate) {
        String sql = "select s.id, s.name, s.birth_date, s.sex from student s where 1=1";
        if (name != null) {
            sql += " and s.name like '%" + name + "%'";
        }
        if (startDate != null && endDate != null) {
            sql += " and s.birth_date between '" + startDate.toString() + "' and '" + endDate.toString() + "'";
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
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
    public List<Student> orderStudents(String orderBy) {
        String sql = "select s.id, s.name, s.birth_date, s.sex from student s";
        if (orderBy != null) {
            sql += " order by " + orderBy;
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
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
}
