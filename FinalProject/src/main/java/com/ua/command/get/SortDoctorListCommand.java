package com.ua.command.get;

import com.ua.command.Command;
import com.ua.entity.Doctor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("sortDoctorList")
@Scope("prototype")
public class SortDoctorListCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) throws SQLException {
        return execute(req, resp);
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //doctor list sorting
        HttpSession session = req.getSession();
        String sort = req.getParameter("sort");
        String categorize = req.getParameter("categorize");
        session.setAttribute("sort", sort);
        List<Doctor> newDoctors = new ArrayList<>();
        if (sort.equals("name")) {
            newDoctors = (List<Doctor>) session.getAttribute("doctorsByName");
        }
        if (sort.equals("count")) {
            newDoctors = (List<Doctor>) session.getAttribute("doctorsByNumberPatient");
        }

        if (!categorize.equals("all")) {
            newDoctors = newDoctors.stream().filter(x -> x.getDepartment().equals(categorize)).collect(Collectors.toList());
        }

        session.setAttribute("doctors", newDoctors);

        return (String) session.getAttribute("finalAddress");
    }
}
