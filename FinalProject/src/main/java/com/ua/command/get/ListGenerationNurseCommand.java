package com.ua.command.get;

import com.ua.ConnectionPool;
import com.ua.Utils.CloseLink;
import com.ua.Utils.Constant;
import com.ua.command.Command;
import com.ua.entity.Nurse;
import com.ua.entity.Staff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.ua.Utils.CreateElement.newElement;

@Component("viewNurse")
@Scope("prototype")
public class ListGenerationNurseCommand implements Command {

    private static final Logger log= LogManager.getLogger(ListGenerationNurseCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) {
        //returns lists of nurse
        HttpSession session = req.getSession();

        try {
            ResultSet rs = con.createStatement()
                    .executeQuery(Constant.SQL_SELECT_NURSE);
            List<Nurse> nurses = new ArrayList<>();
            while (rs.next()) {
                Nurse nurse = (Nurse) newElement(rs, "nurse", con);
                nurses.add(nurse);
            }
            nurses.sort(Comparator.comparing(Staff::getName));
            session.setAttribute("nurses",nurses);
            rs.close();
        } catch (Exception ex) {
            log.error("command ListGenerationNurse not executed" + con, ex);
            session.setAttribute("errorMessage",1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.close(con);
        }
        return Constant.URL_CONTROLLER_VIEW_PATIENT;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session= req.getSession();
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
        } catch (SQLException throwables) {
            log.error("connection lost " + con, throwables);
            session.setAttribute("errorMessage",1);
            return Constant.URL_ERROR_PAGE;
        }
        return execute(req, resp, con);
    }
}
