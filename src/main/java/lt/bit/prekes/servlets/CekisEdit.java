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

@WebServlet(name = "CekisEdit", urlPatterns = {"/editCekis"})
public class CekisEdit extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!"".equals(request.getParameter("id")) &&
                !"".equals(request.getParameter("data")) &&
                !"".equals(request.getParameter("parduotuve"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date data = null;
            try {
                data = sdf.parse(request.getParameter("data"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String pavadinimas = request.getParameter("parduotuve").trim();
            String aprasymas = "";
            Cekis cekis = null;
            if (!"".equals(request.getParameter("aprasymas"))) {
                aprasymas = request.getParameter("aprasymas").trim();
            }
            cekis = new Cekis(id, data, pavadinimas, aprasymas);
            System.out.println(cekis);
            try (Connection conn = (Connection) request.getAttribute("conn")) {
                CekisRepo.updateCekis(cekis, conn);
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
