package com.ua.command.update;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component("updatePatient")
@Scope("prototype")
public class UpdatePatientCommand implements Command {

    private static final Logger log = LogManager.getLogger(UpdatePatientCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) {
        //patient profile update
        HttpSession session = req.getSession();

        try {
            if (session.getAttribute("changeProfile") == null) {
                session.setAttribute("changeProfile", "true");
                session.setAttribute("successfully", "");
                session.setAttribute("error", "");
                return Constant.URL_UPDATE_PATIENT;
            }
            session.setAttribute("successfully", "");
            con.setAutoCommit(false);
            updatePatient(con, req);
            con.commit();
        } catch (SQLException throwables) {
            CloseLink.rollback(con);
            log.error("command UpdatePatient not executed" + con, throwables);
            session.setAttribute("errorMessage", 1);
            return Constant.URL_ERROR_PAGE;
        } finally {
            CloseLink.setAutoCommitOn(con);
            CloseLink.close(con);
        }
        session.setAttribute("successfully", "true");
        session.setAttribute("messageFalse", "0");
        session.setAttribute("changeProfile", "false");
        return Constant.URL_UPDATE_PATIENT;
    }

    private void updatePatient(Connection con, HttpServletRequest req) throws SQLException {
        HttpSession session = req.getSession();
        int caseId1 = 0;
        int caseId2 = 0;
        int caseId3 = 0;
        int caseId4 = 0;
        int selectDoctor1 = 0;
        int selectDoctor2 = 0;
        int selectDoctor3 = 0;
        int selectDoctor4 = 0;
        if (req.getParameter("caseRecordId1") != null) {
            caseId1 = Integer.parseInt(req.getParameter("caseRecordId1"));
            selectDoctor1 = Integer.parseInt(req.getParameter("selectDoctor1"));
        }
        if (req.getParameter("caseRecordId2") != null) {
            caseId2 = Integer.parseInt(req.getParameter("caseRecordId2"));
            selectDoctor2 = Integer.parseInt(req.getParameter("selectDoctor2"));
        }
        if (req.getParameter("caseRecordId3") != null) {
            caseId3 = Integer.parseInt(req.getParameter("caseRecordId3"));
            selectDoctor3 = Integer.parseInt(req.getParameter("selectDoctor3"));
        }
        if (req.getParameter("caseRecordId4") != null) {
            caseId4 = Integer.parseInt(req.getParameter("caseRecordId4"));
            selectDoctor4 = Integer.parseInt(req.getParameter("selectDoctor4"));
        }
        int[] caseId = {caseId1, caseId2, caseId3, caseId4};
        int[] selectDoctor = {selectDoctor1, selectDoctor2, selectDoctor3, selectDoctor4};
        PreparedStatement preparedStatement = null;
        ResultSet st;
        int k;
        for (int i = 0; i < caseId.length; i++) {
            preparedStatement = con.prepareStatement(Constant.SQL_UPDATE_PATIENT);
            k = 1;
            System.out.println("selectDoctor => " + selectDoctor[i]);
            System.out.println("caseId => " + caseId[i]);
            preparedStatement.setInt(k++, selectDoctor[i]);
            preparedStatement.setInt(k++, caseId[i]);
            preparedStatement.executeUpdate();
        }
        preparedStatement = con.prepareStatement(Constant.SQL_NEW_LOGIN_UPDATE_PATIENT);
        k = 1;
        System.out.println("name => " + req.getParameter("name"));
        System.out.println("surname => " + req.getParameter("surname"));
        System.out.println("telephone => " + req.getParameter("telephone"));
        System.out.println("passport => " + req.getParameter("passport"));
        preparedStatement.setString(k++, req.getParameter("name"));
        preparedStatement.setString(k++, req.getParameter("surname"));
        preparedStatement.setString(k++, req.getParameter("telephone"));
        preparedStatement.setString(k++, req.getParameter("passport"));
        preparedStatement.setInt(k++, Integer.parseInt((String)session.getAttribute("loginId")));
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        return Constant.URL_UPDATE_DOCTOR;
    }
}

