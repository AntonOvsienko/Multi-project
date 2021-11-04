package com.ua;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class ConnectionPool {
    private static ConnectionPool instance;
    private static DataSource ds;

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private ConnectionPool() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/FinalBD");
        } catch (NamingException ex) {
            throw new IllegalStateException("Cannot init ConnectionPool", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
