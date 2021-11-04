package com.ua.command;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

@Component("redirect")
@Scope("prototype")
public class RedirectCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) throws SQLException {
        return execute(req,resp);
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = req.getParameter("address");
        HttpSession session = req.getSession();
        session.setAttribute("checkLogin",null);
        session.setAttribute("messageFalse",null);
        session.setAttribute("login", null);
        session.setAttribute("password", null);
        session.setAttribute("password_repeat", null);
        session.setAttribute("role", null);
        session.setAttribute("successfully", null);
        session.setAttribute("changeProfile", null);
        if (req.getParameter("loginDoctor")!=null){
            session.setAttribute("loginDoctor",req.getParameter("loginDoctor"));
        }
        if (req.getParameter("loginNurse")!=null){
            session.setAttribute("loginNurse",req.getParameter("loginNurse"));
        }
        if (req.getParameter("loginId")!=null){
            session.setAttribute("loginId",req.getParameter("loginId"));
        }
        System.out.println(address);
        return address;
    }
}
