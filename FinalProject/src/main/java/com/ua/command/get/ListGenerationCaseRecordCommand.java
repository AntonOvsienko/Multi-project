package com.ua.command.get;

import com.ua.ConnectionPool;
import com.ua.Utils.CloseLink;
import com.ua.Utils.Constant;
import com.ua.Utils.CreateElement;
import com.ua.command.Command;
import com.ua.entity.CaseRecord;
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

@Component("viewCaseRecord")
@Scope("prototype")
public class ListGenerationCaseRecordCommand implements Command {

    private static final Logger log= LogManager.getLogger(ListGenerationCaseRecordCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) throws SQLException {
        //returns sick leave
        HttpSession session = req.getSession();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        List<CaseRecord> nurseCaseRecord = new ArrayList<>();
        try {
            ps = con.prepareStatement(Constant.SQL_SELECT_PATIENT_HAS_CASERECORDS);
            rs = ps.executeQuery();
            while (rs.next()) {
                List<DoctorAppointment> doctorAppointmentList = new ArrayList<>();
                Doctor doctor = new Doctor();
                Patient patient = new Patient();
                CaseRecord caseRecord = new CaseRecord();
                int patientId = rs.getInt("patient_id");
                int caseRecordId = rs.getInt("case_record_id");
                int doctorId = rs.getInt("doctor_id");
                ps = con.prepareStatement(Constant.SQL_SELECT_DOCTOR_WHERE_ID);
                ps.setInt(1,doctorId);
                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    int login_password=rs2.getInt("login_password_id");
                    createDoctor(rs2, doctor, doctorId);
                    ps = con.prepareStatement(Constant.SQL_SELECT_LOGIN_PASSWORD_WHERE_ID);
                    ps.setInt(1,login_password);
                    rs3 = ps.executeQuery();
                    while (rs3.next()) {
                        doctor.setLogin(rs3.getString("login"));
                    }
                }
                ps = con.prepareStatement(Constant.SQL_SELECT_PATIENT_WHERE_ID);
                ps.setInt(1,patientId);
                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    createPatient(rs2, patient, patientId);
                }
                ps = con.prepareStatement(Constant.SQL_SELECT_DOCTOR_APPOINTMENT_WHERE_CASERECORDS_ID);
                ps.setInt(1,caseRecordId);
                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    DoctorAppointment da = new DoctorAppointment();
                    doctorAppointmentCreate(rs2, da);
                    doctorAppointmentList.add(da);
                }
                ps = con.prepareStatement(Constant.SQL_SELECT_CASERECORD_WHERE_ID);
                ps.setInt(1,caseRecordId);
                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    caseRecord.setInitialDiagnosis(rs2.getString("initial_diagnosis"));
                    }
                caseRecord.setId(rs.getInt("id"));
                caseRecord.setDoctor(doctor);
                caseRecord.setPatient(patient);
                caseRecord.setDoctorAppointmentList(doctorAppointmentList);
                nurseCaseRecord.add(caseRecord);
            }
            rs.close();
            rs2.close();
            rs3.close();
            ps.close();
            session.setAttribute("caseRecordList", nurseCaseRecord);
        } catch (SQLException throwables) {
            log.error("command ListGenerationCaseRecord not executed" + con, throwables);
            session.setAttribute("errorMessage",1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.close(con);
        }
        return Constant.URL_CONTROLLER_VIEW_ARCHIVE;
    }

    private void doctorAppointmentCreate(ResultSet rs2, DoctorAppointment da) throws SQLException {
        da.setId(rs2.getInt("id"));
        da.setType(rs2.getString("type"));
        da.setDescription(rs2.getString("description"));
        da.setNameStaffComplete(rs2.getString("name_staff_complete"));
        da.setComplete(rs2.getString("complete"));
    }

    private void createPatient(ResultSet rs2, Patient patient, int patientId) throws SQLException {
        patient.setId(patientId);
        patient.setName(rs2.getString("name"));
        patient.setSurname(rs2.getString("surname"));
        patient.setPassport(rs2.getString("passport"));
        patient.setTelephone(rs2.getString("telephone"));
        CreateElement.getAge(rs2,patient);
    }

    private void createDoctor(ResultSet rs2, Doctor doctor, int doctorId) throws SQLException {
        doctor.setId(doctorId);
        doctor.setName(rs2.getString("name"));
        doctor.setSurname(rs2.getString("surname"));
        doctor.setDepartment(rs2.getString("department"));
        doctor.setPassport(rs2.getString("passport"));
        doctor.setTelephone(rs2.getString("telephone"));
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return execute(req, resp, con);
    }
}
