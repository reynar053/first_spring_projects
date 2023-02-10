package com.fox.example.dao;

import com.fox.example.models.Person;
import org.springframework.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPersons() {
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Person getPersonById(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new int[]{Type.INT}, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO PERSON values(1, ?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET p_name=?, age=?, email=? WHERE id=?", person.getName(), person.getAge(), person.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public void batchUpdate() {
        List<Person> people = createPeople(1000);

        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO PERSON values(?, ?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, people.get(i).getId());
                ps.setString(2, people.get(i).getName());
                ps.setInt(3, people.get(i).getAge());
                ps.setString(4, people.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });
        long after = System.currentTimeMillis();

        System.out.println("Batching update working time = " + (after - before) + "(ms)");
    }

    public void multipleUpdate() {
        List<Person> people = createPeople(1000);

        long before = System.currentTimeMillis();
        people.forEach(this::save);
        long after = System.currentTimeMillis();

        System.out.println("Multiple update working time = " + (after - before) + "(ms)");
    }

    private List<Person> createPeople(int amountOfPeopleToCreate) {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < amountOfPeopleToCreate; i++) {
            people.add(new Person(i, "Person" + i, 10+i, "email" + i + "@mail.com"));
        }
        return people;
    }

    public void clearTable() {
        jdbcTemplate.update("TRUNCATE TABLE Person");
    }
}
