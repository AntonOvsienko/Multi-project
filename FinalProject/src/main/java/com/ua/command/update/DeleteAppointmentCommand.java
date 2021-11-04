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

@Component("deleteAppointment")
@Scope("prototype")
public class DeleteAppointmentCommand implements Command {

    private static final Logger log = LogManager.getLogger(DeleteAppointmentCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) {
        //Deletes the doctor's appointment
        HttpSession session = req.getSession();
        System.out.println("session ==> " + session);
        PreparedStatement ps = null;
        int caseRecordId = (int) session.getAttribute("caseRecordId");
        String  URL = Constant.URL_ADD_APPOINTMENT + caseRecordId;
        String[] checkbox = req.getParameterValues("appointment");
        try {
            con.setAutoCommit(false);
            for (int i = 0; i < checkbox.length; i++) {
                ps = con.prepareStatement(Constant.SQL_DOCTOR_APPOINTMENT_DELETE);
                int k=1;
                ps.setInt(k++,Integer.parseInt(checkbox[i]));
                ps.execute();
            }
            ps.close();
            con.commit();
        } catch (SQLException throwables) {
            CloseLink.rollback(con);
            log.error("command DeleteAppointment not executed" + con, throwables);
            session.setAttribute("errorMessage", 1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.setAutoCommitOn(con);
            CloseLink.close(con);
        }
        return URL;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        return null;
    }
}
