package com.ua.command;

import com.ua.ConnectionPool;
import com.ua.Utils.CloseLink;
import com.ua.Utils.Constant;
import com.ua.command.update.UpdatePatientCommand;
import com.ua.entity.DoctorAppointment;
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

@Component("archiveAppointment")
@Scope("prototype")
public class ListArchiveAppointmentCommand implements Command {

    private static final Logger log = LogManager.getLogger(UpdatePatientCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con){
        PreparedStatement ps = null;
        ResultSet rs = null;
        HttpSession session = req.getSession();
        System.out.println("session ==> " + session);
        List<DoctorAppointment> doctorAppointments=new ArrayList<>();
        int id=Integer.parseInt(req.getParameter("appointmentId"));
        try {
            ps=con.prepareStatement("SELECT * FROM doctor_appointment_archive WHERE case_record_archive_id=?");
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while (rs.next()){
                DoctorAppointment da=new DoctorAppointment();
                da.setNameStaffComplete(rs.getString("name_staff_complete"));
                da.setDescription(rs.getString("description"));
                da.setType(rs.getString("type"));
                doctorAppointments.add(da);
            }
            rs.close();
            ps.close();
            session.setAttribute("doctorAppointments",doctorAppointments);
        } catch (SQLException throwables) {
            log.error("command ListArchiveAppointment not executed" + con, throwables);
            session.setAttribute("errorMessage", 1);
            return Constant.URL_ERROR_PAGE;
        } finally{
            CloseLink.close(con);
        }
        return Constant.URL_LIST_ARCHIVE;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp){
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
