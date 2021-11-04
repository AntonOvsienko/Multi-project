package com.ua.command.add;

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
import java.sql.*;

@Component("addAppointment")
@Scope("prototype")
public class AddAppointmentCommand implements Command {

    private static final Logger log= LogManager.getLogger(AddAppointmentCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) {
        //adding medical appointments to the database
        HttpSession session = req.getSession();
        int caseRecordId = Integer.parseInt(req.getParameter("caseRecordId"));
        String select;
        String description;
        String URL = Constant.URL_ADD_APPOINTMENT + caseRecordId;

        try {
            con.setAutoCommit(false);
            if (req.getParameter("select1") == null & req.getParameter("select2") == null &
                    req.getParameter("select3") == null & req.getParameter("select4") == null) {
                return URL;
            }
            if (req.getParameter("select1") != null) {
                select = req.getParameter("select1");
                description = req.getParameter("description1");
                addInTable(req, con, select, description);
            }
            if (req.getParameter("select2") != null) {
                select = req.getParameter("select2");
                description = req.getParameter("description2");
                addInTable(req, con, select, description);
            }
            if (req.getParameter("select3") != null) {
                select = req.getParameter("select3");
                description = req.getParameter("description3");
                addInTable(req, con, select, description);
            }
            if (req.getParameter("select4") != null) {
                select = req.getParameter("select4");
                description = req.getParameter("description4");
                addInTable(req, con, select, description);
            }
            con.commit();
        } catch (SQLException throwables) {
            CloseLink.rollback(con);
            log.error("command AddAppointment not executed" + con, throwables);
            session.setAttribute("errorMessage",1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.close(con);
        }
        System.out.println(URL);
        return URL;
    }

    private void addInTable(HttpServletRequest req, Connection con, String select, String description) throws SQLException {
        HttpSession session = req.getSession();
        int case_record_id = 0;
        PreparedStatement ps;
        ps = con.prepareStatement(Constant.SQL_APPOINTMENT_SELECT);
        int k = 1;
        ps.setInt(k++, Integer.parseInt(req.getParameter("caseRecordId")));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            case_record_id = rs.getInt("case_record_id");
        }
        ps = con.prepareStatement(Constant.SQL_APPOINTMENT_INSERT);
        k = 1;
        ps.setInt(k++, case_record_id);
        ps.setString(k++, select);
        ps.setString(k++, description);
        ps.setString(k++, null);
        System.out.println(ps.executeUpdate());
        ps.close();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        return null;
    }
}
