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

@Component("confirmNurseAppointment")
@Scope("prototype")
public class ConfirmNurseAppointmentCommand implements Command {

    private static final Logger log= LogManager.getLogger(ConfirmNurseAppointmentCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) {
        //notes the nurse who made the appointment
        HttpSession session = req.getSession();
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] checkbox = req.getParameterValues("appointment");
        try {
            con.setAutoCommit(false);
            StringBuilder nameSurname = new StringBuilder();
            int id = (int) session.getAttribute("keyLogin");
            ps = con.prepareStatement(Constant.SQL_SELECT_NURSE_WHERE_LOGIN_PASSWORD);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                nameSurname.append(name).append(" ").append(surname);
            }
            ps = con.prepareStatement(Constant.SQL_SELECT_LOGIN_PASSWORD_WHERE_ID);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String role = rs.getString("role");
                if (role.equals("doctor")) {
                    nameSurname.append("(").append("доктор").append(")");
                }
                if (role.equals("nurse")) {
                    nameSurname.append("(").append("медсестра").append(")");
                }
            }
            for (int i = 0; i < checkbox.length; i++) {
                ps = con.prepareStatement(Constant.SQL_UPDATE_DOCTOR_APPOINTMENT);
                ps.setString(1,nameSurname.toString());
                ps.setInt(2,Integer.parseInt(checkbox[i]));
                ps.execute();
            }
            con.commit();
        } catch (SQLException throwables) {
            CloseLink.rollback(con);
            log.error("command ConfirmNurseAppointment not executed" + con, throwables);
            session.setAttribute("errorMessage",1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.close(con);
        }
        return Constant.URL_CONTROLLER_VIEW_NURSE;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        return null;
    }
}
