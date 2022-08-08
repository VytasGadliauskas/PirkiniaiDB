package lt.bit.prekes.servlets;


import lt.bit.prekes.data.Preke;
import lt.bit.prekes.data.PrekeRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "PrekeEdit", urlPatterns = {"/editPreke"})
public class PrekeEdit  extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if( !"".equals(request.getParameter("id")) &&
                !"".equals(request.getParameter("preke")) &&
                !"".equals(request.getParameter("kiekis")) &&
                !"".equals(request.getParameter("kaina")) &&
                !"".equals(request.getParameter("tipas_id")) ){
            int id = Integer.parseInt(request.getParameter("id"));
            String prekep = request.getParameter("preke").trim();
            Double kiekis = Double.parseDouble(request.getParameter("kiekis"));
            Double kaina = Double.parseDouble(request.getParameter("kaina"));
            int tipas_id = Integer.parseInt(request.getParameter("tipas_id"));

            Preke preke = new Preke(id, 0 , prekep , kiekis, kaina, tipas_id);
            System.out.println(preke);
            try (Connection conn = (Connection) request.getAttribute("conn")) {
                PrekeRepo.updatePreke(preke, conn);
            } catch (SQLException e) {
                response.sendRedirect("klaida.html");
            }
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
