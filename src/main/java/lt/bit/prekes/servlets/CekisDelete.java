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


@WebServlet(name = "CekisDelete", urlPatterns = {"/deleteCekis"})
public class CekisDelete extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!"".equals(request.getParameter("id"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                EntityManager em = (EntityManager) request.getAttribute("em");
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                Cekis cekis = em.find(Cekis.class, id);
                em.remove(cekis);
                tx.commit();
            } catch (Exception e) {
                response.sendRedirect("klaida.jsp?klaida="+e.getMessage());
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
