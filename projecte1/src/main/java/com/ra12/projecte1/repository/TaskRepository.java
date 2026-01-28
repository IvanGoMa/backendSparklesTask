package com.ra12.projecte1.repository;

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

    //create task

    // read all

    // read x id





    //H

    // find by id
    public Task findTaskById(Long id){
        String sql = "SELECT * FROM tasks WHERE id = ?";
        List<Task> result = jdbcTemplate.query(sql, taskMapper, id);
        return result.get(0);
    }

    // set image path - 

    //update x id - 

    //delete all - 

    //detete x id - 



}
