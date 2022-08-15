package lt.bit.prekes.servlets;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lt.bit.prekes.data.Cekis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "CekisAdd", urlPatterns = {"/addCekis"})
public class CekisAdd  extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String responds = "index.jsp";
        if( !"".equals(request.getParameter("data")) &&
                !"".equals(request.getParameter("pavadinimas"))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date data = null;
            try {
                 data = sdf.parse(request.getParameter("data"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String pavadinimas = request.getParameter("pavadinimas").trim();
            String aprasymas = "";
            Cekis cekis = null;
            if (!"".equals(request.getParameter("aprasymas"))) {
                 aprasymas = request.getParameter("aprasymas").trim();
            }
            cekis = new Cekis(new java.sql.Date(data.getTime()) , pavadinimas, aprasymas);

            try {
                EntityManager em = (EntityManager) request.getAttribute("em");
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                em.persist(cekis);
                tx.commit();
            } catch (Exception e) {
                responds = "klaida.jsp?klaida="+e.getMessage();
            }
        }
        response.sendRedirect(responds);
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
