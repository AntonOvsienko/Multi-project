package com.ua;

import com.ua.command.Command;
import com.ua.command.CommandContainer;
import org.junit.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

import static org.mockito.Mockito.mock;


public class TestAddController {

    private HttpServletRequest req;
    private HttpServletResponse resp;
    private HttpSession session;
    private PreparedStatement ps;
    private ResultSet rs;
    private ResultSet rs1;
    private ResultSet rs2;
    private Connection con;
    private Statement st;
    private Statement newLogin2;
    private PreparedStatement preparedStatement;
    private PreparedStatement preparedStatement1;
    private PreparedStatement preparedStatement2;
    private PreparedStatement preparedStatement3;
    private String role;

    @Before
    public void init() {
        st = Mockito.mock(Statement.class);
        newLogin2 = Mockito.mock(Statement.class);
        rs = Mockito.mock(ResultSet.class);
        rs1 = Mockito.mock(ResultSet.class);
        rs2 = Mockito.mock(ResultSet.class);
        ps = Mockito.mock(PreparedStatement.class);
        preparedStatement = Mockito.mock(PreparedStatement.class);
        preparedStatement1 = Mockito.mock(PreparedStatement.class);
        preparedStatement2 = Mockito.mock(PreparedStatement.class);
        preparedStatement3 = Mockito.mock(PreparedStatement.class);
        resp = Mockito.mock(HttpServletResponse.class);
        req = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
        con = mock(Connection.class);
        Mockito.when((req.getSession())).thenReturn(session);
    }


    @Test
    public void addAppointmentInCaseRecordPatientShouldTrue() throws SQLException {
        Mockito.when(req.getParameter("caseRecordId"))
                .thenReturn("15");
        Mockito.when(req.getParameter("select1"))
                .thenReturn("1");
        Mockito.when(req.getParameter("select2"))
                .thenReturn("2");
        Mockito.when(req.getParameter("select3"))
                .thenReturn("3");
        Mockito.when(req.getParameter("select4"))
                .thenReturn("4");
        Mockito.when(req.getParameter("description1"))
                .thenReturn("text1");
        Mockito.when(req.getParameter("description2"))
                .thenReturn("text2");
        Mockito.when(req.getParameter("description3"))
                .thenReturn("text3");
        Mockito.when(req.getParameter("description4"))
                .thenReturn("text4");
        Mockito.when(con.prepareStatement("SELECT * FROM patient_has_case_records WHERE id=?"))
                .thenReturn(ps);
        Mockito.when(req.getParameter("caseRecordId")).thenReturn("15");
        Mockito.when(ps.executeQuery()).thenReturn(rs);
        Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
        Mockito.when(rs.getInt("case_record_id")).thenReturn(40);
        Mockito.when(con.prepareStatement("INSERT INTO doctor_appointment " +
                "(case_record_id,type,description,complete) VALUES (?,?,?,?)"))
                .thenReturn(ps);
        try (MockedStatic<ConnectionPool> utilMock = Mockito.mockStatic(ConnectionPool.class)) {
            utilMock.when(ConnectionPool::getConnection)
                    .thenReturn(con);

            Command command = CommandContainer.getCommand("addAppointment");
            String address = command.execute(req, resp, con);
            Assert.assertEquals(address, "controller?command=doctorAppointment&caseRecordId=15");
        }
    }

    @Test
    public void addNewLoginInDataBaseIsDoctorShouldTrue() throws SQLException {
        Mockito.when(req.getParameter("login"))
                .thenReturn("admin");
        Mockito.when(req.getParameter("password"))
                .thenReturn("12345");
        Mockito.when(req.getParameter("password_repeat"))
                .thenReturn("12345");
        Mockito.when(session.getAttribute("role"))
                .thenReturn("doctor");
        Mockito.when(con.prepareStatement("INSERT INTO login_password (login,password,role) VALUES (?,?,?)"))
                .thenReturn(preparedStatement1);
        Mockito.when(con.prepareStatement("SELECT * FROM login_password WHERE login=?"))
                .thenReturn(preparedStatement2);
        Mockito.when(preparedStatement2.executeQuery())
                .thenReturn(rs2);
        Mockito.when(rs2.next())
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(rs2.getInt("id"))
                .thenReturn(10);
        Mockito.when(con.prepareStatement("INSERT INTO " + role + " (login_password_id) VALUES (?)"))
                .thenReturn(preparedStatement3);
        Mockito.when(con.prepareStatement("UPDATE doctor SET name=?, surname=?, telephone=?, passport=?" +
        ", department=? WHERE login_password_id=?")).thenReturn(preparedStatement);
        Mockito.when(req.getParameter("name"))
                .thenReturn("Vasya");
        Mockito.when(req.getParameter("surname"))
                .thenReturn("Petrov");
        Mockito.when(req.getParameter("passport"))
                .thenReturn("AH1278394");
        Mockito.when(req.getParameter("telephone"))
                .thenReturn("380507205912");
        Mockito.when(session.getAttribute("idLoginPassword"))
                .thenReturn(10);
        Mockito.when(req.getParameter("department"))
                .thenReturn("pediatr");
        Mockito.when(con.createStatement())
                .thenReturn(newLogin2);
        Mockito.when(newLogin2.executeUpdate("UPDATE doctor SET name=Vasya, surname=Petrov," +
                " telephone=380507205912, passport=AH1278394, department=pediatr" +
                " WHERE login_password_id=10"))
                .thenReturn(5);

        try (MockedStatic<ConnectionPool> utilMock = Mockito.mockStatic(ConnectionPool.class)) {
            utilMock.when(ConnectionPool::getConnection)
                    .thenReturn(con);

            Command command = CommandContainer.getCommand("createNewLogin");
            String address = command.execute(req, resp, con);
            Assert.assertEquals(address, "users/newLogin.jsp");
        }
    }

    @Test
    public void addNewPatientInDataBaseShouldTrue() throws SQLException {
        Mockito.when(req.getParameter("name"))
                .thenReturn("Vasya");
        Mockito.when(req.getParameter("surname"))
                .thenReturn("Petrov");
        Mockito.when(req.getParameter("passport"))
                .thenReturn("AH1278394");
        Mockito.when(req.getParameter("telephone"))
                .thenReturn("380507205912");
        Mockito.when(req.getParameter("date"))
                .thenReturn("1995-03-12");
        Mockito.when(req.getParameter("diagnosis2")).thenReturn("");
        Mockito.when(req.getParameter("diagnosis3")).thenReturn("");
        Mockito.when(req.getParameter("diagnosis4")).thenReturn("");
        Mockito.when(req.getParameter("selectDoctor1")).thenReturn("3");
        Mockito.when(req.getParameter("selectDoctor2")).thenReturn(null);
        Mockito.when(req.getParameter("selectDoctor3")).thenReturn(null);
        Mockito.when(req.getParameter("selectDoctor4")).thenReturn(null);
        Mockito.when(con.prepareStatement("INSERT INTO patient (name,surname,passport,telephone,birthday) " +
                "VALUES (?,?,?,?,?)"))
                .thenReturn(ps);
        Mockito.when(con.prepareStatement("INSERT INTO patient_has_case_records " +
                "(patient_id,case_record_id,doctor_id) VALUES (?,?,?)"))
                .thenReturn(ps);
        Mockito.when(ps.executeUpdate())
                .thenReturn(5)
                .thenReturn(2);
        Mockito.when(con.createStatement())
                .thenReturn(st);
        Mockito.when(st.executeQuery("SELECT MAX(id) FROM patient"))
                .thenReturn(rs);
        Mockito.when(st.executeQuery("SELECT MAX(id) FROM case_record"))
                .thenReturn(rs);
        Mockito.when(rs.next())
                .thenReturn(true)
                .thenReturn(false)
                .thenReturn(true)
                .thenReturn(false);
        Mockito.when(rs.getInt(1))
                .thenReturn(10);
        Mockito.when(con.prepareStatement("INSERT INTO case_record (initial_diagnosis) VALUES (?)"))
                .thenReturn(ps);
        Mockito.when(ps.executeUpdate())
                .thenReturn(1);

        try (MockedStatic<ConnectionPool> utilMock = Mockito.mockStatic(ConnectionPool.class)) {
            utilMock.when(ConnectionPool::getConnection)
                    .thenReturn(con);

            Command command = CommandContainer.getCommand("addNewPatient");
            String address = command.execute(req, resp, con);
            Assert.assertEquals(address, "users/newPatient.jsp");
        }
    }

    @After
    public void destroy() throws SQLException {
        con.close();
    }
}
