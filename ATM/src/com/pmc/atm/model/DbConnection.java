package com.pmc.atm.model;

import java.sql.*;

public class DbConnection {
    public void forName() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

    public static Connection getConnection(String url, String name, String password) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, name, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

}
