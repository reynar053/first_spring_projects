package com.fox.example.controllers;

import com.fox.example.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/batch")
public class BatchController {
    private final PersonDao personDao;

    @Autowired
    public BatchController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping("")
    public String batchingPage() {
        return "batch/index";
    }

    @GetMapping("/without")
    public String withoutBatching() {
        personDao.multipleUpdate();
        return "redirect:/people";
    }

    @GetMapping("/with")
    public String withBatching() {
        personDao.batchUpdate();
        return "redirect:/people";
    }

    @GetMapping("/drop")
    public String clearTable() {
        personDao.clearTable();
        return "redirect:/people";
    }
}
