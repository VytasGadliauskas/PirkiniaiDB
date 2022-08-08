package lt.bit.prekes.servlets;

import lt.bit.prekes.data.CekisRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet(name = "CekisDelete", urlPatterns = {"/deleteCekis"})
public class CekisDelete extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // System.out.println("parametrai : "+request.getParameter("id"));
        if (!"".equals(request.getParameter("id"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            try (Connection conn = (Connection) request.getAttribute("conn")) {
                CekisRepo.deleteCekis(id, conn);
            } catch (SQLException e) {
                System.out.println("deleteCekis klaida " + e.getMessage());
                response.sendRedirect("klaida.html");
            }
        } else {
            System.out.println("deleteCekis klaida nera id");
        }
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
