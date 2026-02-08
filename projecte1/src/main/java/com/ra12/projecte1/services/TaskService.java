package com.ra12.projecte1.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ra12.projecte1.model.Task;
import com.ra12.projecte1.odt.taskRequestDTO;
import com.ra12.projecte1.odt.taskResponseDTO;
import com.ra12.projecte1.repository.TaskRepository;



@Service
public class TaskService {

    private static final Path PATH_DIR = Paths.get("private");

    @Autowired
    TaskRepository repo;

    public taskResponseDTO readById(long id){

        try {
            Task task = repo.readById(id);
            return task.toTaskResponseDTO();

        } catch (Exception e) {
            return new taskResponseDTO();
        }
    }

    public List<taskResponseDTO> readAll(){
        List<taskResponseDTO> tasksResponse = new ArrayList<>();

        try {
            List<Task> tasks = repo.readAll();
            for (Task task: tasks){
                tasksResponse.add(task.toTaskResponseDTO());
            }
            return tasksResponse;
        } catch (Exception e){
            return tasksResponse;
        }
        
    }

    public String[] createTask(taskRequestDTO taskDTO){
        Task task = new Task();
        task.setNomTasca(taskDTO.getNomTasca());
        task.setSparks(taskDTO.getSparks());
        task.setDataLimit(taskDTO.getDataLimit());
        
        try {
            int result = repo.createTask(task);
            if (result > 0) {
                return new String[]{"ok", "Tasca s'ha creat correctament"};
            } else {
                return new String[]{"e", "No s'ha pogut crear la tasca"};
            }
        } catch (Exception e) {
            return new String[]{"e", e.getMessage()};
        }
    }

    public boolean createTasks(MultipartFile csv) throws IOException{
        String linia;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        try(BufferedReader br = new BufferedReader(new InputStreamReader(csv.getInputStream()))){
            while((linia = br.readLine())!= null){
                String[] c = linia.split(",");
                try{
                    repo.createTask(new Task(c[0],Integer.parseInt(c[1]),Timestamp.valueOf(c[2]), now, now));
                } catch(Exception e){
                    
                }
            }
            return true;

        } catch (Exception e){
            return false;
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
