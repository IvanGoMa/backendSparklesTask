package com.ra12.projecte1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ra12.projecte1.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    public String getPwd(String name){
        try{
            return repo.getPwd(name);
        } catch (Exception e){
            return "";
        }
    }

    public long getSparks(String name){
        try{
            return repo.getSparks(name);
        } catch (Exception e){
            return -1;
        }
        
    }

}
