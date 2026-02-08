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

import com.ra12.projecte1.logs.TaskLogs;
import com.ra12.projecte1.model.Task;
import com.ra12.projecte1.odt.taskRequestDTO;
import com.ra12.projecte1.odt.taskResponseDTO;
import com.ra12.projecte1.repository.TaskRepository;



@Service
public class TaskService {

    private static final Path PATH_DIR = Paths.get("private");

    @Autowired
    TaskRepository repo;

    @Autowired
    TaskLogs log;

    public taskResponseDTO readById(long id){

        String msg;
        try {
            Task task = repo.readById(id);
            msg = log.info("TaskService", "readById", "Consultant la tasca");
            log.writeToFile(msg);
            return task.toTaskResponseDTO();

        } catch (Exception e) {
            msg = log.error("TaskService", "readById", "No s'ha pogut consultar la tasca");
            log.writeToFile(msg);
            return new taskResponseDTO();
        }
    }

    public List<taskResponseDTO> readAll(){
        String msg = log.info("TaskService", "readAll", "Consultant tots el usuaris");
        log.writeToFile(msg);
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
        
        String msg = log.info("TaskService", "createTask", "Creant una tasca");
        log.writeToFile(msg);

        try {
            int result = repo.createTask(task);
            if (result > 0) {
                log.writeToFile(log.info("TaskService", "createTask", "Usuari creat correctament"));
                return new String[]{"ok", "Tasca s'ha creat correctament"};
            } else {
                log.writeToFile(log.error("TaskService", "createTask", "No s'ha pogut crear l'usuari"));
                return new String[]{"e", "No s'ha pogut crear la tasca"};
            }
        } catch (Exception e) {
            log.writeToFile(log.error("TaskService", "createTask", "No s'ha pogut crear l'usuari"));
            return new String[]{"e", e.getMessage()};
        }
    }

    public int createTasks(MultipartFile csv) throws IOException{

        String msg = log.info("TaskService", "createTasks", "Carregant la informació del fitxer " + csv.getName());
        log.writeToFile(msg);

        int comptador = 0;
        int erronis = 0;

        
        Timestamp now = new Timestamp(System.currentTimeMillis());
        try(BufferedReader br = new BufferedReader(new InputStreamReader(csv.getInputStream()))){
            String linia;
            int nLinia = 1;
            while((linia = br.readLine())!= null){
                String[] c = linia.split(",");
                try{
                    repo.createTask(new Task(c[0],Integer.parseInt(c[1]),Timestamp.valueOf(c[2]), now, now));
                    comptador++;
                } catch(Exception e){
                    msg = log.error("TaskService", "createTasks",
                        String.format("Error en la línia %d del fitxer. Missatge d'error: %s",nLinia,e));
                    log.writeToFile(msg);
                    erronis++;
                }
                nLinia++;
            }

        } catch (IOException e){
            System.err.println("Error d'accès al fitxer: " + e.getMessage());
           msg = log.error("TasksService", "createTask", "Error de lectura de l'arxiu");
           log.writeToFile(msg);
           return -1;
        }
        String dir = "src/main/resources/private/csv_processed";
        Path directory = Paths.get(dir);
        Path filePath = Paths.get(dir + "/" + csv.getOriginalFilename());
        
        // Guardem el csv
        try{
            Files.createDirectories(directory);
            Files.copy(csv.getInputStream(),filePath);
            
        }
        catch (Exception e){
            System.err.println("No s'ha pogut guardar el csv");
            msg = log.error("TaskService", "createTasks", "No s'ha pogut guardar l'arxiu");
            log.writeToFile(msg);
        }
        msg = log.info("TaskService", "createTasks",
            String.format("S'han guardat correctament %d registres i han donat error %d registres",comptador, erronis));
        log.writeToFile(msg);
        // Retornem registres creats
        return comptador;

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
