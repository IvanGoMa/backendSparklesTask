package com.ra12.projecte1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ra12.projecte1.model.User;
import com.ra12.projecte1.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping("/user/{username}/pwd")
    public String getPwd(@PathVariable String username) {
         return service.getPwd(username);
    }

    @GetMapping("/user/{username}/sparks")
    public long getSparks(@PathVariable String username) {
        return service.getSparks(username);
        
    }

    @PatchMapping("/user/{username}/sparks/{sparks}")
    public ResponseEntity<String> updateSparks(@PathVariable String username, @PathVariable long sparks){
        service.updateSparks(username, sparks);
        return ResponseEntity.ok("Sparks actualitades per a l'usuari/a " + username);
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try{
            service.createUser(user);
            return ResponseEntity.ok("Usuari creat");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No s'ha pogit crear l'usuari: " + e);
        }
        
    }
    
    
    

}
