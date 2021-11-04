package com.ua.command.get;

import com.ua.ConnectionPool;
import com.ua.Utils.CloseLink;
import com.ua.Utils.Constant;
import com.ua.Utils.CreateElement;
import com.ua.command.Command;
import com.ua.entity.*;
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

@Component("archivePatient")
@Scope("prototype")
public class ListGenerationArchiveCommand implements Command {

    private static final Logger log= LogManager.getLogger(ListGenerationArchiveCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) throws SQLException {
        //Returns lists of archives
        HttpSession session = req.getSession();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Patient> archivePatient = new ArrayList<>();
        try {
            ps = con.prepareStatement(Constant.SQL_SELECT_ARCHIVE);
            rs = ps.executeQuery();
            while (rs.next()) {
                int idPatient=rs.getInt("id");
                List<CaseRecord> caseRecords=new ArrayList<>();
                Patient patient = new Patient();
                patient.setId(idPatient);
                patient.setName(rs.getString("name"));
                patient.setSurname(rs.getString("surname"));
                patient.setPassport(rs.getString("passport"));
                patient.setTelephone(rs.getString("telephone"));
                CreateElement.getAge(rs,patient);
                getCaseRecord(con, idPatient, caseRecords, patient);
                archivePatient.add(patient);
            }
            session.setAttribute("archivePatient", archivePatient);
        } catch (SQLException throwables) {
            log.error("command ListGenerationArchive not executed" + con, throwables);
            session.setAttribute("errorMessage",1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.close(con);
        }
        return (String) session.getAttribute("finalAddress");
    }

    private void getCaseRecord(Connection con, int idPatient, List<CaseRecord> caseRecords, Patient patient) throws SQLException {
        PreparedStatement ps;
        ResultSet rs2;
        ps= con.prepareStatement(Constant.SQL_SELECT_CASE_RECORD_ARCHIVE_WHERE_ARCHIVE_ID);
        ps.setInt(1, idPatient);
        rs2= ps.executeQuery();
        while (rs2.next()){
            CaseRecord caseRecord=new CaseRecord();
            List<DoctorAppointment> doctorAppointments=new ArrayList<>();
            Doctor doctor=new Doctor();
            int idCaseRecord=rs2.getInt("id");
            int idDoctor=rs2.getInt("doctor_id");
            caseRecord.setId(idCaseRecord);
            caseRecord.setInitialDiagnosis(rs2.getString("initial_diagnosis"));
            caseRecord.setFinalDiagnosis(rs2.getString("final_diagnosis"));
            getDoctorAppointment(con, caseRecord, doctorAppointments, idCaseRecord);
            getDoctor(con, caseRecord, doctor, idDoctor);
            caseRecords.add(caseRecord);
        }
        patient.setCaseRecords(caseRecords);
    }

    private void getDoctor(Connection con, CaseRecord caseRecord, Doctor doctor, int idDoctor) throws SQLException {
        PreparedStatement ps;
        ResultSet rs3;
        ps= con.prepareStatement(Constant.SQL_SELECT_DOCTOR_ARCHIVE);
        ps.setInt(1, idDoctor);
        rs3=ps.executeQuery();
        while (rs3.next()){
           doctor.setName(rs3.getString("name"));
           doctor.setSurname(rs3.getString("surname"));
           doctor.setPassport(rs3.getString("passport"));
           doctor.setTelephone(rs3.getString("telephone"));
        }
        caseRecord.setDoctor(doctor);
    }

    private void getDoctorAppointment(Connection con, CaseRecord caseRecord, List<DoctorAppointment> doctorAppointments, int idCaseRecord) throws SQLException {
        PreparedStatement ps;
        ResultSet rs3;
        ps= con.prepareStatement(
                Constant.SQL_SELECT_DOCTOR_APPOINTMENT_ARCHIVE_WHERE_ARCHIVE_ID);
        ps.setInt(1, idCaseRecord);
        rs3=ps.executeQuery();
        while (rs3.next()){
            DoctorAppointment doctorAppointment=new DoctorAppointment();
            doctorAppointment.setType(rs3.getString("type"));
            doctorAppointment.setDescription(rs3.getString("description"));
            doctorAppointment.setNameStaffComplete(rs3.getString("name_staff_complete"));
            doctorAppointments.add(doctorAppointment);
        }
        caseRecord.setDoctorAppointmentList(doctorAppointments);
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        return execute(req,resp,ConnectionPool.getConnection());
    }
}
