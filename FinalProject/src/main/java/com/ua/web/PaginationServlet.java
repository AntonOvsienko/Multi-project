package com.ua.web;

import com.ua.entity.CaseRecord;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/page")
public class PaginationServlet extends HttpServlet {

    private static final int shift = 0;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        System.out.println("session ==> " + session);

        String paramPage = req.getParameter("page");
        String paramPageSize = req.getParameter("pageSize");

        int page = Integer.parseInt(paramPage);
        int pageSize = Integer.parseInt(paramPageSize);

        List<CaseRecord> caseRecordList = (List<CaseRecord>) session.getAttribute("caseRecordList");
        List<CaseRecord> finalCR = caseRecordList.stream()
                .filter(x -> x.getDoctorAppointmentList().size() != 0)
                .collect(Collectors.toList());
        int size = finalCR.size();
        int minPagePossible = page - shift < 1 ? 1 : page - shift;
        int pageCount = (int) Math.ceil((double) size / (double) pageSize);
        int maxPagePossible = page + shift > pageCount ? pageCount : page + shift;
        session.setAttribute("caseRecordList", finalCR);
        session.setAttribute("pageCount", pageCount);
        session.setAttribute("page", page);
        session.setAttribute("pageSize", pageSize);
        session.setAttribute("minPossiblePage", minPagePossible);
        session.setAttribute("maxPossiblePage", maxPagePossible);
        req.getRequestDispatcher("users/nurse.jsp").forward(req, resp);
    }


}
