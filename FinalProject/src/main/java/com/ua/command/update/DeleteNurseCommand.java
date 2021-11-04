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

@Component("deleteNurse")
@Scope("prototype")
public class DeleteNurseCommand implements Command {

    private static final Logger log = LogManager.getLogger(DeleteNurseCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) {
        //deletes doctor login
        HttpSession session=req.getSession();
        PreparedStatement ps = null;
        String loginNurse = req.getParameter("loginNurse");
        try {
            ps=con.prepareStatement(Constant.SQL_LOGIN_PASSWORD_DELETE_WHERE_LOGIN);
            int k=1;
            ps.setString(k++,loginNurse);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException throwables) {
            log.error("command DeleteNurse not executed" + con, throwables);
            session.setAttribute("errorMessage", 1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.close(con);
        }
        return Constant.URL_CONTROLLER_VIEW_STAFF;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return null;
    }
}
