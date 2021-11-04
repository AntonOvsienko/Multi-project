package com.ua.command.get;

import com.ua.command.Command;
import com.ua.entity.CaseRecord;
import com.ua.entity.Patient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

@Component("sortPatientList")
@Scope("prototype")
public class SortPatientListCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) throws SQLException {
        return null;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //patient list sorting
        HttpSession session = req.getSession();
        String sort = req.getParameter("sort2");
        session.setAttribute("sort2", sort);
        if (sort.equals("sortByName")) {
            session.setAttribute("patients", session.getAttribute("patientsByName"));
        }
        if (sort.equals("sortByBirthday")) {
            session.setAttribute("patients", session.getAttribute("patientsByBirthday"));
        }
        return (String)session.getAttribute("finalAddress");
    }
}
