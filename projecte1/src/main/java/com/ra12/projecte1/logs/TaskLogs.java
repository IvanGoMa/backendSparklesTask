package com.ra12.projecte1.logs;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.stereotype.Component;

@Component
public class TaskLogs {
    private final String LOG_DIRECTORY = "src/main/resources/private/logs/";

    public TaskLogs(){

    }

    // Construcció missatges d'error
    public String error(String classe, String function, String msg){

        Timestamp ara = new Timestamp(System.currentTimeMillis());
        return String.format("[%s] ERROR - %s - %s - %s",ara.toString(),classe,function,msg);
    }

    // Construcció missatges informatius
    public String info(String classe, String function, String msg){

        Timestamp ara = new Timestamp(System.currentTimeMillis());
        return String.format("[%s] INFO - %s - %s - %s",ara.toString(),classe,function,msg);
    }

    // Escriptura al fitxer
    public void writeToFile(String msg){

        
        // Construim el nom del fitxer amb la data actual
        Calendar ara = Calendar.getInstance();
        String filename = String.format("aplicacio-%d-%d-%d.log",ara.get(Calendar.YEAR), ara.get(Calendar.MONTH)+1, ara.get(Calendar.DAY_OF_MONTH));
        Path directori = Paths.get(LOG_DIRECTORY);
        Path file = Paths.get(LOG_DIRECTORY+filename);

        // Creem el directori si no existeix
        try{
            Files.createDirectories(directori);
        } catch (Exception e) {
        }

        // Obrim el fitxer, el creem si no existeix i no sobreescrivim les dades anteriors. A l'acabar tanquem el fitxer.
        try (BufferedWriter writer = Files.newBufferedWriter(file,StandardCharsets.UTF_8,StandardOpenOption.CREATE, StandardOpenOption.APPEND)){
            
            writer.append(msg);
            writer.newLine();
            
        } catch(Exception e){

        }

        

    }
}
