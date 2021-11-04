package com.ua.command.add;

import com.ua.ConnectionPool;
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
import java.util.ArrayList;
import java.util.List;

@Component("addNewPatient")
@Scope("prototype")
public class AddNewPatientCommand implements Command {

    private static final Logger log= LogManager.getLogger(AddNewPatientCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con){
        //add new Patient
        HttpSession session = req.getSession();
        PreparedStatement ps = null;
        Statement st = null;
        try {
            con.setAutoCommit(false);
            addAnketData(req, ConnectionPool.getConnection());
            String lastPatientId = Constant.SQL_MAX_ID_PATIENT;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(lastPatientId);
            int patientId = 0;
            while (rs.next()) {
                patientId = rs.getInt(1);
            }
            System.out.println("patientId => " + patientId);
            List<String> massivDiagnoses = createDiagnosList(req);
            List<Integer> massivDoctor = createDoctorList(req);
            for (int i = 0; i < massivDiagnoses.size(); i++) {
                addDiagnoses(con, patientId, massivDiagnoses.get(i),massivDoctor.get(i));
            }
            con.commit();
        } catch (SQLException throwables) {
            CloseLink.rollback(con);
            log.error("command AddNewPatient not executed" + con, throwables);
            session.setAttribute("errorMessage",1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.setAutoCommitOn(con);
            CloseLink.close(con);
        }
        return Constant.URL_NEW_PATIENT;
    }

    private List<String> createDiagnosList(HttpServletRequest req) {
        List<String> list = new ArrayList<>();
        String initDiagnosis1 = req.getParameter("diagnosis1");
        list.add(initDiagnosis1);
        String initDiagnosis2 = null;
        if (!req.getParameter("diagnosis2").equals("")&&req.getParameter("selectDoctor2") != null) {
            initDiagnosis2 = req.getParameter("diagnosis2");
            list.add(initDiagnosis2);
        }
        String initDiagnosis3 = null;
        if (!req.getParameter("diagnosis3").equals("")&&req.getParameter("selectDoctor3") != null) {
            initDiagnosis3 = req.getParameter("diagnosis3");
            list.add(initDiagnosis3);
        }
        String initDiagnosis4 = null;
        if (!req.getParameter("diagnosis4").equals("")&&req.getParameter("selectDoctor4") != null) {
            initDiagnosis4 = req.getParameter("diagnosis4");
            list.add(initDiagnosis4);
        }
        return list;
    }

    private List<Integer> createDoctorList(HttpServletRequest req) {
        List<Integer> list = new ArrayList<>();
        int doctor1=Integer.parseInt(req.getParameter("selectDoctor1"));
        list.add(doctor1);
        int doctor2=0;
        if (req.getParameter("selectDoctor2") != null&&!req.getParameter("diagnosis2").equals("")) {
            doctor2=Integer.parseInt(req.getParameter("selectDoctor2"));
            list.add(doctor2);
        }
        int doctor3=0;
        if (req.getParameter("selectDoctor3") != null&&!req.getParameter("diagnosis3").equals("")) {
            doctor3=Integer.parseInt(req.getParameter("selectDoctor3"));
            list.add(doctor3);
        }
        int doctor4=0;
        if (req.getParameter("selectDoctor4") != null&&!req.getParameter("diagnosis4").equals("")) {
            doctor4=Integer.parseInt(req.getParameter("selectDoctor4"));
            list.add(doctor4);
        }
        return list;
    }

    private void addDiagnoses(Connection con, int patientId, String diagnosis,int doctorId) throws SQLException {
        PreparedStatement ps;
        Statement st = null;
        ResultSet rs;
        ps = con.prepareStatement(Constant.SQL_NEW_PATIENT_ADD_DIAGNOSIS);
        ps.setString(1,diagnosis);
        ps.executeUpdate();
        st = con.createStatement();
        rs = st.executeQuery(Constant.SQL_MAX_ID_CASERECORD);
        int caseId = 0;
        while (rs.next()) {
            caseId = rs.getInt(1);
        }
        System.out.println("caseId => " + caseId);
        ps = con.prepareStatement(Constant.SQL_NEW_PATIENT_ADD_PATIENT_CASERECORD);
        ps.setInt(1, patientId);
        ps.setInt(2, caseId);
        ps.setInt(3,doctorId);
        ps.executeUpdate();
    }

    private void addAnketData(HttpServletRequest req, Connection con) throws SQLException {
        PreparedStatement ps;
        String nameSet = req.getParameter("name");
        String surnameSet = req.getParameter("surname");
        String passportSet = req.getParameter("passport");
        String telephoneSet = req.getParameter("telephone");
        String dataBirthdaySet = req.getParameter("date");
        ps = con.prepareStatement(Constant.SQL_NEW_PATIENT_ADD_ANKETA);
        ps.setString(1, nameSet);
        ps.setString(2, surnameSet);
        ps.setString(3, passportSet);
        ps.setString(4, telephoneSet);
        ps.setString(5, dataBirthdaySet);
        ps.executeUpdate();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return Constant.URL_NEW_PATIENT;
    }
}
