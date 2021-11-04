package com.ua.command;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

@Component("exit")
@Scope("prototype")
public class FinishSessionComand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) {
        return execute(req, resp);
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //close the session and return to the start page

        HttpSession session = req.getSession();
        session.invalidate();

        return "/index.jsp";
    }
}
