package lt.bit.prekes.servlets;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lt.bit.prekes.data.Cekis;
import lt.bit.prekes.data.Preke;
import lt.bit.prekes.data.Tipas;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "PrekeAdd", urlPatterns = {"/addPreke"})
public class PrekeAdd  extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String responds = "index.jsp";
        if( !"".equals(request.getParameter("cekis_id")) &&
                !"".equals(request.getParameter("preke")) &&
                !"".equals(request.getParameter("kiekis")) &&
                !"".equals(request.getParameter("kaina")) &&
                !"".equals(request.getParameter("tipas_id")) ){
            int cekis_id = Integer.parseInt(request.getParameter("cekis_id"));
            String prekep = request.getParameter("preke").trim();
            double kiekis = Double.parseDouble(request.getParameter("kiekis"));
            double kaina = Double.parseDouble(request.getParameter("kaina"));
            int tipas_id = Integer.parseInt(request.getParameter("tipas_id"));
            Cekis cekis = null;
            Tipas tipas = null;
            try {
                EntityManager em = (EntityManager) request.getAttribute("em");
                cekis = em.find(Cekis.class, cekis_id);
                tipas = em.find(Tipas.class, tipas_id);
            } catch (Exception e) {
                response.sendRedirect("klaida.jsp?klaida="+e.getMessage());
            }
            Preke preke = new Preke(cekis , prekep , kiekis, kaina, tipas);
            System.out.println(preke);
            try {
                EntityManager em = (EntityManager) request.getAttribute("em");
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                em.persist(preke);
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
