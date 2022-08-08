package lt.bit.prekes.servlets;

import lt.bit.prekes.data.Preke;
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
import java.util.List;


@WebServlet(name = "PrekesList", urlPatterns = {"/listPrekes"})
public class PrekesList  extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if( !"".equals(request.getParameter("cekis_id"))) {
            int cekis_id = Integer.parseInt(request.getParameter("cekis_id"));
            List<Preke> prekes = null;
            try (Connection conn = (Connection) request.getAttribute("conn")) {
                prekes = PrekeRepo.getPrekes(cekis_id, conn);
            } catch (SQLException e) {
                response.sendRedirect("klaida.html");
            }
            PrintWriter writer = response.getWriter();
            writer.print(
                    "<table class=\"table border border-primary border-2\">\n" +

                    "        <thead>\n" +
                    "          <tr class=\"table-primary rounded-top\">\n" +
                    "            <th scope=\"col\">\n" +
                    "                <img class=\"imgBtn\" src=\"img/add-shopping-cart.png\" onclick=\"addPrekeModalF()\" alt=\"Add\" width=\"40\" height=\"42\">\n" +
                    "<input type=\"hidden\" class=\"cekis_id\" value="+cekis_id+">"+
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
                writer.print("<tr> <td scope=\"col\">\n" +
                        "<img class=\"imgBtn\" src=\"img/clear-shopping-cart.png\" onclick=\"deletePrekeModalF(this, "+preke.getId()+")\" alt=\"Delete\" width=\"40\" height=\"42\">&nbsp\n" +
                        "     <img class=\"imgBtn\" src=\"img/shopping-cart.png\" onclick=\"editPrekeModalF(this, "+preke.getId()+")\" alt=\"Edit\" width=\"40\" height=\"42\">\n" +
                        "     </td>\n" +
                        "     <td class=\"tableprekeid\" name=\"id\">"+preke.getId()+"</td>\n" +
                        "     <td class=\"tableprekepreke\" name=\"preke\">"+preke.getPreke()+"</td>\n" +
                        "     <td class=\"tableprekekiekis\" name=\"kiekis\">"+preke.getKiekis()+"</td>\n" +
                        "     <td class=\"tableprekekaina\" name=\"kaina\">"+preke.getKaina()+"</td>\n" +
                        "     <td class=\"tablepreketipasid\" name=\"tipas_id\">"+preke.getTipas_id()+"</td>\n"+
                        "</tr>");
            }
            writer.print("</tbody>\n" +
                    "    </table>");

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
