package com.ua.command.get;

import com.ua.ConnectionPool;
import com.ua.Utils.CloseLink;
import com.ua.Utils.Constant;
import com.ua.command.Command;
import com.ua.entity.Doctor;
import com.ua.entity.DoctorAppointment;
import com.ua.entity.Patient;
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
import java.util.List;


import static com.ua.Utils.CreateElement.newElement;

@Component("doctorAppointment")
@Scope("prototype")
public class DoctorAppointmentListCommand implements Command {

    private static final Logger log= LogManager.getLogger(DoctorAppointmentListCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) {
        //Returns lists of appointments
        HttpSession session = req.getSession();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        List<DoctorAppointment> doctorAppointmentList = new ArrayList<>();
        try {
            int caseRecordId;
            if (req.getParameter("caseRecordId") != null) {
                caseRecordId = Integer.parseInt(req.getParameter("caseRecordId"));
                session.setAttribute("caseRecordId", caseRecordId);
            } else {
                caseRecordId = (int) session.getAttribute("caseRecordId");
            }
            ps = con.prepareStatement(Constant.SQL_APPOINTMENT_SELECT);
            ps.setInt(1, caseRecordId);
            rs = ps.executeQuery();
            while (rs.next()) {
                int idDoctor = rs.getInt("doctor_id");
                int idPatient = rs.getInt("patient_id");
                int idCaseRecord = rs.getInt("case_record_id");
                ps = con.prepareStatement(Constant.SQL_SELECT_DOCTOR_WHERE_ID);
                ps.setInt(1, idDoctor);
                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    doctor = (Doctor) newElement(rs2, "doctor", con);
                }
                ps = con.prepareStatement(Constant.SQL_SELECT_PATIENT_WHERE_ID);
                ps.setInt(1,idPatient);
                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    patient = (Patient) newElement(rs2, "patient", con);
                }
                ps = con.prepareStatement(Constant.SQL_SELECT_DOCTOR_APPOINTMENT_WHERE_CASERECORDS_ID);
                ps.setInt(1,idCaseRecord);
                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    DoctorAppointment da = new DoctorAppointment();
                    daCreate(rs2, da);
                    doctorAppointmentList.add(da);
                }
            }
            rs2.close();
            rs.close();
            ps.close();
            session.setAttribute("appointmentList", doctorAppointmentList);
            session.setAttribute("doctor", doctor);
            session.setAttribute("patient", patient);
        } catch (SQLException throwables) {
            log.error("command DoctorAppointmentList not executed" + con, throwables);
            session.setAttribute("errorMessage",1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.close(con);
        }
        return Constant.URL_CONTROLLER_VIEW_CASERECORD;
    }

    private void daCreate(ResultSet rs2, DoctorAppointment da) throws SQLException {
        da.setId(rs2.getInt("id"));
        da.setType(rs2.getString("type"));
        da.setDescription(rs2.getString("description"));
        da.setComplete(rs2.getString("complete"));
        da.setNameStaffComplete(rs2.getString("name_staff_complete"));
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        return execute(req, resp, ConnectionPool.getConnection());
    }
}
