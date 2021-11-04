package com.ua.Utils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalTag extends TagSupport {

    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int doStartTag() throws JspException {
        String baseName="";
        if ((String) pageContext.getSession().getAttribute("basename") == null) {
            baseName = "message";
        } else {
            baseName = (String) pageContext.getSession().getAttribute("basename");
        }
        String curLocale = (String) pageContext.getSession().getAttribute("currentLocale");
        if (curLocale == null) {
            curLocale = pageContext.getServletContext().getInitParameter("defaultLocale");
        }

        ResourceBundle rb =
                ResourceBundle.getBundle(baseName, new Locale(curLocale));
        String value = rb.getString(key);
        try {
            pageContext.getOut().println(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

}
