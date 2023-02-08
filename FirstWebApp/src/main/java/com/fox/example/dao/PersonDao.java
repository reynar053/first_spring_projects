package com.fox.example.dao;

import com.fox.example.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
    private static int PEOPLE_COUNT;
    final private List<Person> db;

    {
        db = new ArrayList<>();
        db.add(new Person(++PEOPLE_COUNT, "Tom"));
        db.add(new Person(++PEOPLE_COUNT, "Jerry"));
        db.add(new Person(++PEOPLE_COUNT, "Harry"));
    }

    public List<Person> getAllPersons(){
        return db;
    }

    public Person getPersonById(int id){
        return db.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }
}
