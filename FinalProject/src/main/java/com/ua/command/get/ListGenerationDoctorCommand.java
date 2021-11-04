package com.ua.command.get;

import com.ua.ConnectionPool;
import com.ua.Utils.CloseLink;
import com.ua.Utils.Constant;
import com.ua.command.Command;
import com.ua.entity.Department;
import com.ua.entity.Doctor;
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

@Component("viewStaff")
@Scope("prototype")
public class ListGenerationDoctorCommand implements Command {

    private static final Logger log= LogManager.getLogger(ListGenerationDoctorCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) {
        //returns lists of doctors
        HttpSession session = req.getSession();

        try {
            ResultSet rs = con.createStatement()
                    .executeQuery(Constant.SQL_SELECT_DOCTOR);
            List<Doctor> doctors = new ArrayList<>();
            while (rs.next()) {
                Doctor doctor = (Doctor) newElement(rs, "doctor", con);
                doctors.add(doctor);
            }
            List<Doctor> doctorsSortByName = new ArrayList<>(doctors);
            List<Doctor> doctorsSortByCategory = new ArrayList<>(doctorsSortByName);
            List<Doctor> doctorsSortByNumberPatient = new ArrayList<>(doctors);

            doctorsSortByName.sort(Comparator.comparing(Staff::getName));
            doctorsSortByCategory.sort(Comparator.comparing(Staff::getDepartment));
            doctorsSortByNumberPatient.sort(Comparator.comparingInt(o -> o.getCaseRecords().size()));

            List<Department> departments=new ArrayList<>();

            rs = con.createStatement().executeQuery(Constant.SQL_SELECT_DEPARTMENT);
            while (rs.next()) {
                Department department = new Department();
                department.setId(rs.getInt("id"));
                department.setDescription(rs.getString("department"));
                departments.add(department);
            }
            departments.sort(Comparator.comparing(Department::getDescription));
            session.setAttribute("departments",departments);
            session.setAttribute("doctors", doctors);
            session.setAttribute("doctorsByCategory", doctorsSortByCategory);
            session.setAttribute("doctorsByName", doctorsSortByName);
            session.setAttribute("doctorsByNumberPatient", doctorsSortByNumberPatient);
            rs.close();
        } catch (Exception ex) {
            log.error("command ListGenerationDoctor not executed" + con, ex);
            session.setAttribute("errorMessage",1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.close(con);
        }
        return Constant.URL_CONTROLLER_VIEW_NURSE;
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
