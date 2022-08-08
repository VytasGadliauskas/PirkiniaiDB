package lt.bit.prekes.servlets;


import com.google.gson.Gson;
import lt.bit.prekes.data.PrekeRepo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@WebServlet(name = "Ataskaita2", urlPatterns = {"/ataskaita2"})
public class Ataskaita2 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!"".equals(request.getParameter("data_nuo")) &&
                !"".equals(request.getParameter("data_iki"))
        ) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date data_nuo = null;
            try {
                data_nuo = sdf.parse(request.getParameter("data_nuo"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Date data_iki = null;
            try {
                data_iki = sdf.parse(request.getParameter("data_iki"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Map<String,Double> map = null;
            try (Connection conn = (Connection) request.getAttribute("conn")) {
                map = PrekeRepo.mapPagalDatasTipus(conn, data_nuo, data_iki);
            } catch (SQLException e) {
                response.sendRedirect("klaida.html");
            }
            Gson gson = new Gson();
            String mapJsonString = gson.toJson(map);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(mapJsonString);
            out.flush();

        } else {
            response.sendRedirect("klaida.html");
        }
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
