package com.ua.command.update;

import com.ua.Utils.CloseLink;
import com.ua.Utils.Constant;
import com.ua.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component("updateNurse")
@Scope("prototype")
public class UpdateNurseCommand implements Command {

    private static final Logger log = LogManager.getLogger(UpdateNurseCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con){
        //nurse profile update
        HttpSession session = req.getSession();

        try {
            if (session.getAttribute("changeProfile") == null) {
                session.setAttribute("changeProfile", "true");
                session.setAttribute("successfully", "");
                session.setAttribute("error", "");
                return Constant.URL_UPDATE_NURSE;
            }
            if (!req.getParameter("password").equals(req.getParameter("password_repeat"))) {
                session.setAttribute("messageFalse", "1");
                return Constant.URL_UPDATE_NURSE;
            }
            session.setAttribute("successfully", "");
            con.setAutoCommit(false);
            updateLogin(con, req);
            con.commit();
        } catch (SQLException throwables) {
            CloseLink.rollback(con);
            log.error("command UpdateNurse not executed" + con, throwables);
            session.setAttribute("errorMessage", 1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.setAutoCommitOn(con);
            CloseLink.close(con);
        }
        session.setAttribute("successfully", "true");
        session.setAttribute("messageFalse", "0");
        session.setAttribute("changeProfile", "false");
        return Constant.URL_UPDATE_NURSE;
    }

    private void updateLogin(Connection con, HttpServletRequest req) throws SQLException {
        PreparedStatement preparedStatement;
        ResultSet st;
        int idLoginPassword = 0;
        int k;
        preparedStatement = con.prepareStatement(Constant.SQL_SELECT_LOGIN_PASSWORD_WHERE_LOGIN);
        k = 1;
        preparedStatement.setString(k++, req.getParameter("loginNurse"));
        st = preparedStatement.executeQuery();
        while (st.next()) {
            idLoginPassword = st.getInt("id");
        }
        preparedStatement = con.prepareStatement(Constant.SQL_NEW_LOGIN_UPDATE_NURSE);
        k = 1;
        System.out.println("name => " + req.getParameter("name"));
        System.out.println("surname => " + req.getParameter("surname"));
        System.out.println("telephone => " + req.getParameter("telephone"));
        System.out.println("passport => " + req.getParameter("passport"));
        System.out.println("idLoginPassword => " + idLoginPassword);
        preparedStatement.setString(k++, req.getParameter("name"));
        preparedStatement.setString(k++, req.getParameter("surname"));
        preparedStatement.setString(k++, req.getParameter("telephone"));
        preparedStatement.setString(k++, req.getParameter("passport"));
        preparedStatement.setInt(k++, idLoginPassword);
        preparedStatement.executeUpdate();
        k = 1;
        preparedStatement = con.prepareStatement(Constant.SQL_NEW_LOGIN_PASSWORD_UPDATE);
        preparedStatement.setString(k++, req.getParameter("password"));
        preparedStatement.setString(k++, req.getParameter("loginNurse"));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        return Constant.URL_UPDATE_NURSE;
    }
}
