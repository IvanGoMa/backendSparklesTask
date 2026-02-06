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
            t.setNomTaska(rs.getString("nomTaska"));
            t.setSparks(rs.getInt("sparks"));
            t.setDataLimit(rs.getTimestamp("dataLimit"));
            t.setDataCreated(rs.getTimestamp("dataCreated"));
            t.setDataUpdated(rs.getTimestamp("dataUpdated"));
            t.setUrlImage(rs.getString("urlImage"));
            return t;
        }
        
    };

    //I

    //create task & optional para imagen
    public int createTask(Task task){
    String sql = "INSERT INTO tasks (nomTaska, sparks, dataLimit, dataCreated, dataUpdated, ultimAcces) VALUES (?, ?, ?, ?, ?, ?)";
    Timestamp now = new Timestamp(System.currentTimeMillis());
    return jdbcTemplate.update(sql, 
        task.getNomTaska(), 
        task.getSparks(), 
        task.getDataLimit(), 
        now, 
        now, 
        now
    );
}

    // read all

    // read x id





    //H

    public void updateUltimAcces(Timestamp hora, Long id){
        String sql = "UPDATE tasks SET ultimAcces = ? WHERE id = ?";
        jdbcTemplate.update(sql, hora, id);
    }

    // find by id
    public Task findTaskById(Long id){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String sql = "SELECT * FROM tasks WHERE id = ?";
        List<Task> result = jdbcTemplate.query(sql, taskMapper, id);
        updateUltimAcces(now, id);
        return result.get(0);
    }

    // set image path -> int ( n valors modificats)
    public int setImagePath(Long id, String urlImage){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String sql = "UPDATE tasks SET urlImage = ?, dataUpdated = ?, ultimAcces = ? WHERE id = ?";
        return jdbcTemplate.update(sql, urlImage, now, now, id);
    }

    //update x id - int
    public int updateTaskById(Long id, Task taska){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String sql = "UPDATE tasks SET nomTaska = ?, sparks = ?, dataLimit = ?, dataUpdated = ?, ultimAcces = ?, urlImage = ? WHERE id = ?";
        return jdbcTemplate.update(sql, taska.getNomTaska(), taska.getSparks(), taska.getDataLimit(), now, now, taska.getUrlImage(), id);
    }

    //delete all - int
    public int deleteAll(){
        String sql = "DELETE FROM tasks";
        return jdbcTemplate.update(sql);
    }

    //detete x id - int
    public int deleteById(Long id){
        String sql = "DELETE FROM tasks WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
