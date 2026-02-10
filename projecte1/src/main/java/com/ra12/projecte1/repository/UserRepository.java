package com.ra12.projecte1.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ra12.projecte1.model.User;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbc;

    private RowMapper<User> userMapper = new RowMapper<User>(){

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException{
            User u = new User();
            u.setId(rs.getLong("id"));
            u.setUsername(rs.getString("username"));
            u.setSparks(rs.getLong("sparks"));
            u.setPassword(rs.getString("password"));
            return u;
        }

    };

    public long getSparks(String username){
        String sql = "SELECT * FROM user WHERE username = ?";
        User user = jdbc.queryForObject(sql, userMapper, username);
        return user.getSparks();
    }

    public String getPwd(String username){
        String sql = "SELECT * FROM user WHERE username = ?";
        User user = jdbc.queryForObject(sql, userMapper, username);
        return user.getPassword();
    }


    public void updateSparks (String username, long sparks){
        String sql = "UPDATE user SET sparks = ? WHERE username = ? ";
        jdbc.update(sql, sparks, username);
    }

    public void createUser (User user){
        String sql = "INSERT INTO user (username, password, sparks) VALUES (?,?,?)";
        jdbc.update(sql,user.getUsername(),user.getPassword(),user.getSparks());
    }
}
