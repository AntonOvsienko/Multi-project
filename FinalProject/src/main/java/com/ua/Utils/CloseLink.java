package com.ua.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.sql.Connection;


public class CloseLink {

    private static final Logger log = LogManager.getLogger(CloseLink.class.getName());

    public static void close(AutoCloseable ac) {
        if (ac != null) {
            try {
                ac.close();
            } catch (Exception e) {
                log.error("Cannot close resource: " + ac, e);
//                req.setAttribute("ex", ex);
//                req.getRequestDispatcher("error.jsp").forward(req, resp);
            }
        }
    }

    public static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (Exception e) {
                log.error("Cannot rollback connection: " + con, e);
            }
        }
    }

    public static void setAutoCommitOn(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true);
            } catch (Exception e) {
                log.error("Cannot setAutoCommit connection: " + con, e);
            }
        }
    }
}
