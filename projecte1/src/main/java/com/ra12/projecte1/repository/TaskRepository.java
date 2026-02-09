package com.ra12.projecte1.repository;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ra12.projecte1.model.Task;

@Repository
public class TaskRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<Task> taskMapper = new RowMapper<Task>() {
        
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task t = new Task();
            t.setId(rs.getLong("id"));
            t.setNomTasca(rs.getString("nomTasca"));
            t.setSparks(rs.getInt("sparks"));
            t.setDataLimit(rs.getTimestamp("dataLimit"));
            t.setDataCreated(rs.getTimestamp("dataCreated"));
            t.setDataUpdated(rs.getTimestamp("dataUpdated"));
            t.setUrlImage(rs.getString("urlImage"));
            return t;
        }
        
    };


    // Crear tasca
    public int createTask(Task task){
    String sql = "INSERT INTO task (nomTasca, sparks, dataLimit) VALUES (?, ?, ?)";
    return jdbcTemplate.update(sql, 
        task.getNomTasca(), 
        task.getSparks(), 
        task.getDataLimit()
    );
    }

    // Llegir totes les tasques
    public List<Task> readAll(){
        return jdbcTemplate.query("SELECT * FROM task", taskMapper);
    }

    // Llegir per id
    public Task readById(long id){
        String query = "SELECT * FROM task WHERE id = ?";
        return jdbcTemplate.queryForObject(query, taskMapper, id);
    }






    // Funció auxiliar per comprovar existencia
    public Task findTaskById(Long id){
        String sql = "SELECT * FROM task WHERE id = ?";
        List<Task> result = jdbcTemplate.query(sql, taskMapper, id);
        return result.get(0);
    }

    // set image path -> int ( n valors modificats)
    public int setImagePath(Long id, String urlImage){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String sql = "UPDATE task SET urlImage = ?, dataUpdated = ? WHERE id = ?";
        return jdbcTemplate.update(sql, urlImage, now, id);
    }

    //update x id - int
    public int updateTaskById(Long id, Task tasca){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String sql = "UPDATE task SET nomTasca = ?, sparks = ?, dataLimit = ?, dataUpdated = ?,  urlImage = ? WHERE id = ?";
        return jdbcTemplate.update(sql, tasca.getNomTasca(), tasca.getSparks(), tasca.getDataLimit(), now, tasca.getUrlImage(), id);
    }

    //delete all - int
    public int deleteAll(){
        String sql = "DELETE FROM task";
        return jdbcTemplate.update(sql);
    }

    //detete x id - int
    public int deleteById(Long id){
        String sql = "DELETE FROM task WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}