package lt.bit.prekes.servlets;

import lt.bit.prekes.data.Cekis;
import lt.bit.prekes.data.CekisRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "CekisAdd", urlPatterns = {"/addCekis"})
public class CekisAdd  extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if( !"".equals(request.getParameter("data")) &&
                !"".equals(request.getParameter("pavadinimas"))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date data = null;
            try {
                 data = (Date) sdf.parse(request.getParameter("data"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String pavadinimas = request.getParameter("pavadinimas").trim();
            String aprasymas = "";
            Cekis cekis = null;
            if (!"".equals(request.getParameter("aprasymas"))) {
                 aprasymas = request.getParameter("aprasymas").trim();
            }
            cekis = new Cekis(0, data , pavadinimas, aprasymas);

            try (Connection conn = (Connection) request.getAttribute("conn")) {
                CekisRepo.addCekis(cekis, conn);
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
