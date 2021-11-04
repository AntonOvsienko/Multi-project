package com.ua.command.update;

import com.ua.Utils.CloseLink;
import com.ua.Utils.Constant;
import com.ua.command.Command;
import com.ua.entity.CaseRecord;
import com.ua.entity.DoctorAppointment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

@Component("dischargedHospital")
@Scope("prototype")
public class DischargedHospitalCommand implements Command {

    private static final Logger log = LogManager.getLogger(DischargedHospitalCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) {
        //moving the case history to the archive and discharging the patient
        PreparedStatement ps = null;
        ResultSet rs = null;
        HttpSession session = req.getSession();
        try {
            con.setAutoCommit(false);
            int caseRecordId = Integer.parseInt(req.getParameter("caseRecordId"));
            System.out.println("caseRecordId => " + caseRecordId);
            String finalDiagnosis = req.getParameter("finalDiagnosis");
            List<CaseRecord> caseRecordList = (List<CaseRecord>) session.getAttribute("caseRecordList");
            caseRecordList = caseRecordList.stream().filter(x -> x.getId() == caseRecordId).collect(Collectors.toList());
            String birthday = caseRecordList.get(0).getPatient().getYearBorn() + "-" +
                    caseRecordList.get(0).getPatient().getMonthBorn() + "-" +
                    caseRecordList.get(0).getPatient().getDayBorn();

            addDoctor(con, session, caseRecordList);

            ps = con.prepareStatement(Constant.SQL_SELECT_ARCHIVE_WHERE_PASSPORT);
            int k = 1;
            ps.setString(k, caseRecordList.get(0).getPatient().getPassport());
            rs = ps.executeQuery();
            while (rs.next()) {
                int archiveId = rs.getInt(1);
                updateCase(con, session, finalDiagnosis, caseRecordList, archiveId, caseRecordId);
                con.commit();
                return Constant.URL_CONTROLLER_VIEW_CASERECORD;
            }
            createNewRecord(con, session, finalDiagnosis, caseRecordList, birthday, caseRecordId);
            con.commit();
            ps.close();
            rs.close();
        } catch (SQLException throwables) {
            CloseLink.rollback(con);
            log.error("command DischargedHospital not executed" + con, throwables);
            session.setAttribute("errorMessage", 1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.setAutoCommitOn(con);
            CloseLink.close(con);
        }
        return Constant.URL_CONTROLLER_VIEW_CASERECORD;

    }

    private void addDoctor(Connection con, HttpSession session, List<CaseRecord> caseRecordList) throws SQLException {
        ResultSet rs2;
        PreparedStatement ps;
        int k;
        ResultSet rs;
        int keyLogin = (Integer) session.getAttribute("keyLogin");
        ps = con.prepareStatement(Constant.SQL_SELECT_DOCTOR_ARCHIVE);
        k = 1;
        ps.setInt(k++, keyLogin);
        rs2 = ps.executeQuery();
        while (rs2.next()) {
            return;
        }
        ps = con.prepareStatement(Constant.SQL_NEW_DOCTOR_ARCHIVE);
        k = 1;
        ps.setInt(k++, keyLogin);
        ps.setString(k++, caseRecordList.get(0).getDoctor().getName());
        ps.setString(k++, caseRecordList.get(0).getDoctor().getSurname());
        ps.setString(k++, caseRecordList.get(0).getDoctor().getPassport());
        ps.setString(k++, caseRecordList.get(0).getDoctor().getTelephone());
        ps.executeUpdate();
    }

    private void createNewRecord(Connection con, HttpSession session, String finalDiagnosis, List<CaseRecord> caseRecordList, String birthday, int caseRecordId) throws SQLException {
        int k;
        PreparedStatement ps;
        ResultSet rs;
        ps = con.prepareStatement(Constant.SQL_NEW_ARCHIVE_ADD_NEWRECORD);
        k = 1;
        ps.setString(k++, caseRecordList.get(0).getPatient().getName());
        ps.setString(k++, caseRecordList.get(0).getPatient().getSurname());
        ps.setString(k++, caseRecordList.get(0).getPatient().getPassport());
        ps.setString(k++, caseRecordList.get(0).getPatient().getTelephone());
        ps.setString(k++, birthday);
        ps.executeUpdate();
        ps = con.prepareStatement(Constant.SQL_MAX_ID_ARCHIVE);
        rs = ps.executeQuery();
        int archiveId = 0;
        while (rs.next()) {
            archiveId = rs.getInt(1);
        }
        con.commit();
        updateCase(con, session, finalDiagnosis, caseRecordList, archiveId, caseRecordId);

    }

    private void updateCase(Connection con, HttpSession session, String finalDiagnosis, List<CaseRecord> caseRecordList, int archiveId, int caseRecordId) throws SQLException {
        int k;
        int keyLogin = (Integer) session.getAttribute("keyLogin");
        System.out.println(keyLogin);
        PreparedStatement ps;
        ResultSet rs;
        ps = con.prepareStatement(Constant.SQL_NEW_CASE_RECORD_ADD);
        k = 1;
        ps.setInt(k++, archiveId);
        ps.setString(k++, caseRecordList.get(0).getInitialDiagnosis());
        ps.setString(k++, finalDiagnosis);
        ps.setInt(k++,keyLogin);
        ps.executeUpdate();
        ps = con.prepareStatement(Constant.SQL_MAX_ID_CASE_RECORD_ARCHIVE);
        rs = ps.executeQuery();
        int caseRecordArchiveId = 0;
        while (rs.next()) {
            caseRecordArchiveId = rs.getInt(1);
            session.setAttribute(" caseRecordArchiveId", caseRecordArchiveId);
        }
        for (DoctorAppointment da : caseRecordList.get(0).getDoctorAppointmentList()) {
            ps = con.prepareStatement(Constant.SQL_NEW_DOCTOR_APPOINTMENT_ADD);
            k = 1;
            ps.setInt(k++, caseRecordArchiveId);
            ps.setString(k++, da.getType());
            ps.setString(k++, da.getDescription());
            ps.setString(k++, da.getNameStaffComplete());
            ps.executeUpdate();
            session.setAttribute("finalAddress", "users/doctor.jsp");
        }
        ps = con.prepareStatement(Constant.SQL_PATIENT_CASE_RECORD_DELETE);
        k = 1;
        ps.setInt(k++, caseRecordId);
        ps.executeUpdate();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        return null;
    }
}
