package com.ua.command.get;

import com.ua.ConnectionPool;
import com.ua.Utils.CloseLink;
import com.ua.Utils.Constant;
import com.ua.command.Command;
import com.ua.entity.Patient;
import com.ua.entity.Staff;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.ua.Utils.CreateElement.newElement;

@Component("viewPatient")
@Scope("prototype")
public class ListGenerationPatientCommand implements Command {

    private static final Logger log= LogManager.getLogger(ListGenerationPatientCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) {
        //returns lists of patients
        HttpSession session = req.getSession();
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            ps=con.prepareStatement(Constant.SQL_SELECT_PATIENT);
            rs = ps.executeQuery();
            List<Patient> patients = new ArrayList<>();
            while (rs.next()) {
                Patient patient = (Patient) newElement(rs, "patient", con);
                patients.add(patient);
            }
            List<Patient> patientsSortByName = new ArrayList<>(patients);
            List<Patient> patientsSortByBirthday = new ArrayList<>(patients);

            patientsSortByName.sort(Comparator.comparing(Staff::getName));
            patientsSortByBirthday.sort((o1, o2) -> {
                if (o1.getYearBorn() < o2.getYearBorn()) {
                    return 1;
                }
                if (o1.getYearBorn() > o2.getYearBorn()) {
                    return -1;
                }
                if (o1.getMonthBorn() < o2.getMonthBorn()) {
                    return 1;
                }
                if (o1.getMonthBorn() > o2.getMonthBorn()) {
                    return -1;
                }
                if (o1.getDayBorn() < o2.getDayBorn()) {
                    return 1;
                }
                if (o1.getDayBorn() > o2.getDayBorn()) {
                    return -1;
                }
                return 0;
            });
            session.setAttribute("patients", patients);
            session.setAttribute("patientsByName", patientsSortByName);
            session.setAttribute("patientsByBirthday", patientsSortByBirthday);
            ps.close();
            rs.close();
        } catch (Exception ex) {
            log.error("command ListGenerationPatient not executed" + con, ex);
            session.setAttribute("errorMessage",1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.close(con);
        }
        return Constant.URL_CONTROLLER_VIEW_CASERECORD;
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
