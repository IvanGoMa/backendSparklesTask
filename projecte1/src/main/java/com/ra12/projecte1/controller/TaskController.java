package com.ra12.projecte1.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ra12.projecte1.odt.taskRequestDTO;
import com.ra12.projecte1.services.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskService service;

    @PostMapping("/task")
    public ResponseEntity<String> createTask(@RequestBody taskRequestDTO task) {

        
        return service.createTask(task);
    }

    @PostMapping("/task/{taskId}/add/imatge")
    public ResponseEntity<String> postMethodName(@PathVariable Long taskId, @RequestParam("imageFile") MultipartFile imatge) throws Exception {
        String[] resposta = service.addImage(taskId, imatge);
        if (resposta[0].equals("ok")){ 
            return ResponseEntity.ok(resposta[1]);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta[1])
        }
    }
    
    
}
