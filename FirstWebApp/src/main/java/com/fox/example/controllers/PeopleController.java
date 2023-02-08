package com.fox.example.controllers;

import com.fox.example.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDao personDao;

    @Autowired
    public PeopleController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping()
    public String getPeople(Model model){
        model.addAttribute("people", personDao.getAllPersons());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String getPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDao.getPersonById(id));
        return "people/person";
    }
}
