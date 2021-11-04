package com.ua.Utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //setting pages to UTF-8
        if (sce.getServletContext().getRequestCharacterEncoding()==null) {
            sce.getServletContext().setRequestCharacterEncoding("UTF-8");
        }
    }
}
