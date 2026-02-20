package com.ra12.projecte1.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ra12.projecte1.dto.APIResponse;
import com.ra12.projecte1.model.Task;
import com.ra12.projecte1.dto.taskRequestDTO;
import com.ra12.projecte1.dto.taskResponseDTO;
import com.ra12.projecte1.services.TaskService;
import com.ra12.projecte1.services.UserService;

import java.util.List;

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
    @Autowired
    UserService uService;

    // Llegir totes les tasques
    @GetMapping("/task")
    public ResponseEntity<List<taskResponseDTO>> readAll(){
        try {
            List<taskResponseDTO> response = service.readAll();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse<>(false, "No s'han pogut llegir les tasques", null));
        }
    }

    // Legir tasca per id
    @GetMapping("/task/{id}")
    public ResponseEntity<APIResponse<taskResponseDTO>> readById(@PathVariable long id) {
        try{
            taskResponseDTO response = service.readById(id);
            return ResponseEntity.ok(new APIResponse<>(true, "Tasca retornada", response));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse<>(false, "No s'ha pogut llegir la tasca", null));
        }
    }
    
    // Crear una tasca a partir del RequestDTO
    @PostMapping("/task")
    public ResponseEntity<APIResponse<Void>> createTask(@RequestBody taskRequestDTO task) {
        String[] resposta = service.createTask(task);
        if (resposta[0].equals("ok")){
            return ResponseEntity.ok(new APIResponse<>(true,resposta[1], null));
        }else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse<>(false, resposta[1], null));
    }

    // Afegir una imatge a la tasca
    @PostMapping("/task/{taskId}/add/imatge")
    public ResponseEntity<APIResponse<Void>> postMethodName(@PathVariable Long taskId, MultipartFile imatge) throws Exception {
        String[] resposta = service.addImage(taskId, imatge);
        if (resposta[0].equals("ok")){ 
            return ResponseEntity.ok(new APIResponse<>(true,resposta[1],null));
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(false,resposta[1],null));
        }
    }

    // Afegir tasques desde un csv
    @PostMapping("task/batch")
    public ResponseEntity<APIResponse<Void>> importTasks(@RequestBody MultipartFile csv) {
        try {
            service.createTasks(csv);
            return ResponseEntity.ok(new APIResponse<>(true,"Tasques importades",null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse<>(false,"No s'han pogut importar les tasques",null));
        }
    }

    // Actualitzar tasca
    @PutMapping("/task/update/{taskId}")
    public ResponseEntity<APIResponse<Void>> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        // crida la funcio del service
        int result = service.updateTask(taskId, task);
        if (result > 0) {
            return ResponseEntity.ok(new APIResponse<>(true,"Tasca modificada correctament.", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(false,"Tas no trobat.",null));
        }
    }
    
    // Esborrar totes les tasques
    @DeleteMapping("/task/delete/all")
    public ResponseEntity<APIResponse<Void>> deleteAllTasks() {
        // crida la funcio del service
        int result = service.deleteAll();
        if (result > 0) {
            return ResponseEntity.ok(new APIResponse<>(true,"Totes les tasques han estat eliminades correctament.",null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse<>(false,"No s'han pogut eliminar les tasques", null));
        }
    }

    // Esborrar una tasca per l'id
    @DeleteMapping("/task/delete/{taskId}")
    public ResponseEntity<APIResponse<Void>> deleteTask(@PathVariable Long taskId) {
        // crida la funcio del service
        int result = service.deleteById(taskId);
        if (result > 0) {
            return ResponseEntity.ok(new APIResponse<>(true,"Tasca eliminada correctament",null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse<>(false,"Tasca no trobada.",null));
        }
    }

    // Completar tasca
    @DeleteMapping("/task/complete/{taskId}")
    public ResponseEntity<APIResponse<Long>> completeTask(@PathVariable long taskId){
        long result = service.completeTask(taskId);
        long oldSparks = uService.getSparks("Hanna");
        uService.updateSparks("Hanna", oldSparks + result);
        long newSparks = uService.getSparks("Hanna");
        if (result > 0) {
            return ResponseEntity.ok(new APIResponse<>(true, "Tasca completada", newSparks));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse<>(false, "No s'ha pogut eliminar la tasca", null));
    }
    
    
}
