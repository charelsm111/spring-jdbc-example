package com.charel.springdatajdbcexample2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class StudentJDBCTemplate implements StudentDAO {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Override
    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
        this.jdbcTemplateObject = new JdbcTemplate(ds);
    }

    @Override
    public void create(String name, Integer age) {
        String SQL = "INSERT INTO Student (name, age) VALUES (?, ?)";
        jdbcTemplateObject.update(SQL, name, age);
        System.out.println("Created record Name = " + name + " Age = " + age);
        return;
    }

    @Override
    public Student getStudent(Integer id) {
        String SQL = "SELECT * FROM Student where id = ?";
        Student student = (Student) jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new StudentMapper());

        return student;
    }

    @Override
    public List<Student> listStudents() {
        String SQL = "SELECT * FROM Student";
        List<Student> students = jdbcTemplateObject.query(SQL, new StudentMapper());

        return students;
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM Student WHERE id = ?";
        jdbcTemplateObject.update(SQL, id);
        System.out.println("Delete record with id = " + id);
        return;
    }

    @Override
    public void update(Integer id, Integer age) {
        String SQL = "UPDATE Student SET age = ? WHERE id = ?";
        jdbcTemplateObject.update(SQL, age, id);
        System.out.println("Updated record with id = " + id);
        return;
    }
}
