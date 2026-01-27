package com.ra12.projecte1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ra12.projecte1.odt.taskRequestDTO;
import com.ra12.projecte1.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    TaskRepository repo;

    public ResponseEntity<String> createTask(taskRequestDTO task){
        repo.createTask();
    }

}
