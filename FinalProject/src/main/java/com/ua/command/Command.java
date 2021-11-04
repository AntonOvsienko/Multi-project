package com.ua.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

public interface Command {
	
	String execute(HttpServletRequest req, HttpServletResponse resp, Connection con) throws SQLException;

	String execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException;

}
