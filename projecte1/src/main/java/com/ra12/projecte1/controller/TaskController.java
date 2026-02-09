package com.ra12.projecte1.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ra12.projecte1.model.Task;
import com.ra12.projecte1.odt.taskRequestDTO;
import com.ra12.projecte1.services.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskService service;

    // Llegir totes les tasques
    @GetMapping("/task")
    public ResponseEntity<String> readAll(){
        try {
            String response = service.readAll().toString();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No s'han pogut llegir les tasques");
        }
    }

    // Legir tasca per id
    @GetMapping("/task/{id}")
    public ResponseEntity<String> readById(@PathVariable long id) {
        try{
            String response = service.readById(id).toString();
            return ResponseEntity.ok(response);
        } catch (Exception e){
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No s'ha pogut llegir la tasca");
        }
    }
    
    // Crear una tasca a partir del RequestDTO
    @PostMapping("/task")
    public ResponseEntity<String> createTask(@RequestBody taskRequestDTO task) {
        String[] resposta = service.createTask(task);
        if (resposta[0].equals("ok")){
            return ResponseEntity.ok(resposta[1]);
        }else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta[1]);
    }

    // Afegir una imatge a la tasca
    @PostMapping("/task/{taskId}/add/imatge")
    public ResponseEntity<String> postMethodName(@PathVariable Long taskId, MultipartFile imatge) throws Exception {
        String[] resposta = service.addImage(taskId, imatge);
        if (resposta[0].equals("ok")){ 
            return ResponseEntity.ok(resposta[1]);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta[1]);
        }
    }

    // Afegir tasques desde un csv
    @PostMapping("task/batch")
    public ResponseEntity<String> importTasks(@RequestBody MultipartFile csv) {
        try {
            service.createTasks(csv);
            return ResponseEntity.ok("Tasques importades");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No s'han pogut importar les tasques");
        }
    }

    // Actualitzar tasca
    @PutMapping("/task/update/{taskId}")
    public ResponseEntity<String> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        // crida la funcio del service
        int result = service.updateTask(taskId, task);
        if (result > 0) {
            return ResponseEntity.ok("Tasca modificada correctament.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tas no trobat.");
        }
    }
    
    // Esborrar totes les tasques
    @DeleteMapping("/task/delete/all")
    public ResponseEntity<String> deleteAllTasks() {
        // crida la funcio del service
        int result = service.deleteAll();
        if (result > 0) {
            return ResponseEntity.ok("Totes les tasques han estat eliminades correctament.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No s'han pogut eliminar les tasques.");
        }
    }

    // Esborrar una tasca per l'id
    @DeleteMapping("/task/delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        // crida la funcio del service
        int result = service.deleteById(taskId);
        if (result > 0) {
            return ResponseEntity.ok("Tasca eliminada correctament.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tasca no trobada.");
        }
    }
    
    
}
