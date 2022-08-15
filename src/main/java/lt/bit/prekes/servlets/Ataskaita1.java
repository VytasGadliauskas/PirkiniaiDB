package lt.bit.prekes.servlets;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet(name = "Ataskaita1", urlPatterns = {"/ataskaita1"})
public class Ataskaita1 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!"".equals(request.getParameter("data_nuo")) &&
                !"".equals(request.getParameter("data_iki"))
        ) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date data_nuo = null;
            try {
                data_nuo = (Date) sdf.parse(request.getParameter("data_nuo"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Date data_iki = null;
            try {
                data_iki = (Date) sdf.parse(request.getParameter("data_iki"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            Double suma = 0.0;

            try {
                EntityManager em = (EntityManager) request.getAttribute("em");
                Query q = em.createNativeQuery("select sum(`Prekes`.`kiekis`*`Prekes`.`kaina`) as suma from Cekis, Prekes  WHERE Cekis.id=Prekes.cekis_id AND (Cekis.data >= :datanuo AND Cekis.data <= :dataiki)");
                q.setParameter("datanuo", data_nuo);
                q.setParameter("dataiki", data_iki);
                suma = Double.parseDouble(q.getSingleResult().toString());
            } catch (Exception e) {
                response.sendRedirect("klaida.jsp?klaida=" + e);
            }

            PrintWriter writer = response.getWriter();
            writer.print("<p> Suma nuo <img src=\"img/date-from.png\" alt=\"euro\"width=\"40\" height=\"42\"> " + sdf.format(data_nuo) +
                    " iki <img src=\"img/date-to.png\" alt=\"euro\"width=\"40\" height=\"42\"> " + sdf.format(data_iki) +
                    " <img src=\"img/buy.png\" alt=\"euro\"width=\"40\" height=\"42\"> nupirktų prekių: " + suma +
                    "<img src=\"img/euro.png\" alt=\"euro\"width=\"40\" height=\"42\"> </p> ");
            writer.flush();

        } else {
            response.sendRedirect("klaida.jsp?klaida=Formuojant_Ataskaita1");
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
