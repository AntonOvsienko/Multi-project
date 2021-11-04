package com.ua.Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/controller"})
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String config = filterConfig.getInitParameter("security-config");
        System.out.println(config);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq =
                (HttpServletRequest) request;

        HttpSession session = httpReq.getSession();

        if (request.getParameter("globalLogin")!=null){
            session.setAttribute("globalLogin",request.getParameter("globalLogin"));
        }
        String userRole =  (String) session.getAttribute("globalLogin");
        if (userRole==null){
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        String servletPath = httpReq.getServletPath();
        System.out.println("servletPath ==> " + servletPath);

        chain.doFilter(request, response);
    }



}
