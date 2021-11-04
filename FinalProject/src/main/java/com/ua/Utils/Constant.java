package com.ua.Utils;

public class Constant {

    public static final String URL_ADD_APPOINTMENT = "controller?command=doctorAppointment&caseRecordId=";
    public static final String URL_ERROR_PAGE = "errorMessage/error.jsp";
    public static final String URL_ERROR_PAGE_REPEAT_PASSWORD="errorMessage/errorRepeatPassword.jsp";
    public static final String URL_NEW_LOGIN = "users/newLogin.jsp";
    public static final String URL_NEW_PATIENT = "users/newPatient.jsp";
    public static final String URL_UPDATE_DOCTOR = "users/anketaDoctor.jsp";
    public static final String URL_UPDATE_NURSE = "users/anketaNurse.jsp";
    public static final String URL_UPDATE_PATIENT = "users/anketaPatient.jsp";
    public static final String URL_LIST_ARCHIVE = "users/doctorAppointmentArchive.jsp";


    public static final String URL_CONTROLLER_VIEW_NURSE = "controller?command=viewNurse";
    public static final String URL_CONTROLLER_VIEW_CASERECORD = "controller?command=viewCaseRecord";
    public static final String URL_CONTROLLER_VIEW_ARCHIVE = "controller?command=archivePatient";
    public static final String URL_CONTROLLER_VIEW_PATIENT = "controller?command=viewPatient";
    public static final String URL_CONTROLLER_VIEW_STAFF = "controller?command=viewStaff";


    public static final String SQL_APPOINTMENT_SELECT = "SELECT * FROM patient_has_case_records WHERE id=?";
    public static final String SQL_SELECT_LOGIN_PASSWORD_WHERE_LOGIN = "SELECT * FROM login_password WHERE login=?";
    public static final String SQL_SELECT_LOGIN_PASSWORD_WHERE_ID = "SELECT * FROM login_password WHERE id=?";
    public static final String SQL_SELECT_LOGIN_PASSWORD_WHERE_LOGIN_AND_PASSWORD = "SELECT * FROM login_password " +
            "WHERE login=? AND password=?";
    public static final String SQL_SELECT_DEPARTMENT = "SELECT * FROM department";
    public static final String SQL_SELECT_DOCTOR = "SELECT * FROM doctor";
    public static final String SQL_SELECT_DOCTOR_WHERE_ID = "SELECT * FROM doctor WHERE id=?";
    public static final String SQL_SELECT_DOCTOR_WHERE_LOGIN_PASSWORD_ID = "SELECT * FROM doctor WHERE login_password_id=?";
    public static final String SQL_SELECT_DOCTOR_APPOINTMENT_WHERE_CASERECORDS_ID = "SELECT * FROM doctor_appointment" +
            " WHERE case_record_id=?";
    public static final String SQL_SELECT_PATIENT = "SELECT * FROM patient";
    public static final String SQL_SELECT_PATIENT_WHERE_ID = "SELECT * FROM patient WHERE id=?";
    public static final String SQL_SELECT_PATIENT_HAS_CASERECORDS = "SELECT * FROM patient_has_case_records";
    public static final String SQL_SELECT_PATIENT_HAS_CASERECORDS_WHERE_PATIENT_ID =
            "SELECT * FROM patient_has_case_records WHERE patient_id=?";
    public static final String SQL_SELECT_PATIENT_HAS_CASERECORDS_WHERE_DOCTOR_ID =
            "SELECT * FROM patient_has_case_records WHERE doctor_id=?";
    public static final String SQL_SELECT_CASERECORD_WHERE_ID = "SELECT * FROM case_record WHERE id=?";
    public static final String SQL_SELECT_NURSE = "SELECT * FROM nurse";
    public static final String SQL_SELECT_NURSE_WHERE_LOGIN_PASSWORD = "SELECT * FROM nurse WHERE login_password_id=?";
    public static final String SQL_SELECT_ARCHIVE_WHERE_PASSPORT="SELECT * FROM archive WHERE passport=?";
    public static final String SQL_SELECT_CASE_RECORD_ARCHIVE_WHERE_ARCHIVE_ID="SELECT * FROM case_record_archive WHERE archive_id=?";
    public static final String SQL_SELECT_DOCTOR_APPOINTMENT_ARCHIVE_WHERE_ARCHIVE_ID="SELECT * FROM doctor_appointment_archive WHERE case_record_archive_id=?";
    public static final String SQL_SELECT_ARCHIVE="SELECT * FROM archive";
    public static final String SQL_SELECT_DOCTOR_ARCHIVE="SELECT * FROM doctor_archive WHERE id=?";
    public static final String SQL_MAX_ID_PATIENT = "SELECT MAX(id) FROM patient";
    public static final String SQL_MAX_ID_CASERECORD = "SELECT MAX(id) FROM case_record";
    public static final String SQL_MAX_ID_ARCHIVE="SELECT MAX(id) FROM archive";
    public static final String SQL_MAX_ID_CASE_RECORD_ARCHIVE="SELECT MAX(id) FROM case_record_archive";


    public static final String SQL_APPOINTMENT_INSERT = "INSERT INTO doctor_appointment " +
            "(case_record_id,type,description,complete) VALUES (?,?,?,?)";
    public static final String SQL_NEW_LOGIN_INSERT_LOGIN = "INSERT INTO login_password (login,password,role)" +
            " VALUES (?,?,?)";
    public static final String SQL_NEW_PATIENT_ADD_DIAGNOSIS = "INSERT INTO case_record (initial_diagnosis) VALUES (?)";
    public static final String SQL_NEW_PATIENT_ADD_PATIENT_CASERECORD =
            "INSERT INTO patient_has_case_records (patient_id,case_record_id,doctor_id) VALUES (?,?,?)";
    public static final String SQL_NEW_PATIENT_ADD_ANKETA =
            "INSERT INTO patient (name,surname,passport,telephone,birthday) VALUES (?,?,?,?,?)";
    public static final String SQL_NEW_ARCHIVE_ADD_NEWRECORD=
            "INSERT INTO archive (name,surname,passport,telephone,birthday) VALUES (?,?,?,?,?)";
    public static final String SQL_NEW_CASE_RECORD_ADD =
            "INSERT INTO case_record_archive (archive_id,initial_diagnosis,final_diagnosis,doctor_id) VALUES (?,?,?,?)";
    public static final String SQL_NEW_DOCTOR_APPOINTMENT_ADD=
            "INSERT INTO doctor_appointment_archive (case_record_archive_id,type,description,name_staff_complete) VALUES (?,?,?,?)";
    public static final String SQL_NEW_DOCTOR_ARCHIVE="INSERT INTO doctor_archive (id,name,surname,passport,telephone) " +
            "VALUES (?,?,?,?,?)";


    public static final String SQL_DOCTOR_APPOINTMENT_DELETE = "DELETE FROM doctor_appointment WHERE id=?";
    public static final String SQL_LOGIN_PASSWORD_DELETE = "DELETE FROM login_password WHERE id=?";
    public static final String SQL_LOGIN_PASSWORD_DELETE_WHERE_LOGIN = "DELETE FROM login_password WHERE login=?";
    public static final String SQL_PATIENT_CASE_RECORD_DELETE="DELETE FROM patient_has_case_records WHERE id=?";


    public static final String SQL_NEW_LOGIN_UPDATE_DOCTOR = "UPDATE doctor SET name=?, surname=?, telephone=?, passport=?" +
            ", department=? WHERE login_password_id=?";
    public static final String SQL_NEW_LOGIN_PASSWORD_UPDATE = "UPDATE login_password SET password=? WHERE login=?";
    public static final String SQL_UPDATE_LOGIN_UPDATE_DOCTOR = "UPDATE doctor SET name=?, surname=?, telephone=?, passport=?" +
            ", department=?, password=? WHERE login_password_id=?";
    public static final String SQL_NEW_LOGIN_UPDATE_NURSE = "UPDATE nurse SET name=?, surname=?, telephone=?, passport=?" +
            " WHERE login_password_id=?";
    public static final String SQL_NEW_LOGIN_UPDATE_PATIENT = "UPDATE patient SET name=?, surname=?, telephone=?, passport=?" +
            " WHERE id=?";
    public static final String SQL_UPDATE_DOCTOR_APPOINTMENT = "UPDATE doctor_appointment SET complete='true'," +
            " name_staff_complete=? WHERE id=?";
    public static final String SQL_UPDATE_PATIENT = "UPDATE patient_has_case_records SET doctor_id=? " +
            "WHERE id=?";
    public static final String SQL_UPDATE_PATIENT_CASERECORDS = "UPDATE patient_has_case_records SET doctor_id=? WHERE id=?";
    public static final String SQL_UPDATE_CASERECORD_ID="UPDATE case_record_archive SET doctor_id=? WHERE id=?";

}


