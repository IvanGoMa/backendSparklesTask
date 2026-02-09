package com.ra12.projecte1.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user/{username}/pwd")
    public ResponseEntity<String> getPwd(@PathVariable String username) {
        return ;
    }

    @GetMapping("/user/{username}/sparks")
    public ResponseEntity<String> getSparks(@PathVariable String username) {
        return new String();
    }

    @PatchMapping("/user/{username}/sparks/{sparks}")
    public ResponseEntity<String> updateSparks(@PathVariable String username, @PathVariable long sparks){
        
    }
    
    

}
