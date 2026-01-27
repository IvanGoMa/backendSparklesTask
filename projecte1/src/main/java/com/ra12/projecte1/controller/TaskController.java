package com.ra12.projecte1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ra12.projecte1.odt.taskRequestDTO;
import com.ra12.projecte1.services.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskService service;

    @PostMapping("/task")
    public ResponseEntity<String> createTask(@RequestBody taskRequestDTO task) {

        
        return service.createTask(task);
    }
    
}
