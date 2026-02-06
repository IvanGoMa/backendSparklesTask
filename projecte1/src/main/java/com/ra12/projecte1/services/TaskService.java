package com.ra12.projecte1.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ra12.projecte1.model.Task;
import com.ra12.projecte1.odt.taskRequestDTO;
import com.ra12.projecte1.repository.TaskRepository;



@Service
public class TaskService {

    private static final Path PATH_DIR = Paths.get("private");

    @Autowired
    TaskRepository repo;

    public String[] createTask(taskRequestDTO taskDTO){
        Task task = new Task();
        task.setNomTaska(taskDTO.getNomTaska());
        task.setSparks(taskDTO.getSparks());
        task.setDataLimit(taskDTO.getDataLimit());
        
        try {
            int result = repo.createTask(task);
            if (result > 0) {
                return new String[]{"ok", "Taska s'ha creat correctament"};
            } else {
                return new String[]{"e", "No s'ha pogut crear la taska"};
            }
        } catch (Exception e) {
            return new String[]{"e", e.getMessage()};
        }
    }

    
    public String[] addImage(Long id, MultipartFile image){
        Task existeix = idExisteix(id);
        if (existeix == null){
            return new String[]{"e", "Usuari no trobat"};
        }else {
            try{
                if (Files.notExists(PATH_DIR)){
                    Files.createDirectories(PATH_DIR);
                }
                String urlImage = image.getOriginalFilename();
                Path urlSencer = PATH_DIR.resolve(urlImage);
                Files.copy(image.getInputStream(), urlSencer, StandardCopyOption.REPLACE_EXISTING);
                int resposta = repo.setImagePath(id, urlSencer.toString());

                if (resposta == 0){
                    return new String[] {"e", "No s'ha pogut actualitzar l'imatge"};
                }else {
                    return new String[] {"ok", "Imatge actualitzada correctament"};
                }
            }catch (Exception e){
                return new String[] {"e", e.getMessage()};
            }
        }
    }

    public Task idExisteix(Long id){
        Task task = repo.findTaskById(id) != null ? repo.findTaskById(id) : null;
        return task;
    }

}
