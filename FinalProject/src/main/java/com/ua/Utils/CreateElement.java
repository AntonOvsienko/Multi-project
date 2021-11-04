package com.ua.Utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import com.ua.ConnectionPool;
import com.ua.entity.*;

import java.sql.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateElement {

    public static Staff newElement(ResultSet rs, String role, Connection con) throws SQLException {
        Staff user = null;
        if (role.equals("doctor")) {
            user = new Doctor();
            if (rs.getString("department") != null) {
                user.setDepartment(rs.getString("department"));
            }
            getLoginPassword(rs, con, user);
            getStandartFields(rs, user);
            getCaseRecordDoctor(con, user, user.getId());
        }
        if (role.equals("patient")) {
            user = new Patient();
            getAge(rs, (Patient) user);
            getStandartFields(rs, user);
            getCaseRecordPatient(con, user, user.getId());
        }
        if (role.equals("nurse")) {
            user = new Nurse();
            getLoginPassword(rs, con, user);
            getStandartFields(rs, user);
        }
        return user;
    }

    private static void getCaseRecordPatient(Connection con, Staff user, int patient_id) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        List<CaseRecord> caseRecords = new ArrayList<>();
        ps = con.prepareStatement(Constant.SQL_SELECT_PATIENT_HAS_CASERECORDS_WHERE_PATIENT_ID);
        ps.setInt(1, patient_id);
        rs1 = ps.executeQuery();
        int id = 0;
        int doctor_id = 0;
        int case_record_id = 0;
        while (rs1.next()) {
            id = rs1.getInt("id");
            Staff doctor = new Doctor();
            if (rs1.getInt("doctor_id") != 0) {
                doctor_id = rs1.getInt("doctor_id");
                ps = con.prepareStatement(Constant.SQL_SELECT_DOCTOR_WHERE_ID);
                ps.setInt(1, doctor_id);
                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    int idoctor = rs2.getInt(1);
                    String name = rs2.getString(2);
                    String surname = rs2.getString(3);
                    String department = rs2.getString(5);
                    String passport = rs2.getString(6);
                    String telephone = rs2.getString(7);
                    doctor.setId(idoctor);
                    doctor.setName(name);
                    doctor.setSurname(surname);
                    doctor.setDepartment(department);
                    doctor.setPassport(passport);
                    doctor.setTelephone(telephone);
                }
            }
            Staff patient = new Patient();
            ps = con.prepareStatement(Constant.SQL_SELECT_PATIENT_WHERE_ID);
            ps.setInt(1, patient_id);
            rs2 = ps.executeQuery();
            while (rs2.next()) {
                String name = rs2.getString("name");
                String surname = rs2.getString("surname");
                String telephone = rs2.getString("telephone");
                String passport = rs2.getString("passport");
                patient.setId(rs2.getInt("id"));
                patient.setName(name);
                patient.setSurname(surname);
                patient.setTelephone(telephone);
                patient.setPassport(passport);
            }
            case_record_id = rs1.getInt("case_record_id");
            String initialDiagnosis = "";
            ps = con.prepareStatement(Constant.SQL_SELECT_CASERECORD_WHERE_ID);
            ps.setInt(1, case_record_id);
            rs2 = ps.executeQuery();
            while (rs2.next()) {
                initialDiagnosis = rs2.getString("initial_diagnosis");
            }
            caseRecords.add(new CaseRecord(id, doctor, patient, initialDiagnosis));
        }
        user.setCaseRecords(caseRecords);

    }

    private static void getCaseRecordDoctor(Connection con, Staff user, int doctorId) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        List<CaseRecord> caseRecords = new ArrayList<>();

        ps = con.prepareStatement(Constant.SQL_SELECT_PATIENT_HAS_CASERECORDS_WHERE_DOCTOR_ID);
        ps.setInt(1, doctorId);
        rs1 = ps.executeQuery();
        int id = 0;
        int patient_id = 0;
        int case_record_id = 0;
        while (rs1.next()) {
            Staff patient = new Patient();
            id = rs1.getInt("id");
            patient_id = rs1.getInt("patient_id");
            ps = con.prepareStatement(Constant.SQL_SELECT_PATIENT_WHERE_ID);
            ps.setInt(1, patient_id);
            rs2 = ps.executeQuery();
            while (rs2.next()) {
                String name = rs2.getString("name");
                String surname = rs2.getString("surname");
                String passport = rs2.getString("passport");
                String telephone = rs2.getString("telephone");
                patient = new Patient(patient_id, name, surname, passport, telephone);
            }
            Staff doctor = new Doctor();
            ps = con.prepareStatement(Constant.SQL_SELECT_DOCTOR_WHERE_ID);
            ps.setInt(1, doctorId);
            rs2 = ps.executeQuery();
            while (rs2.next()) {
                String name = rs2.getString("name");
                String surname = rs2.getString("surname");
                String department = rs2.getString("department");
                String passport = rs2.getString("passport");
                String telephone = rs2.getString("telephone");
                doctor = new Doctor(doctorId, name, surname, department, passport, telephone);
            }
            case_record_id = rs1.getInt("case_record_id");
            String initialDiagnosis = "";
            ps = con.prepareStatement(Constant.SQL_SELECT_CASERECORD_WHERE_ID);
            ps.setInt(1, case_record_id);
            rs2 = ps.executeQuery();
            while (rs2.next()) {
                initialDiagnosis = rs2.getString("initial_diagnosis");
            }
            caseRecords.add(new CaseRecord(id, doctor, patient, initialDiagnosis));
        }
        user.setCaseRecords(caseRecords);
    }

    public static void getAge(ResultSet rs, Patient user) throws SQLException {

        String date = rs.getDate("birthday").toString();
        String dateParser = "([0-9]+)-([0-9]+)-([0-9]+)";
        Pattern pattern = Pattern.compile(dateParser);
        Matcher matcher = pattern.matcher(date);
        while (matcher.find()) {
            user.setYearBorn(Integer.parseInt(matcher.group(1)));
            user.setMonthBorn(Integer.parseInt(matcher.group(2)));
            user.setDayBorn(Integer.parseInt(matcher.group(3)));
        }

        LocalDate start = LocalDate.of(user.getYearBorn(), user.getMonthBorn(), user.getDayBorn());
        LocalDate end = LocalDate.now();
        long years = ChronoUnit.YEARS.between(start, end);
        user.setYears(years);

    }

    private static void getStandartFields(ResultSet rs, Staff staff) throws SQLException {

        staff.setId(rs.getInt("id"));

        if (rs.getString("name") != null) {
            staff.setName(rs.getString("name"));
        }
        if (rs.getString("surname") != null) {
            staff.setSurname(rs.getString("surname"));
        }
        if (rs.getString("telephone") != null) {
            staff.setTelephone(rs.getString("telephone"));
        }
        if (rs.getString("passport") != null) {
            staff.setPassport(rs.getString("passport"));
        }
    }

    public static void getLoginPassword(ResultSet rs, Connection con, Staff user) throws SQLException {

        if (rs.getString("login_password_id") != null) {
            int login_password_id = rs.getInt("login_password_id");
            PreparedStatement ps = con.prepareStatement(Constant.SQL_SELECT_LOGIN_PASSWORD_WHERE_ID);
            ps.setInt(1, login_password_id);
            ResultSet rs2 = ps.executeQuery();
            while (rs2.next()) {
                if (rs2.getString("role") != null) {
                    user.setRole(rs2.getString("role"));
                }
                if (rs2.getString("login") != null) {
                    user.setLogin(rs2.getString("login"));
                }
                if (rs2.getString("password") != null) {
                    user.setPassword(rs2.getString("password"));
                }
            }
        }
    }
}
