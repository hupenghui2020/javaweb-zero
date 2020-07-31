package com.hph.smart.service;

import com.hph.smart.domain.User;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.helper.DatabaseHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hph
 */
@Service
public class SmartDemoService {

    public List<User> getUserList() throws SQLException {
        Connection conn = DatabaseHelper.getConnection();
        ResultSet resultSet = conn.createStatement().executeQuery("select * from user");
        List<User> userList = new ArrayList<User>();
        while (resultSet.next()) {
            long id = resultSet.getLong(0);
            String name = resultSet.getString(1);
            long age = resultSet.getLong(2);
            User user = new User(id, name, age);
            userList.add(user);
        }
        return userList;
    }
}
