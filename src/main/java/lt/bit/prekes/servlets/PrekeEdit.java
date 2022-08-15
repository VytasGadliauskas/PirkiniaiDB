package lt.bit.prekes.servlets;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lt.bit.prekes.data.Preke;
import lt.bit.prekes.data.Tipas;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PrekeEdit", urlPatterns = {"/editPreke"})
public class PrekeEdit  extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String responds = "index.jsp";
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

            try {
                EntityManager em = (EntityManager) request.getAttribute("em");
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                Tipas tipas = em.find(Tipas.class, tipas_id);
                Preke preke = em.find(Preke.class, id);
                preke.setPreke(prekep);
                preke.setKiekis(kiekis);
                preke.setKaina(kaina);
                preke.setTipas(tipas);
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
