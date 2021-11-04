package com.ua.command.update;

import com.ua.Utils.CloseLink;
import com.ua.Utils.Constant;
import com.ua.command.Command;
import com.ua.command.get.LoginCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

@Component("doctorToPatient")
@Scope("prototype")
public class AppointDoctorToPatientCommand implements Command {

    private static final Logger log = LogManager.getLogger(AppointDoctorToPatientCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) {
        //patient-physician communication via the hospital chart
        HttpSession session = req.getSession();
        PreparedStatement ps = null;
        try {
            int idDoctor = Integer.parseInt(req.getParameter("selectDoctor"));
            int idCaseRecord = Integer.parseInt(req.getParameter("selectPatient"));
            ps = con.prepareStatement(Constant.SQL_UPDATE_PATIENT_CASERECORDS);
            int k = 1;
            ps.setInt(k++, idDoctor);
            ps.setInt(k++, idCaseRecord);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException throwables) {
            log.error("command AppointDoctorToPatient not executed" + con, throwables);
            session.setAttribute("errorMessage", 1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.close(con);
        }
        return Constant.URL_CONTROLLER_VIEW_STAFF;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        return null;
    }
}
