package lt.bit.prekes.servlets;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lt.bit.prekes.data.Cekis;
import lt.bit.prekes.data.Preke;
import lt.bit.prekes.data.Tipas;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(name = "PrekesList", urlPatterns = {"/listPrekes"})
public class PrekesList extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!"".equals(request.getParameter("cekis_id"))) {
            int cekis_id = Integer.parseInt(request.getParameter("cekis_id"));
            List<Preke> prekes = null;
            Tipas tipas = null;
            try {
                EntityManager em = (EntityManager) request.getAttribute("em");
                Cekis cekis = em.find(Cekis.class, cekis_id);
                /////
                ///// Problema neranda tokio Entity, nes klase yra Preke, o selekte Prekes (Jeigu naudoju @Entity(name="Prekes"))
                ///// tada randa bet panasu, kad nesucastina List<Preke> prekes.get(1) = (Preke) q.getResultList().get(1)
                ///// matyt del Cekis cekis Tipas tipas objektu prekes klaseje (@ManyToOne)
                /////
                ///// -- Susitvarkiau pakeisdamas clasiu toString metodus kad grazintu tik lentos laukus
                ///// -- Idomu kaip toString metodas dalyvauja hibernate/jpa procese
                /////

                Query q = em.createQuery("SELECT preke FROM Prekes preke WHERE preke.cekis=:cekis");
                q.setParameter("cekis", cekis);
                prekes = q.getResultList();

                ////
                PrintWriter writer = response.getWriter();
                writer.print(
                        "<table class=\"table border border-primary border-2\">\n" +
                                "        <thead>\n" +
                                "          <tr class=\"table-primary rounded-top\">\n" +
                                "            <th scope=\"col\">\n" +
                                "                <img class=\"imgBtn\" src=\"img/add-shopping-cart.png\" onclick=\"addPrekeModalF()\" alt=\"Add\" width=\"40\" height=\"42\">\n" +
                                "<input type=\"hidden\" class=\"cekis_id\" value=" + cekis_id + ">" +
                                "</th>\n" +
                                "            <th scope=\"col\">&nbsp Id</th>\n" +
                                "            <th scope=\"col\"><img src=\"img/hamper.png\" alt=\"hamper\"width=\"40\" height=\"42\">&nbsp Prekė</th>\n" +
                                "            <th scope=\"col\"><img src=\"img/numbers.png\" alt=\"number\"width=\"40\" height=\"42\">&nbsp Kiekis</th>\n" +
                                "            <th scope=\"col\"><img src=\"img/cash.png\" alt=\"cash\"width=\"40\" height=\"42\">&nbsp Kaina </th>\n" +
                                "            <th scope=\"col\"><img src=\"img/type.png\" alt=\"type\"width=\"40\" height=\"42\">&nbsp Tipas </th>\n" +
                                "          </tr>\n" +
                                "        </thead>\n" +
                                "        <tbody>\n");
                for (Preke preke : prekes) {
                    tipas = preke.getTipas();
                    writer.print("<tr> <td scope=\"col\">\n" +
                            "<img class=\"imgBtn\" src=\"img/clear-shopping-cart.png\" onclick=\"deletePrekeModalF(this, " + preke.getId() + ")\" alt=\"Delete\" width=\"40\" height=\"42\">&nbsp\n" +
                            "     <img class=\"imgBtn\" src=\"img/shopping-cart.png\" onclick=\"editPrekeModalF(this, " + preke.getId() + ")\" alt=\"Edit\" width=\"40\" height=\"42\">\n" +
                            "     </td>\n" +
                            "     <td class=\"tableprekeid\" name=\"id\">" + preke.getId() + "</td>\n" +
                            "     <td class=\"tableprekepreke\" name=\"preke\">" + preke.getPreke() + "</td>\n" +
                            "     <td class=\"tableprekekiekis\" name=\"kiekis\">" + preke.getKiekis() + "</td>\n" +
                            "     <td class=\"tableprekekaina\" name=\"kaina\">" + preke.getKaina() + "</td>\n" +
                            "     <td class=\"tablepreketipasid\" name=\"tipas_id\">" + tipas.getPavadinimas() + "</td>\n" +
                            "</tr>");
                }
                writer.print("</tbody>\n" +
                        "    </table>");
                writer.flush();
            } catch (Exception e) {
                response.sendRedirect("klaida.jsp?klaida=" + e.getMessage());
            }
        } else {
            response.sendRedirect("klaida.jsp?klaida=Blogas_Cekis");
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
