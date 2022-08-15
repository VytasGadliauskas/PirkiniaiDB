package lt.bit.prekes.servlets;


import com.google.gson.Gson;
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
import java.util.HashMap;
import java.util.List;
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
            Map<String, Double> map = new HashMap<>();

            try {
                EntityManager em = (EntityManager) request.getAttribute("em");
                Query q = em.createNativeQuery("select Tipas.pavadinimas as tipas, sum(Prekes.kiekis*Prekes.kaina) as suma  from Cekis, Prekes, Tipas" +
                        " WHERE Cekis.id=Prekes.cekis_id AND Prekes.tipas_id=Tipas.id " +
                        " AND (Cekis.data >= :datanuo AND Cekis.data <= :dataiki) GROUP BY Tipas.pavadinimas");
                q.setParameter("datanuo", data_nuo);
                q.setParameter("dataiki", data_iki);
                List<Object[]> list = q.getResultList();
                for (Object[] o : list) {
                    map.put(o[0].toString(), Double.parseDouble(o[1].toString()));
                }
            } catch (Exception e) {
                response.sendRedirect("klaida.jsp?klaida=" + e);
            }

            Gson gson = new Gson();
            String mapJsonString = gson.toJson(map);

            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(mapJsonString);
            out.flush();

        } else {
            response.sendRedirect("klaida.jsp?klaida=FormuojantAtaskaita2");
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
